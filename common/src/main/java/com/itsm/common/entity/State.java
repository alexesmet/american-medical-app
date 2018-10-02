package com.itsm.common.entity;

import java.util.Objects;

public class State {
    private final long id;
    private int code;
    private String name;

    public State(long id) {
        this.id = id;
    }

    public State(long id, byte code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return getId() == state.getId() &&
                getCode() == state.getCode() &&
                Objects.equals(getName(), state.getName());
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
