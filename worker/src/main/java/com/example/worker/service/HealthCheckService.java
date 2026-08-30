package com.example.worker.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.servicemonitoring.entity.EndpointStatus;
import com.example.servicemonitoring.entity.HealthCheck;
import com.example.servicemonitoring.entity.MonitoredEndpoint;
import com.example.servicemonitoring.repository.MonitoredEndpointRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HealthCheckService {
    
    private final MonitoredEndpointRepository monitoredEndpointRepository;
    private final WebClient webClient;

    public HealthCheckService(
        MonitoredEndpointRepository monitoredEndpointRepository,
        WebClient webClient){
        this.monitoredEndpointRepository = monitoredEndpointRepository;
        this.webClient = webClient;
    }

    public Flux<HealthCheck> checkAll(){
        List<MonitoredEndpoint> endpointList = monitoredEndpointRepository.findAll();

        return Flux.fromIterable(endpointList)
        .flatMap(this::check);

    }

    private Mono<HealthCheck> check(MonitoredEndpoint endpoint){
        Instant start = Instant.now();

        return webClient
        .get()
        .uri(endpoint.getUrl())
        .exchangeToMono(response -> {
            Instant end = Instant.now();
            int code = response.statusCode().value();
            EndpointStatus status = (code > 199 && code <= 299)? EndpointStatus.UP : EndpointStatus.DOWN;
            return Mono.just(
                new HealthCheck(
                    endpoint.getId(), 
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
                    endpoint.getId(), 
                    EndpointStatus.DOWN, 
                    -1,
                    Duration.between(start, end).toMillis(), 
                    start)
            );
        });
        
    }
}
