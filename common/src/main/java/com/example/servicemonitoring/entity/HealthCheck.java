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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "health_checks")
@Getter
@Setter
@NoArgsConstructor
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
    long responseTime;

    @Column(nullable = false)
    Instant checkedAt;

    public HealthCheck(
            long endpointId,
            EndpointStatus status,
            int statusCode,
            long responseTime,
            Instant checkedAt
    ) {
        this.endpointId = endpointId;
        this.status = status;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.checkedAt = checkedAt;
    }

    
}
