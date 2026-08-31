package com.example.api.DTO;

import java.time.Instant;
import java.util.List;

import com.example.servicemonitoring.entity.MonitoredService;

public record ServiceHistoryDTO(
    long id, 
    String name, 
    String url,
    Instant from, 
    Instant to, 
    List<HealthCheckDTO> healthChecks
) {
    public ServiceHistoryDTO(MonitoredService service,Instant from, Instant to, List<HealthCheckDTO> healthChecks){
        this(
            service.getId(), 
            service.getName(), 
            service.getUrl(), 
            from, 
            to, 
            healthChecks
        );
    } 
}