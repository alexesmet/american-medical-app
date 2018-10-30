package com.itsm.frontend.storage;

import com.itsm.common.entity.State;
import com.itsm.frontend.auditor.Auditable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Repository
public class StateStorage extends AbstractStorage<State> {

    @Override
    protected Class<State> getEntityClass() {
        return State.class;
    }
}
