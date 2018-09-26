package com.itsm.core;

import com.itsm.entity.Transaction;
import com.itsm.factory.TransactionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionManager {
    private final String url;
    private final TransactionFactory transactionFactory;

    public TransactionManager(String url, TransactionFactory transactionFactory) {
        this.url = url;
        this.transactionFactory = transactionFactory;
    }

    void transact() throws SQLException {
        Transaction transaction = transactionFactory.create(0);
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
            } else {
                throw new SQLException("Client's and Product's ID should be equal!");
            }
        } else {
            System.out.println("Abort.");
        }
    }
}
