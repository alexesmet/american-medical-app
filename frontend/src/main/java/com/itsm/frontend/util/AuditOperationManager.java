package com.itsm.frontend.util;

import com.itsm.common.entity.AuditOperation;

import java.sql.*;

public class AuditOperationManager implements Manager<AuditOperation> {
    private final String url;

    public AuditOperationManager(String url) {
        this.url = url;
    }

    @Override
    public void execute(AuditOperation auditOperation) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO audit_operations (time, success, action) VALUES (?, ?, ?)");
            ps.setTimestamp(1, new Timestamp(auditOperation.getDate().getTime()));
            ps.setBoolean(2, auditOperation.isSuccessful());
            ps.setString(3, auditOperation.getAction());
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
