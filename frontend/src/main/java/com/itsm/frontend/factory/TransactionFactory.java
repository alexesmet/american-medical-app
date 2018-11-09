package com.itsm.frontend.factory;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.Product;
import com.itsm.common.entity.Transaction;
import com.itsm.frontend.annotation.Auditable;
import com.itsm.frontend.storage.Storage;

import javax.transaction.Transactional;
import java.util.Scanner;

public class TransactionFactory implements Factory<Transaction> {

    private final Storage<Client> clientStorage;
    private final Storage<Product> productStorage;

    public TransactionFactory(Storage<Client> clientStorage, Storage<Product> productStorage) {
        this.clientStorage = clientStorage;
        this.productStorage = productStorage;
    }

    @Override
    @Transactional
    @Auditable
    public Transaction create(long id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Patient's id: ");
        long client_id = sc.nextLong();
        Client client = clientStorage.get(client_id);
        System.out.print("Medicine id: ");
        long product_id = sc.nextLong();
        Product product = productStorage.get(product_id);


        System.out.println("Transaction created...");
        return new Transaction(client,product);

    }
}
