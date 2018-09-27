package com.itsm.util;

import com.itsm.auditor.Auditable;
import com.itsm.entity.Transaction;

import java.sql.*;

public class TransactionManager implements Manager<Transaction> {
    private final String url;

    public TransactionManager(String url) {
        this.url = url;
    }

    @Auditable
    public void execute(Transaction transaction) throws SQLException {
        if(transaction != null) {
            if (transaction.getPatient().getState().getId() == transaction.getProduct().getState().getId()) {
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement ps = connection.prepareStatement("INSERT INTO transactions (client_id,product_id,time) VALUES (?, ?, ?)");
                ps.setLong(1, transaction.getPatient().getId());
                ps.setLong(2, transaction.getProduct().getId());
                ps.setTimestamp(3, new Timestamp(transaction.getDatetime().getTime()));
                ps.execute();
                ps.close();
                connection.close();
                System.out.println("Success!");
            } else {
                throw new SQLException("Client's and Product's ID should be equal!");
            }
        } else {
            System.out.println("Abort.");
        }
    }
}
