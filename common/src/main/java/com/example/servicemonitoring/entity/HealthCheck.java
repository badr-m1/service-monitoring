package com.example.servicemonitoring.entity;

import java.time.Duration;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HealthCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    long endpointId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    EndpointStatus status;

    @Column(nullable = false)
    int statusCode;

    @Column(nullable = false)
    Duration responseTime;

    @Column(nullable = false)
    Instant checkedAt;
    
}
