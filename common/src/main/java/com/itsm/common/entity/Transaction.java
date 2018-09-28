package com.itsm.common.entity;

import java.util.Date;

public class Transaction {
    private final long id;
    private Client patient;
    private Product product;
    private Date datetime;

    public Transaction(long id, Client patient, Product product, Date datetime) {
        this.id = id;
        this.patient = patient;
        this.product = product;
        this.datetime = datetime;
    }

    public Transaction(Client patient, Product product) {
        this.id = 0;
        this.patient = patient;
        this.product = product;
        this.datetime = new Date();
    }

    public long getId() {
        return id;
    }

    public Client getPatient() {
        return patient;
    }

    public void setPatient(Client patient) {
        this.patient = patient;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", patient=" + patient +
                ", product=" + product +
                ", datetime=" + datetime +
                '}';
    }
}
