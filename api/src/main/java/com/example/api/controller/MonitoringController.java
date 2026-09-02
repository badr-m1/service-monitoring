package com.example.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.DTO.*;
import com.example.api.service.MonitoringService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/services")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceSummaryDTO createService(@RequestBody @Valid ServiceRequestDTO serviceRequest){
        return monitoringService.createService(serviceRequest.name(), serviceRequest.url());
    }

    @PutMapping("/{id}")
    public ServiceSummaryDTO updateService(@PathVariable @Positive long id, @RequestBody String name){
        return monitoringService.updateService(id, name);
    }

    @GetMapping
    public List<ServiceSummaryDTO> getAllServicesSummaries(){
        return monitoringService.getAllServicesSummaries();
    }

    @GetMapping("/{id}")
    public ServiceSummaryDTO getService(@PathVariable @Positive long id){
        return monitoringService.getService(id);
    }

    @GetMapping("/{id}/history")
    public ServiceHistoryDTO getServiceHistory(
        @PathVariable @Positive long id, 
        @RequestParam(required = false) Instant startTime,  
        @RequestParam(required = false) Instant endTime
    ){
        if(endTime == null) endTime = Instant.now();
        if(startTime == null) startTime = endTime.minus(24, ChronoUnit.HOURS);

        return monitoringService.getServiceHistory(id, startTime, endTime);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable @Positive long id){
        monitoringService.deleteService(id);
    }
    
    @GetMapping("/config")
    public long getConfig() {
        return monitoringService.getHealthCheckIntervalMS();
    }
    
}
