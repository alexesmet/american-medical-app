package com.itsm.frontend.factory;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.State;
import com.itsm.frontend.storage.Storage;

import java.sql.SQLException;
import java.util.Scanner;

public class ClientFactory implements Factory<Client> {

    private final Storage<State> stateStorage;

    public ClientFactory(Storage<State> stateStorage) {
        this.stateStorage = stateStorage;
    }

    @Override
    public Client create(long id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Client's name: ");
        String name = sc.nextLine();
        System.out.print("Client's phone: ");
        String phone = sc.nextLine();
        System.out.print("Client's state id: ");
        long state_id = sc.nextLong();
        Client result = null;
        try {
            if (stateStorage.contains(state_id)){
                State state = stateStorage.get(state_id);
                result = new Client(id,name,phone,state);
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
