package services;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO implements DAO<User> {

    private Session session;

    @Override
    public User findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка поиска");
        }
    }

    @Override
    public List<User> findAll() {
        String SELECT = "From User";
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery(SELECT, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения всех");
        }
    }

    @Override
    public void save(User user) {
        String massage = "Ошибка записи";
        tryTransaction(() -> session.persist(user), massage);
    }

    @Override
    public void update(User user) {
        String massage = "Ошибка обновления";
        tryTransaction(() -> session.merge(user), massage);
    }

    @Override
    public void delete(User user) {
        String massage = "Ошибка удаления";
        tryTransaction(() -> session.remove(user), massage);
    }

    private void tryTransaction(Runnable runnable, String massage) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            this.session = session;
            transaction = this.session.beginTransaction();
            runnable.run();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(massage);
        }
    }
}