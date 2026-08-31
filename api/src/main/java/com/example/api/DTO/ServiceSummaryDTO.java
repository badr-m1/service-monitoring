package com.example.api.DTO;
import com.example.servicemonitoring.entity.Service;

public record ServiceSummaryDTO(
    long id, 
    String name, 
    String url, 
    HealthCheckDTO healthCheck
) {
    public ServiceSummaryDTO(Service service, HealthCheckDTO healthCheck){
        this(
            service.getId(), 
            service.getName(), 
            service.getUrl(), 
            healthCheck
        );
    } 
}