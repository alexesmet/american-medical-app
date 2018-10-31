package com.itsm.frontend.factory;

import com.itsm.common.entity.State;

import java.util.Scanner;

public class StateFactory implements Factory<State> {

    @Override
    public State create(long id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("State's name: ");
        String name = sc.nextLine();
        System.out.print("State's code: ");
        byte code = sc.nextByte();
        return new State(id,code,name);
    }
}
