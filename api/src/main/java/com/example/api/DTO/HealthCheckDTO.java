package com.example.api.DTO;

import java.time.Instant;

import com.example.servicemonitoring.entity.HealthCheck;
import com.example.servicemonitoring.entity.ServiceStatus;

public record HealthCheckDTO(long id, ServiceStatus status, int statusCode, long responseTime, Instant checkedAt) {
    public HealthCheckDTO(HealthCheck healthCheck){
        this(
            healthCheck.getId(), 
            healthCheck.getStatus(), 
            healthCheck.getStatusCode(), 
            healthCheck.getResponseTime(), 
            healthCheck.getCheckedAt()
        );
    }
} 