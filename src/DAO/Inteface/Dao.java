package DAO.Inteface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    boolean save(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(int id) throws SQLException;
}