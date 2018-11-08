package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.AuditOperation;
import com.itsm.frontend.storage.AbstractStorage;
import com.itsm.frontend.storage.interf.InterfaceAuditOperationStorage;
import org.springframework.stereotype.Repository;

@Repository
public class AuditOperationStorage extends AbstractStorage<AuditOperation> implements InterfaceAuditOperationStorage {
    @Override
    protected Class<AuditOperation> getEntityClass() {
        return AuditOperation.class;
    }
}
