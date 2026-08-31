package com.example.servicemonitoring.repository;


import com.example.servicemonitoring.entity.Service;
import org.springframework.data.jpa.repository.*;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    
}
