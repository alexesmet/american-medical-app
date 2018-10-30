package com.itsm.frontend.storage;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.State;
import com.itsm.frontend.auditor.Auditable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.event.CaretListener;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ClientStorage extends AbstractStorage<Client>{

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }
}
