package services;

import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private static User user;
    private static UserService userService;
    private static UserDAO mockUserDAO;

    @BeforeAll
    static void init() {
        user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@test.ru");
        user.setAge(20);
        user.setCreated_at(LocalDate.of(2000, 1, 1));
        mockUserDAO = Mockito.mock(UserDAO.class);
        userService = new UserService(mockUserDAO);
    }

    @Test
    @DisplayName("Поиск записи по id")
    void shouldFindUser() {
        when(mockUserDAO.findById(1)).thenReturn(user);
        User returnedUser = userService.findUser(1);
        verify(mockUserDAO, times(1)).findById(1);
        assertEquals(user, returnedUser);
    }

    @Test
    @DisplayName("Выгрузка всех записей")
    void shouldReturnListOfUsers() {
        when(mockUserDAO.findAll()).thenReturn(new ArrayList<>());
        List<User> returnedList = userService.findAllUsers();
        verify(mockUserDAO, times(1)).findAll();
        assertTrue(returnedList.isEmpty());
    }

    @Test
    @DisplayName("Сохранение записи")
    void shouldBeSave() {
        doNothing().when(mockUserDAO).save(user);
        userService.saveUser(user);
        verify(mockUserDAO, times(1)).save(user);
    }

    @Test
    @DisplayName("Обновление записи")
    void shouldBeUpdate() {
        doNothing().when(mockUserDAO).update(user);
        userService.updateUser(user);
        verify(mockUserDAO, times(1)).update(user);
    }
    @Test
    @DisplayName("Удаление записи")
    void shouldBeDelete() {
        doNothing().when(mockUserDAO).delete(user);
        userService.deleteUser(user);
        verify(mockUserDAO, times(1)).delete(user);
    }
}
