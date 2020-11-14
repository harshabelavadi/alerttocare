package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.HealthStatus;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long>{

}
