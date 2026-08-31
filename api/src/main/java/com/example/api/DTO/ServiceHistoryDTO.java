package com.example.api.DTO;

import java.util.List;

import com.example.servicemonitoring.entity.MonitoredService;

public record ServiceHistoryDTO(
    long id, 
    String name, 
    String url,
    List<HealthCheckDTO> healthChecks
) {
    public ServiceHistoryDTO(MonitoredService service, List<HealthCheckDTO> healthChecks){
        this(
            service.getId(), 
            service.getName(), 
            service.getUrl(), 
            healthChecks
        );
    } 
}