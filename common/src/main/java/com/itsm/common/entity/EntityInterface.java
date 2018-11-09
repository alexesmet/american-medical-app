package com.itsm.common.entity;

public interface EntityInterface {
    long getId();

    default void setUpdatedBy(User user) {

    }

    default void setCreatedBy(User user) {

    }

}