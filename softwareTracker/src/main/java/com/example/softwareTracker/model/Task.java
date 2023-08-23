package com.example.softwareTracker.model;

import com.example.softwareTracker.enumeration.ThreadStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "hostname",nullable = false)
    private Machine machine;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Config config;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThreadStatus threadStatus;

    private Date date;

}
