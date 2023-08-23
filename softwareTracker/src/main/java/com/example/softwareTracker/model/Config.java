package com.example.softwareTracker.model;

import com.example.softwareTracker.enumeration.ConfigType;
import com.example.softwareTracker.enumeration.TaskType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfigType configType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    @OneToMany(mappedBy = "config", cascade = CascadeType.ALL, targetEntity = Parameter.class)
    @JsonManagedReference
    private List<Parameter> parameters;
    @Column
    private String username;
    @ManyToOne
    @JoinColumn(name = "hostname",referencedColumnName = "hostname")
    private Machine machine;

}




