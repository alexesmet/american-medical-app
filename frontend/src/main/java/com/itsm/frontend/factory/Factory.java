package com.itsm.frontend.factory;

public interface Factory<T> {
    T create(long id);
}
