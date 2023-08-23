package com.example.softwareTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"config_id", "name"}))
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(targetEntity = Config.class)
    @JoinColumn(name = "config_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Config config;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;
}
