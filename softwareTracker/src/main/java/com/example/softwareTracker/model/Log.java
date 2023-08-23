package com.example.softwareTracker.model;

import com.example.softwareTracker.enumeration.LogType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Task task;

    private String message;

    @Column(nullable = false)
    private Date date;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private LogType logType;
}
