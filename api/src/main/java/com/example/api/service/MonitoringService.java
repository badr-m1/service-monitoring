package com.example.api.service;

import com.example.api.DTO.*;
import com.example.servicemonitoring.entity.HealthCheck;
import com.example.servicemonitoring.entity.MonitoredService;
import com.example.servicemonitoring.repository.HealthCheckRepository;
import com.example.servicemonitoring.repository.MonitoredServiceRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {
    
    private static final int PAGE_SIZE = 100;

    @Value("${health-check.interval-seconds}")
    private int healthCheckIntervalSeconds;

    private final HealthCheckRepository healthCheckRepository;
    private final MonitoredServiceRepository serviceRepository;

    public MonitoringService(
        HealthCheckRepository healthCheckRepository, 
        MonitoredServiceRepository serviceRepository
    ){
        this.healthCheckRepository = healthCheckRepository;
        this.serviceRepository = serviceRepository;
    }
    
    public ServiceDTO createService(String name, String url){
        MonitoredService newService = new MonitoredService(name, url);
        MonitoredService saved = serviceRepository.save(newService);
        return new ServiceDTO(saved.getId(), saved.getName(), saved.getUrl());
    }

    public ServiceDTO updateService(long id, String name, String url){
        Optional<MonitoredService> serviceQurey = serviceRepository.findById(id);

        if(serviceQurey.isEmpty()){
            return new ServiceDTO(-1, null, null);
        }
        
        MonitoredService service = serviceQurey.get();
        service.setName(name);
        service.setUrl(url);
        MonitoredService saved = serviceRepository.save(service);
        return new ServiceDTO(saved.getId(), saved.getName(), saved.getUrl());
        
    }

    public List<ServiceSummaryDTO> getAllServicesSummaries(){
        List<MonitoredService> servs = serviceRepository.findAll();
        List<HealthCheck> healthChecks = healthCheckRepository.findLatestHealthCheckPerService();
        Map<Long, HealthCheck> healthCheckMap = healthChecks.stream()
        .collect(Collectors.toMap(hc->hc.getServiceId(), hc->hc));
        
        List<ServiceSummaryDTO> summaries = servs.stream()
        .map(service ->{
            HealthCheck hc = healthCheckMap.get(service.getId());
            HealthCheckDTO hcDTO = (hc == null)? null : new HealthCheckDTO(hc);
            return new ServiceSummaryDTO(service, hcDTO);
        })
        .toList();

        return summaries;
    }
    
    public ServiceSummaryDTO getService(long id){
        Optional<MonitoredService> serviceQurey = serviceRepository.findById(id);

        if(serviceQurey.isEmpty()){
            return new ServiceSummaryDTO(-1, null, null, null);
        }
        
        MonitoredService service = serviceQurey.get();
        Optional<HealthCheck> healthCheckQuery = healthCheckRepository.findFirstByServiceIdOrderByCheckedAtDesc(service.getId());
        HealthCheckDTO hcDTO = healthCheckQuery.isPresent()? new HealthCheckDTO(healthCheckQuery.get()) : null;
        return new ServiceSummaryDTO(service, hcDTO);
    }

    public ServiceHistoryDTO getServiceHistory(long id, int page){
        Optional<MonitoredService> serviceQurey = serviceRepository.findById(id);

        if(serviceQurey.isEmpty()){
            return new ServiceHistoryDTO(-1, null, null, null);
        }
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        MonitoredService service = serviceQurey.get();
        List<HealthCheckDTO> healthCheckDTOs = healthCheckRepository
        .findByServiceIdOrderByCheckedAtDesc(service.getId(), pageable)
        .stream()
        .map(hc -> new HealthCheckDTO(hc))
        .toList();
        
        return new ServiceHistoryDTO(service, healthCheckDTOs);
    }

    public void deleteService(long id){
        Optional<MonitoredService> serviceQurey = serviceRepository.findById(id);
        if(serviceQurey.isEmpty()){return;}
        serviceRepository.deleteById(id);
    }

    public int getHealthCheckIntervalSeconds(){
        return healthCheckIntervalSeconds;
    }

}
