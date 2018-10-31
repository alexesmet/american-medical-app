package com.itsm.frontend.storage;

import com.itsm.common.entity.State;
import org.springframework.stereotype.Repository;

@Repository
public class StateStorage extends AbstractStorage<State> {

    @Override
    protected Class<State> getEntityClass() {
        return State.class;
    }
}
