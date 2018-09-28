package com.itsm.frontend.factory;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.Product;
import com.itsm.common.entity.Transaction;
import com.itsm.frontend.storage.Storage;

import java.sql.SQLException;
import java.util.Scanner;

public class TransactionFactory implements Factory<Transaction> {

    private final Storage<Client> clientStorage;
    private final Storage<Product> productStorage;

    public TransactionFactory(Storage<Client> clientStorage, Storage<Product> productStorage) {
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
            System.out.println("Transaction created...");
            return new Transaction(clientStorage.get(client_id),productStorage.get(product_id));
        } catch (SQLException e) {
            System.out.println("Unexpected SQLException!");
            e.printStackTrace();
        }
        return null;

    }
}
