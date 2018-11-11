package com.itsm.frontend.core;

import com.itsm.common.entity.Transaction;
import com.itsm.common.entity.User;
import com.itsm.frontend.storage.interf.InterfaceTransactionStorage;
import com.itsm.frontend.storage.interf.InterfaceUserStorage;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;


public class TransactionListMenu implements TransactionListMenuInterface {
    private InterfaceTransactionStorage transactionStorage;
    private InterfaceUserStorage userStorage;

    public TransactionListMenu(InterfaceTransactionStorage transactionStorage, InterfaceUserStorage userStorage) {
        this.transactionStorage = transactionStorage;
        this.userStorage = userStorage;
    }

    @Override
    @Transactional
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Username you look for: ");
        String username = sc.nextLine();
        User user = userStorage.getByName(username);

        if( user == null ){
            System.out.println("No such user.");
            return;
        }

        List<Transaction> transactions = transactionStorage.getByUser(user);
        for (Transaction t : transactions) {
            System.out.printf("#%d: %s sold to %s(#%d) at %s%n", t.getId(), t.getProduct().getName(), t.getPatient().getName(), t.getPatient().getId(), t.getDatetime().toString());
        }

        System.out.printf("All in all %d transactions for %s.%n", transactions.size(), username);


    }
}
