package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.State;
import com.itsm.frontend.storage.AbstractStorage;
import org.springframework.stereotype.Repository;

@Repository
public class StateStorage extends AbstractStorage<State> {

    @Override
    protected Class<State> getEntityClass() {
        return State.class;
    }
}
