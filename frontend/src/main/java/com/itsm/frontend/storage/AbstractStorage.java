package com.itsm.frontend.storage;

import com.itsm.common.entity.EntityInterface;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractStorage<E extends EntityInterface> implements Storage<E> {

    @PersistenceContext
    protected EntityManager em;

    public final  List<E> getAll() {
        TypedQuery<E> query =  em.createQuery("from " + getEntityClass().getAnnotation(Entity.class).name(), getEntityClass());
        return query.getResultList();
    }

    public final E get(long id) {
        return em.find(getEntityClass(), id);
    }

    @Transactional
    public void add(E e) {
        em.persist(e);
    }

    @Transactional
    public void update(E e) {
        em.merge(e);
    }

    @Transactional
    public void delete(E e) {
        em.remove(e);
    }

    public void delete(long id) {
        this.delete(this.get(id));
    }

    @Override
    public boolean contains(long id) {
        return (null == this.get(id));
    }

    protected abstract Class<E> getEntityClass();
}
