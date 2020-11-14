package com.philips.alerttocare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.models.Icu;

@Repository
public interface IcuRepository extends JpaRepository<Icu, Long> {
}
