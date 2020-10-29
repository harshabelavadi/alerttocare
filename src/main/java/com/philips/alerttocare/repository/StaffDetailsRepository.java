package com.philips.alerttocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.StaffDetails;

@Repository
public interface StaffDetailsRepository extends JpaRepository<StaffDetails, Long>{

}
