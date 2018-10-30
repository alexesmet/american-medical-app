package com.itsm.frontend.storage;

import com.itsm.common.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionStorage extends AbstractStorage<Transaction> {
    @Override
    protected Class<Transaction> getEntityClass() {
        return Transaction.class;
    }
}
