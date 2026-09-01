package com.example.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.DTO.*;
import com.example.api.service.MonitoringService;

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
    public ServiceDTO createService(@RequestBody ServiceRequestDTO serviceRequest){
        return monitoringService.createService(serviceRequest.name(), serviceRequest.url());
    }

    @PutMapping("/{id}")
    public ServiceDTO updateService(@PathVariable long id, @RequestBody ServiceRequestDTO serviceRequest){
        return monitoringService.updateService(id, serviceRequest.name(), serviceRequest.url());
    }

    @GetMapping
    public List<ServiceSummaryDTO> getAllServicesSummaries(){
        return monitoringService.getAllServicesSummaries();
    }

    @GetMapping("/{id}")
    public ServiceSummaryDTO getService(@PathVariable long id){
        return monitoringService.getService(id);
    }

    @GetMapping("/{id}/history")
    public ServiceHistoryDTO getServiceHistory(@PathVariable long id, @RequestParam(defaultValue = "0") int page){
        return monitoringService.getServiceHistory(id, 0);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable long id){
        monitoringService.deleteService(id);
    }
    
    @GetMapping("/config")
    public int getConfig() {
        return monitoringService.getHealthCheckIntervalSeconds();
    }
    
}
