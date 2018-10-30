package com.itsm.frontend.storage;

import com.itsm.common.entity.AuditOperation;

public class AuditOperationStaorage extends AbstractStorage<AuditOperation> {
    @Override
    protected Class<AuditOperation> getEntityClass() {
        return AuditOperation.class;
    }
}
