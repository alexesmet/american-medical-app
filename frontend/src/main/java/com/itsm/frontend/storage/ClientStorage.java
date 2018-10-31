package com.itsm.frontend.storage;

import com.itsm.common.entity.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientStorage extends AbstractStorage<Client>{

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }
}
