package com.itsm.factory;

import com.itsm.entity.Client;
import com.itsm.entity.State;

import java.sql.SQLException;
import java.util.Scanner;

public class StateFactory implements Factory<State> {

    @Override
    public State create(long id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("State's name: ");
        String name = sc.nextLine();
        System.out.print("State's code: ");
        byte code = sc.nextByte();
        State result = new State(id,code,name);
        return result;
    }
}
