package com.example.worker.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.servicemonitoring.entity.ServiceStatus;
import com.example.servicemonitoring.entity.HealthCheck;
import com.example.servicemonitoring.entity.Service;
import com.example.servicemonitoring.repository.ServiceRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HealthCheckService {
    
    private final ServiceRepository serviceRepository;
    private final WebClient webClient;

    public HealthCheckService(
        ServiceRepository serviceRepository,
        WebClient webClient){
        this.serviceRepository = serviceRepository;
        this.webClient = webClient;
    }

    public Flux<HealthCheck> checkAll(){
        List<Service> services = serviceRepository.findAll();

        return Flux.fromIterable(services)
        .flatMap(this::check);

    }

    private Mono<HealthCheck> check(Service service){
        Instant start = Instant.now();

        return webClient
        .get()
        .uri(service.getUrl())
        .exchangeToMono(response -> {
            Instant end = Instant.now();
            int code = response.statusCode().value();
            ServiceStatus status = (code > 199 && code <= 299)? ServiceStatus.UP : ServiceStatus.DOWN;
            return Mono.just(
                new HealthCheck(
                    service.getId(), 
                    status, 
                    code,
                    Duration.between(start, end).toMillis(), 
                    start)
            );
        })
        .onErrorResume(exception -> {
            Instant end = Instant.now();
            return Mono.just(
                new HealthCheck(
                    service.getId(), 
                    ServiceStatus.DOWN, 
                    -1,
                    Duration.between(start, end).toMillis(), 
                    start)
            );
        });
        
    }
}
