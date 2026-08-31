package com.example.api.DTO;
import com.example.servicemonitoring.entity.MonitoredService;

public record ServiceSummaryDTO(
    long id, 
    String name, 
    String url, 
    HealthCheckDTO healthCheck
) {
    public ServiceSummaryDTO(MonitoredService service, HealthCheckDTO healthCheck){
        this(
            service.getId(), 
            service.getName(), 
            service.getUrl(), 
            healthCheck
        );
    } 
}