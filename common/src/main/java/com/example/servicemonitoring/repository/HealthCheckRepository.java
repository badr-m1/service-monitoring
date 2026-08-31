package com.example.servicemonitoring.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servicemonitoring.entity.HealthCheck;

public interface HealthCheckRepository extends JpaRepository<HealthCheck, Long>{
    
    List<HealthCheck> findByServiceIdOrderByCheckedAtDesc(long serviceId, Pageable pageable);

} 