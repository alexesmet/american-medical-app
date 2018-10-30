package com.itsm.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.portable.IDLEntity;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "auditOperation")
@Table(name = "audit_operations")
public class AuditOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private Date date;

    @Column(name = "success")
    private boolean successful;
    private String action;

    public AuditOperation(boolean successful, String action) {
        this.date = new Date();
        this.successful = successful;
        this.action = action;
    }
}
