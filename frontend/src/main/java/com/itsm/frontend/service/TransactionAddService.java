package com.itsm.frontend.service;

import com.itsm.common.entity.Transaction;
import com.itsm.frontend.annotation.Auditable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.InputMismatchException;

@Service
public class TransactionAddService implements AddServiceInterface<Transaction> {

    @PersistenceContext
    protected EntityManager em;


    @Override
    @Transactional
    @Auditable
    public void add(Transaction transaction) throws Exception {
        if(transaction.getPatient().getState().getId() != transaction.getProduct().getState().getId()) {
            throw new Exception("Patient's state and Products State has to be the same!");
        }
        em.persist(transaction);
    }
}
