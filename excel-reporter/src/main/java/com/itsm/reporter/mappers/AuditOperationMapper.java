package com.itsm.reporter.mappers;

import com.itsm.common.entity.AuditOperation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuditOperationMapper {
    @Select("SELECT * FROM audit_operations")
    List<AuditOperation> getAll();

    @Select("SELECT * FROM audit_operations WHERE id = #{id}")
    AuditOperation get(long id);
}
