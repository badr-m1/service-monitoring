package com.example.servicemonitoring.repository;


import com.example.servicemonitoring.entity.MonitoredEndpoint;
import org.springframework.data.jpa.repository.*;

public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {
    
}
