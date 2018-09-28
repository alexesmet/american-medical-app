package com.itsm.frontend.util;

import java.sql.SQLException;

/**
 * Has something to do with an entity in a database.
 */
public interface Manager<T> {
    void execute(T t) throws SQLException;
}
