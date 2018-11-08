package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.Client;
import com.itsm.frontend.storage.AbstractStorage;
import org.springframework.stereotype.Repository;

@Repository
public class ClientStorage extends AbstractStorage<Client> {

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }
}
