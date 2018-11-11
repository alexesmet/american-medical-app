package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.Transaction;
import com.itsm.common.entity.User;
import com.itsm.frontend.storage.AbstractStorage;
import com.itsm.frontend.storage.interf.InterfaceTransactionStorage;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class TransactionStorage extends AbstractStorage<Transaction> implements InterfaceTransactionStorage {
    @Override
    protected Class<Transaction> getEntityClass() {
        return Transaction.class;
    }

    @Override
    public List<Transaction> getByUser(User user) {
        TypedQuery<Transaction> query = em.createQuery("select t from transaction t where t.createdBy = :user", Transaction.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
