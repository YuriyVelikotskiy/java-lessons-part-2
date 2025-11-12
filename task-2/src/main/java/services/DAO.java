package services;

import java.util.List;

public interface DAO<T> {
    void save(T t);
    T findById(int id);
    void update(T t);
    void delete(T t);
    List<T> findAll();
}
