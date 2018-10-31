package com.itsm.frontend.storage;

import java.util.List;

public interface Storage<T> {
    T get(long id);

    List<T> getAll();

    void add(T o);

    void update(T o);

    void delete(long id);

    boolean contains(long id);
}
