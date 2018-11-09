package com.itsm.frontend.storage;

import com.itsm.common.entity.EntityInterface;
import com.itsm.frontend.annotation.Auditable;
import com.itsm.frontend.session.UserSessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractStorage<E extends EntityInterface> implements Storage<E> {

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    private UserSessionHolder userSessionHolder;

    public final  List<E> getAll() {
        TypedQuery<E> query =  em.createQuery("from " + getEntityClass().getAnnotation(Entity.class).name(), getEntityClass());
        return query.getResultList();
    }

    public final E get(long id) {
        return em.find(getEntityClass(), id);
    }

    @Auditable
    @Transactional
    public void add(E e) {
        e.setCreatedBy(userSessionHolder.getUser());
        em.persist(e);
    }

    @Auditable
    @Transactional
    public void update(E e) {
        e.setUpdatedBy(userSessionHolder.getUser());
        em.merge(e);
    }

    @Auditable
    @Transactional
    public void delete(E e) {
        em.remove(e);
    }

    @Transactional
    public void delete(long id) {
        this.delete(this.get(id));
    }

    @Override
    public boolean contains(long id) {
        return (null != this.get(id));
    }

    protected abstract Class<E> getEntityClass();
}
