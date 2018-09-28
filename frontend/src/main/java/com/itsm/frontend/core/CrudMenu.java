package com.itsm.frontend.core;

import com.itsm.frontend.storage.Storage;
import com.itsm.frontend.factory.Factory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CrudMenu<T> implements Runnable{

    private final Storage<T> storage;
    private final Factory<T> factory;

    public CrudMenu(Storage<T> storage, Factory<T> factory) {
        this.storage = storage;
        this.factory = factory;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.print("### Crud menu for " + storage.getClass().getSimpleName() + " \n" +
                " 1. Create new \n" +
                " 2. Read all \n" +
                " 3. Update one \n" +
                " 4. Delete one \n" +
                " 0. Back to main menu \n" +
                "> ");
        try {
            switch (sc.nextInt()) {
                case 1:
                    T item = factory.create(0);
                    if (item != null){
                        storage.add(item);
                        System.out.println("Done.");
                    } else {
                        System.out.println("Aborted.");
                    }

                    break;
                case 2:
                    List<T> all = storage.getAll();
                    for (T one : all) {
                        System.out.println("- " + one.toString());
                    }
                    System.out.println("Done: printed " + all.size() + " elements.");
                    break;
                case 3:
                    System.out.print("Client to update: ");
                    long id = sc.nextLong();
                    if (storage.contains(id)){
                        T updated = factory.create(id);
                        if (updated != null) {
                            storage.update(updated);
                            System.out.println("Done.");
                        }
                    } else {
                        System.out.println("Cannot update - we have no element with this id.");
                    }
                    break;
                case 4:
                    System.out.print("Element to update: ");
                    long id1 = sc.nextLong();
                    if (storage.contains(id1)){
                        storage.delete(id1);
                        System.out.println("Done.");
                    } else {
                        System.out.println("Cannot delete - no such element in database");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("No such menu item");
            }
        } catch (SQLException e) {
            System.out.println("!!! SQLException: " + e.getMessage());
        }

    }
}
