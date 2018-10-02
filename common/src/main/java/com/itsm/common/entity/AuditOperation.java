package com.itsm.common.entity;

import java.util.Date;

public class AuditOperation {
    private long id;
    private Date date;
    private boolean successful;
    private String action;

    public AuditOperation() {}

    public AuditOperation(long id, Date date, boolean successful, String action) {
        this.id = id;
        this.date = date;
        this.successful = successful;
        this.action = action;
    }

    public AuditOperation(boolean successful, String action) {
        this.id = 0;
        this.date = new Date();
        this.successful = successful;
        this.action = action;
    }

    //Constructor for MyBatis in report-generator
    public AuditOperation(Long id, java.sql.Timestamp date, Boolean successful, String action){
        this.id = id;
        this.date = new Date(date.getTime());
        this.successful = successful;
        this.action = action;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "AuditOperation{" +
                "id=" + id +
                ", date=" + date +
                ", successful=" + successful +
                ", action='" + action + '\'' +
                '}';
    }
}
