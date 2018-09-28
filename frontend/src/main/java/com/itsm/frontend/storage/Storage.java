package com.itsm.frontend.storage;

import java.sql.SQLException;
import java.util.List;

public interface Storage<T> {
    T get(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void add(T o) throws SQLException;

    void update(T o) throws SQLException;

    void delete(long id) throws SQLException;

    boolean contains(long id) throws SQLException;
}
