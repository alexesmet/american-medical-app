package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.Transaction;
import com.itsm.frontend.storage.AbstractStorage;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionStorage extends AbstractStorage<Transaction> {
    @Override
    protected Class<Transaction> getEntityClass() {
        return Transaction.class;
    }
}
