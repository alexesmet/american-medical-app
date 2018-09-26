package com.itsm.core;


import com.itsm.entity.Client;
import com.itsm.entity.Product;
import com.itsm.entity.State;
import com.itsm.entity.Transaction;
import com.itsm.factory.Factory;

import com.itsm.storage.Storage;
import com.itsm.util.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Scanner;

@Component("ConsoleMenu")
public class ConsoleMenu implements Runnable{
    private final CrudMenu clientCRUD;
    private final CrudMenu productCRUD;
    private final CrudMenu stateCRUD;
    private final Manager<Transaction> transactionManager;
    private final Factory<Transaction> transactionFactory;

    private Scanner sc;

    @Autowired
    public ConsoleMenu(Storage<Client> clientStorage,
                       Storage<Product> productStorage,
                       Storage<State> stateStorage,
                       Factory<Client> clientFactory,
                       Factory<Product> productFactory,
                       Factory<State> stateFactory,
                       Factory<Transaction> transactionFactory,
                       Manager<Transaction> transactionManager) {

        this.transactionManager = transactionManager;
        this.transactionFactory = transactionFactory;
        clientCRUD = new CrudMenu(clientStorage,clientFactory);
        productCRUD = new CrudMenu(productStorage,productFactory);
        stateCRUD = new CrudMenu(stateStorage,stateFactory);
        sc = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.print("### Main menu \n" +
                    " 1. Client  CRUD \n" +
                    " 2. Product CRUD \n" +
                    " 3. State   CRUD \n" +
                    " 4. Sell operation \n" +
                    " 0. exit \n" +
                    "> ");
            switch (sc.nextInt()) {
                case 1:
                    clientCRUD.run();
                    break;
                case 2:
                    productCRUD.run();
                    break;
                case 3:
                    stateCRUD.run();
                    break;
                case 4:
                    try {
                        Transaction t = transactionFactory.create(0);
                        transactionManager.execute(t);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    System.out.println("Bye.");
                    return;
                default:
                    System.out.println("No such menu item.");

            }
        }
    }


}
