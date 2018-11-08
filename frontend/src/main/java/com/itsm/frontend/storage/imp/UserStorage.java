package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.User;
import com.itsm.frontend.storage.AbstractStorage;
import com.itsm.frontend.storage.interf.InterfaceUserStorage;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class UserStorage extends AbstractStorage<User> implements InterfaceUserStorage {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getByName(String name) {
        TypedQuery<User> query = em.createQuery("select u from user u where u.name = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public User getByEmail(String email) {
        TypedQuery<User> query = em.createQuery("select u from user u where u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
}
