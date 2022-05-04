package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "audit_table")
public class AuditLogging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "request_body")
    private String request;

    @Column(name = "calling_method")
    private String callingMethod;

    @Column(name = "creation_date")
    private Date creationDate;
}



