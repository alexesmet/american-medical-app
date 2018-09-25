package com.itsm.factory;

import com.itsm.entity.Transaction;
import com.itsm.storage.ClientStorage;
import com.itsm.storage.ProductStorage;

import java.sql.SQLException;
import java.util.Scanner;

public class TransactionFactory implements Factory<Transaction> {

    private final ClientStorage clientStorage;
    private final ProductStorage productStorage;

    public TransactionFactory(ClientStorage clientStorage, ProductStorage productStorage) {
        this.clientStorage = clientStorage;
        this.productStorage = productStorage;
    }

    @Override
    public Transaction create(long id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Patient's id: ");
        long client_id = sc.nextLong();
        System.out.print("Medicine id: ");
        long product_id = sc.nextLong();
        try {
            if (!clientStorage.contains(client_id)) {
                System.out.println("No such client id");
            }
            if (!productStorage.contains(product_id)) {
                System.out.println("No such product id");
            }
            return new Transaction(clientStorage.get(client_id),productStorage.get(product_id));
        } catch (SQLException e) {
            System.out.println("Unexpected SQLException!");
            e.printStackTrace();
        }
        return null;

    }
}
