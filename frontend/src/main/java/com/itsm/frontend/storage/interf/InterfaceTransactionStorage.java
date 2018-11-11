package com.itsm.frontend.storage.interf;

import com.itsm.common.entity.Transaction;
import com.itsm.common.entity.User;

import java.util.List;

public interface InterfaceTransactionStorage {
    List<Transaction> getByUser(User u);
}
