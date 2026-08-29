package com.example.servicemonitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MonitoredEndpoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(
        nullable = false,
        unique = true
    )
    String name;

    @Column(
        nullable = false,
        unique = true
    )
    String url;

    @Column(nullable = false)
    boolean isActive = true;

}
