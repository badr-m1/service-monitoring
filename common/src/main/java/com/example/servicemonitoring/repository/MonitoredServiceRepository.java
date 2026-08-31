package com.example.servicemonitoring.repository;


import com.example.servicemonitoring.entity.MonitoredService;
import org.springframework.data.jpa.repository.*;

public interface MonitoredServiceRepository extends JpaRepository<MonitoredService, Long> {
    
}
