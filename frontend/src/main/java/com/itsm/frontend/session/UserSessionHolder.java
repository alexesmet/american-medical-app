package com.itsm.frontend.session;

import com.itsm.common.entity.User;

public class UserSessionHolder{
    private ThreadLocal<User> threadUser = new ThreadLocal<>();

    public User getUser() {
        return  threadUser.get();
    }

    public String getUsername() {
        return  threadUser.get().getName();
    }

    public void setUser(User user) {
        threadUser.set(user);
    }



}
