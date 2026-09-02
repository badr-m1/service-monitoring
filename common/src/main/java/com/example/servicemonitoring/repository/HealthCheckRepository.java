package com.example.servicemonitoring.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.servicemonitoring.entity.HealthCheck;

public interface HealthCheckRepository extends JpaRepository<HealthCheck, Long>{
    
    List<HealthCheck> findByServiceIdAndCheckedAtBetweenOrderByCheckedAtDesc(long serviceId, Instant startTime, Instant endTime);
    
    Optional<HealthCheck> findFirstByServiceIdOrderByCheckedAtDesc(long serviceId);

    @Query(value = """
    SELECT DISTINCT ON (service_id) *
    FROM health_checks
    ORDER BY service_id, checked_at DESC
    """, nativeQuery = true)
    List<HealthCheck> findLatestHealthCheckPerService();

} 