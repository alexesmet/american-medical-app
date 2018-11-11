package com.itsm.frontend.service;

import com.itsm.common.entity.Transaction;
import com.itsm.frontend.annotation.Auditable;
import com.itsm.frontend.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.awt.*;

@Service
public class TransactionAddService implements AddServiceInterface<Transaction> {

    @Autowired
    private Storage<Transaction> transactionStorage;

    @Override
    @Transactional
    @Auditable
    public void add(Transaction transaction) throws Exception {
        if(transaction.getPatient().getState().getId() != transaction.getProduct().getState().getId()) {
            throw new Exception("Patient's state and Products State has to be the same!");
        }

        transactionStorage.add(transaction);
    }
}
