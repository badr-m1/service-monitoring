package com.example.worker.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.servicemonitoring.repository.HealthCheckRepository;

import reactor.core.publisher.Mono;

@Service
public class MonitoringService {
    
    private final HealthCheckService healthCheckService;
    private final HealthCheckRepository healthCheckRepository;
    
    public MonitoringService(HealthCheckService healthCheckService, HealthCheckRepository healthCheckRepository) {
        this.healthCheckService = healthCheckService;
        this.healthCheckRepository = healthCheckRepository;
    }

    @Scheduled(fixedRate = 60_000)
    public void monitorEndpoints(){
        healthCheckService
        .checkAll()
        .collectList()
        .flatMapMany(healthChecks -> Mono.fromCallable(() -> healthCheckRepository.saveAll(healthChecks)))
        .subscribe();
    }
}
