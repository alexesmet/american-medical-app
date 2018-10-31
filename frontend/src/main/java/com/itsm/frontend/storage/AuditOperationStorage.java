package com.itsm.frontend.storage;

import com.itsm.common.entity.AuditOperation;
import org.springframework.stereotype.Repository;

@Repository
public class AuditOperationStorage extends AbstractStorage<AuditOperation> {
    @Override
    protected Class<AuditOperation> getEntityClass() {
        return AuditOperation.class;
    }
}
