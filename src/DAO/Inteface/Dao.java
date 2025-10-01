package DAO.Inteface;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> getById(int id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(int id);
}