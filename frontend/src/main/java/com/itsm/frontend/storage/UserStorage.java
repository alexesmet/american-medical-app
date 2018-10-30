package com.itsm.frontend.storage;

import com.itsm.common.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserStorage extends AbstractStorage<User> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
