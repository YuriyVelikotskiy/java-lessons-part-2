package services;

import model.User;

import java.util.List;

public class UserService {

    private final DAO<User> userDAO = new UserDAO();

    public UserService() {
    }

    public User findUser(int id) {
        return userDAO.findById(id);
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }
}
