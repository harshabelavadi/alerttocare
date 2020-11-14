package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.StaffDetails;

@Repository
public interface StaffDetailsRepository extends JpaRepository<StaffDetails, Long>{

}
