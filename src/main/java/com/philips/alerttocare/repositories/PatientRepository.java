package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

}
