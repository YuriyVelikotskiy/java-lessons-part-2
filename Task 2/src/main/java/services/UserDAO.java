package services;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

public class UserDAO implements DAO<User> {

    private Session session;
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Override
    public User findById(int id) {
        logger.info("Поиск пользователя с id: {}", id);
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            logger.info("Пользователя с id: {} - найден", id);
            return session.find(User.class, id);
        } catch (Exception e) {
            logger.info("Пользователя с id: {} - не найден", id);
            throw new RuntimeException("Ошибка поиска");
        }
    }

    @Override
    public List<User> findAll() {
        logger.info("Выгрузка всех записей");
        String SELECT_ALL = "From User";
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            logger.info("Выгрузка всех записей - получена");
            return session.createQuery(SELECT_ALL, User.class).getResultList();
        } catch (Exception e) {
            logger.info("Ошибка выгрузки записей");
            throw new RuntimeException("Ошибка получения записей");
        }
    }

    @Override
    public void save(User user) {
        String massage = "записи";
        logger.info("Попытка сохранения пользователя" + user.toString());
        tryTransaction(() -> session.persist(user), massage);
    }

    @Override
    public void update(User user) {
        String massage = "обновления";
        logger.info("Попытка обновления данных пользователя" + user.toString());
        tryTransaction(() -> session.merge(user), massage);
    }

    @Override
    public void delete(User user) {
        String massage = "удаления";
        logger.info("Попытка удаления пользователя " + user.toString());
        tryTransaction(() -> session.remove(user), massage);
    }

    private void tryTransaction(Runnable runnable, String massage) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            this.session = session;
            transaction = this.session.beginTransaction();
            runnable.run();
            transaction.commit();
            logger.info("Операция {} успешно выполнена", massage);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка " + massage);
        }
    }
}