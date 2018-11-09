package com.itsm.frontend.service;

import com.itsm.common.entity.AuditOperation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class AuditOperationAddService {
    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public void add(AuditOperation e) {
        em.persist(e);
    }
}
