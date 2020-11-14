package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long>{

}
