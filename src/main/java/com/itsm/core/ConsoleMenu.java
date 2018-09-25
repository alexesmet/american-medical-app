package com.itsm.core;


import com.itsm.factory.ClientFactory;
import com.itsm.factory.ProductFactory;
import com.itsm.factory.StateFactory;
import com.itsm.storage.ClientStorage;
import com.itsm.storage.ProductStorage;
import com.itsm.storage.StateStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Scanner;

@Component("ConsoleMenu")
public class ConsoleMenu implements Runnable{
    private final CrudMenu clientCRUD;
    private final CrudMenu productCRUD;
    private final CrudMenu stateCRUD;
    private final TransactionManager transactionManager;

    private Scanner sc;

    @Autowired
    public ConsoleMenu(ClientStorage clientStorage,
                       ProductStorage productStorage,
                       StateStorage stateStorage,
                       ClientFactory clientFactory,
                       ProductFactory productFactory,
                       StateFactory stateFactory,
                       TransactionManager transactionManager) {

        this.transactionManager = transactionManager;
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
                        transactionManager.transact();
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
