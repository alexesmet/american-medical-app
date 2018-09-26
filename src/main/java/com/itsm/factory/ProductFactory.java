package com.itsm.factory;

import com.itsm.entity.Client;
import com.itsm.entity.Product;
import com.itsm.entity.State;
import com.itsm.storage.StateStorage;
import com.itsm.storage.Storage;

import java.sql.SQLException;
import java.util.Scanner;

public class ProductFactory implements Factory<Product> {
    private final Storage<State> stateStorage;

    public ProductFactory(Storage<State> stateStorage) {
        this.stateStorage = stateStorage;
    }

    @Override
    public Product create(long id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Product's name: ");
        String name = sc.nextLine();
        System.out.print("Product's state id: ");
        long state_id = sc.nextLong();
        Product result = null;
        try {
            if (stateStorage.contains(state_id)){
                State state = stateStorage.get(state_id);
                result = new Product(id,name,state);
            } else {
                System.out.println("Looks like there is no such state id.");
            }
        } catch (SQLException e) {
            System.err.println("stateStorage reports: " + e.getMessage());
            System.err.println("Could not create object.");
        }
        return result;
    }
}
