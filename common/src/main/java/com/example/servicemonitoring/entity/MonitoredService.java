package com.example.servicemonitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
public class MonitoredService {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(
        nullable = false,
        unique = true
    )
    String name;

    @Column(
        nullable = false,
        unique = true
    )
    String url;

    @Column(nullable = false)
    boolean isActive = true;

    public MonitoredService(String name, String url, boolean isActive){
        this.name = name;
        this.url = url;
        this.isActive = isActive;
    }

}
