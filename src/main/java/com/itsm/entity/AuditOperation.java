package com.itsm.entity;

import java.sql.Date;

public class AuditOperation {
    private final long id;
    private Date date;
    private boolean successful;
    private String action;

    public AuditOperation(long id, Date date, boolean successful, String action) {
        this.id = id;
        this.date = date;
        this.successful = successful;
        this.action = action;
    }

    public AuditOperation(boolean successful, String action) {
        this.id = 0;
        this.date = new Date(new java.util.Date().getTime());
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
