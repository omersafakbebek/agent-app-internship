package com.example.softwareTracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Entity
@Table
@Data
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column(unique = true, nullable = false)
    private String hostname;
    @Column
    private Date lastSeen;
    @Column
    private int last_hb;
    @Transient
    private boolean agentStatus;

    public boolean isAgentStatus() {
        return ((new Date()).getTime() - lastSeen.getTime()) < 5*60*1000;
    }
}

