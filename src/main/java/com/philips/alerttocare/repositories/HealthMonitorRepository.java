package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.HealthMonitor;


@Repository
public interface HealthMonitorRepository extends JpaRepository<HealthMonitor, Long>{

}
