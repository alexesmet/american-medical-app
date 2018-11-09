package com.itsm.frontend.service;

import com.itsm.common.entity.Transaction;

public interface AddServiceInterface<T> {
    void add(T t) throws Exception;
}
