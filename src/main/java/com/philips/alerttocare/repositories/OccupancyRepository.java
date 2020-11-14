package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.Occupancy;

@Repository
public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {

}