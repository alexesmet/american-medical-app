package com.itsm.common.entity;

import java.util.Objects;

public class Client {
    private final long id;
    private String name;
    private String phone;
    private State state;

    public Client(long id, String name, String phone, State state) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getId() == client.getId() &&
                Objects.equals(getName(), client.getName()) &&
                Objects.equals(getPhone(), client.getPhone()) &&
                Objects.equals(getState(), client.getState());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", state=" + state +
                '}';
    }
}
