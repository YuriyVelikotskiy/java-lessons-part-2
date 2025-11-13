package services;

import model.User;


import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@Testcontainers
class UserDAOTest {

    static User user;
    static UserDAO userDAO;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @BeforeAll
    static void init() {
        postgres.start();
        hibernateConfigure();

        user = new User();
        user.setName("Test");
        user.setEmail("test@test.ru");
        user.setAge(20);
        user.setCreated_at(LocalDate.of(2000, 1, 1));

        userDAO = new UserDAO();
        userDAO.save(user);
    }

    static void hibernateConfigure() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class)
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "create");
        HibernateSessionFactory.setUp(configuration);
    }

    @AfterAll
    static void cleanUp() {
        postgres.stop();
    }

    @Test
    @DisplayName("Поиск записи по существующему id")
    void shouldBeFound() {
        User returnedUser = userDAO.findById(user.getId());
        assertEquals(user, returnedUser);
    }

    @Test
    @DisplayName("Поиск записи по не существующему id")
    void shouldNotBeFound() {
        User returnedUser = userDAO.findById(111);
        assertNull(returnedUser);
    }

    @Test
    @DisplayName("Выгрузка всех записей")
    void shouldReturnListOfUsers() {
        List<User> returnedUsers = userDAO.findAll();
        assertFalse(returnedUsers.isEmpty());
        assertEquals(returnedUsers.size(), 1);
    }

    @Test
    @DisplayName("Сохранение записи")
    void shouldBeSave() {
        User userForSave = new User();
        userDAO.save(userForSave);
        User returnedUser = userDAO.findById(userForSave.getId());
        assertEquals(userForSave, returnedUser);
        userDAO.delete(userForSave);
    }

    @Test
    @DisplayName("Обновление записи")
    void shouldBeUpdate() {
        User userForSave = new User();
        userDAO.save(userForSave);
        User returnedUser = userDAO.findById(userForSave.getId());
        returnedUser.setName("Updated");
        userDAO.update(returnedUser);
        User updatedUser = userDAO.findById(userForSave.getId());
        assertEquals(updatedUser.getName(), "Updated");
        userDAO.delete(updatedUser);
    }

    @Test
    @DisplayName("Обновление несуществующей записи")
    void afterUpdateException() {
        User userForUpdate = new User();
        userForUpdate.setId(111);
        assertThrows(RuntimeException.class,() -> userDAO.update(userForUpdate));
    }

    @Test
    @DisplayName("Удаление записи")
    void shouldBeDelete() {
        User userForSave = new User();
        userDAO.save(userForSave);
        User returnedUser = userDAO.findById(userForSave.getId());
        assertEquals(userForSave, returnedUser);
        userDAO.delete(userForSave);
        User deletedUser = userDAO.findById(userForSave.getId());
        assertNull(deletedUser);
    }

    @Test
    @DisplayName("Удаление несуществующей записи")
    void afterDeleteException() {
        User userForDelete = new User();
        userForDelete.setId(111);
        assertThrows(RuntimeException.class,() -> userDAO.delete(userForDelete));
    }
}
