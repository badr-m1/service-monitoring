package com.example.api.service;

import com.example.api.DTO.*;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.exception.ServiceUnreachableException;
import com.example.servicemonitoring.entity.HealthCheck;
import com.example.servicemonitoring.entity.MonitoredService;
import com.example.servicemonitoring.entity.ServiceStatus;
import com.example.servicemonitoring.repository.HealthCheckRepository;
import com.example.servicemonitoring.repository.MonitoredServiceRepository;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {
    
    @Value("${health.check.interval.ms}")
    private long healthCheckIntervalMS;

    @Value("${health.check.timeout.ms}")
    private int connectionTimeoutMS;
    
    private final RestClient restClient;
    private final HealthCheckRepository healthCheckRepository;
    private final MonitoredServiceRepository serviceRepository; 

    public MonitoringService(
        HealthCheckRepository healthCheckRepository, 
        MonitoredServiceRepository serviceRepository,
        RestClient.Builder restClientBuilder
    ){
        this.healthCheckRepository = healthCheckRepository;
        this.serviceRepository = serviceRepository;

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(connectionTimeoutMS);
        requestFactory.setReadTimeout(connectionTimeoutMS);

        this.restClient = restClientBuilder
        .requestFactory(requestFactory)
        .build();

    }
    
    public ServiceSummaryDTO createService(String name, String url){
        HealthCheck healthCheck = getHealthCheck(url);

        MonitoredService newService = new MonitoredService(name, url);
        MonitoredService saved = serviceRepository.save(newService);
        healthCheck.setServiceId(saved.getId());
        
        healthCheckRepository.save(healthCheck);

        return new ServiceSummaryDTO(saved, new HealthCheckDTO(healthCheck));
    }

    public ServiceSummaryDTO updateService(long id, String name){
        MonitoredService service = serviceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Service with id " + id + " not found"
        ));

        service.setName(name);
        MonitoredService saved = serviceRepository.save(service);
        Optional<HealthCheck> healthCheck = healthCheckRepository.findFirstByServiceIdOrderByCheckedAtDesc(id);
        HealthCheckDTO hcDTO = (healthCheck.isEmpty())? null : new HealthCheckDTO(healthCheck.get());

        return new ServiceSummaryDTO(service, hcDTO);  
    }

    public List<ServiceSummaryDTO> getAllServicesSummaries(){
        List<MonitoredService> servs = serviceRepository.findAll();
        List<HealthCheck> healthChecks = healthCheckRepository.findLatestHealthCheckPerService();
        Map<Long, HealthCheck> healthCheckMap = healthChecks.stream()
        .collect(Collectors.toMap(hc->hc.getServiceId(), hc->hc));
        
        List<ServiceSummaryDTO> summaries = servs.stream()
        .map(service ->{
            HealthCheck hc = healthCheckMap.get(service.getId());
            HealthCheckDTO hcDTO = (hc == null)? null : new HealthCheckDTO(hc); //this could break the frontend
            return new ServiceSummaryDTO(service, hcDTO);
        })
        .toList();

        return summaries;
    }
    
    public ServiceSummaryDTO getService(long id){
        MonitoredService service = serviceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Service with id " + id + " not found"
        ));

        Optional<HealthCheck> healthCheckQuery = healthCheckRepository.findFirstByServiceIdOrderByCheckedAtDesc(service.getId());
        HealthCheckDTO hcDTO = healthCheckQuery.isPresent()? new HealthCheckDTO(healthCheckQuery.get()) : null;
        return new ServiceSummaryDTO(service, hcDTO);
    }

    public ServiceHistoryDTO getServiceHistory(long id, Instant startTime, Instant endTime){
        MonitoredService service = serviceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Service with id " + id + " not found"
        ));

        List<HealthCheckDTO> healthCheckDTOs = healthCheckRepository
        .findByServiceIdAndCheckedAtBetweenOrderByCheckedAtDesc(service.getId(), startTime, endTime)
        .stream()
        .map(hc -> new HealthCheckDTO(hc))
        .toList();
        
        return new ServiceHistoryDTO(service, healthCheckDTOs);
    }

    public void deleteService(long id){
        MonitoredService service = serviceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Service with id " + id + " not found"
        ));

        serviceRepository.deleteById(id);
    }

    public long getHealthCheckIntervalMS(){
        return healthCheckIntervalMS;
    }

    private HealthCheck getHealthCheck(String url) {
        Instant start = Instant.now();

        try {
            ResponseEntity<Void> response = restClient.get()
            .uri(url)
            .retrieve()
            .toBodilessEntity();
            int code = response.getStatusCode().value();

            ServiceStatus status = (code > 199 && code <= 299)? ServiceStatus.UP : ServiceStatus.DOWN;
            Instant end = Instant.now();

            return new HealthCheck(
                -1,
                status,
                code,
                Duration.between(start, end).toMillis(),
                Instant.now()
            );

        } catch (Exception e) {
            throw new ServiceUnreachableException("Could not connect to URL: " + url);
        }
    }

}
