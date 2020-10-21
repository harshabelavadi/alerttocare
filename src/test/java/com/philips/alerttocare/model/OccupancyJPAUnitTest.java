package com.philips.alerttocare.model;


import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import java.util.List;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.OccupancyRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This OccupancyJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Occupancy entities on
 * repository and finding them by Id's
 */

public class OccupancyJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  OccupancyRepository repository;

	  @Test
	  public void should_find_no_Occupancy_if_repository_is_empty() {
		  
	    Iterable<Occupancy> occupancies = repository.findAll();
	    assertThat(occupancies).isEmpty();
	    
	  }

	  @Test
	  public void should_store_a_Occupancy() {
		  Date dischargeDate = new Date();
		  Icu icu = new Icu();
		  Bed bed = new Bed();
		  Patient patient = new Patient();		  
		  
		  Occupancy occupancy = new Occupancy(1, dischargeDate , bed , patient, icu);
		  Date dateobj = occupancy.getOccupiedAt();
		  
		  assertThat(occupancy).hasFieldOrPropertyWithValue("dischargedAt", dischargeDate);
		  assertThat(occupancy).hasFieldOrPropertyWithValue("bed", bed);
		  assertThat(occupancy).hasFieldOrPropertyWithValue("patient", patient);
		  assertThat(occupancy).hasFieldOrPropertyWithValue("icu", icu);
		  assertThat(occupancy).hasFieldOrPropertyWithValue("occupiedAt", dateobj);
	  }

	  @Test
	  public void should_find_all_Occupancies() {
		  
		  Date dischargeDate1 = new Date();
		  Date dischargeDate2 = new Date();
		  Date dischargeDate3 = new Date();
		  
		  Occupancy occupancy1 = new Occupancy(1, dischargeDate1, null, null, null);
		  entityManager.persist(occupancy1);

		  Occupancy occupancy2 = new Occupancy(2, dischargeDate2, null, null, null);
	   	  entityManager.persist(occupancy2);

	   	  Occupancy occupancy3 = new Occupancy(3, dischargeDate3, null, null, null);
	   	  entityManager.persist(occupancy3);

	   	  Iterable<Occupancy> occupancys = repository.findAll();
	   	  assertThat(occupancys).hasSize(3).contains(occupancy1, occupancy2, occupancy3);
	  }

	  @Test
	  public void should_find_occupancy_by_id() {
		  
		  Date dischargeDate1 = new Date();
		  Date dischargeDate2 = new Date();
		  
		  Icu icu = new Icu();
		  Bed bed = new Bed();
		  Patient patient = new Patient();	
		  
		  Occupancy occupancy1 = new Occupancy(1, dischargeDate1 , bed , patient, icu);
		  entityManager.persist(occupancy1);

		  Occupancy occupancy2 = new Occupancy(2, dischargeDate2 , bed , patient, icu);
	   	  entityManager.persist(occupancy2);

	   	  Occupancy foundOccupancy = repository.findById(2L).get();
	   	  assertThat(foundOccupancy).isEqualTo(occupancy2);
	  }


	  @Test
	  public void should_update_occupancy_by_id() {
		 
		  Date dischargeDate1 = new Date();
		  Date dischargeDate2 = new Date();
		  Date dischargeDate3 = new Date();
		  
		  Occupancy occupancy1 = new Occupancy(1, dischargeDate1, null, null, null);
	      entityManager.persist(occupancy1);
	      
	      Occupancy occupancy2 =new Occupancy(2, dischargeDate2, null, null, null);
	      entityManager.persist(occupancy2);

	      Occupancy updatedOccupancy = new Occupancy(3, dischargeDate3, null, null, null);
	      entityManager.persist(updatedOccupancy);
	      
		  updatedOccupancy.setPatient(occupancy2.getPatient());
		  updatedOccupancy.setDischargedAt(occupancy2.getDischargedAt());
		  updatedOccupancy.setBed(occupancy2.getBed());
		  updatedOccupancy.setIcu(occupancy2.getIcu());
	    
		  assertThat(occupancy2.getPatient()).isEqualTo(updatedOccupancy.getPatient());
		  assertThat(occupancy2.getDischargedAt()).isEqualTo(updatedOccupancy.getDischargedAt());
		  assertThat(occupancy2.getBed()).isEqualTo(updatedOccupancy.getBed());
		  assertThat(occupancy2.getIcu()).isEqualTo(updatedOccupancy.getIcu());

	  }

	  @Test
	  public void should_delete_occupancy_by_id() {
			 
		  Date dischargeDate1 = new Date();
		  Date dischargeDate2 = new Date();
		  Date dischargeDate3 = new Date();
		  
		  Occupancy occupancy1 = new Occupancy(1, dischargeDate1, null, null, null);
	      entityManager.persist(occupancy1);

	      Occupancy occupancy2 = new Occupancy(2, dischargeDate2, null, null, null);
	      entityManager.persist(occupancy2);

	      Occupancy occupancy3 = new Occupancy(3, dischargeDate3, null, null, null);
	      entityManager.persist(occupancy3);

	      repository.deleteById(occupancy2.getId());

	      Iterable<Occupancy> occupancys = repository.findAll();
	      assertThat(occupancys).hasSize(2).contains(occupancy1, occupancy3);
	  }

	  @Test
	  public void should_delete_all_occupancy() {
		  
		  Date dischargeDate1 = new Date();
		  Date dischargeDate2 = new Date();
		  Date dischargeDate3 = new Date();
		  
		  Occupancy occupancy1 = new Occupancy(1, dischargeDate1, null, null, null);
	      entityManager.persist(occupancy1);

	      Occupancy occupancy2 = new Occupancy(2, dischargeDate2, null, null, null);
	      entityManager.persist(occupancy2);

	      Occupancy occupancy3 = new Occupancy(3, dischargeDate3, null, null, null);
	      entityManager.persist(occupancy3);

		  repository.deleteAll();
		  assertThat(repository.findAll()).isEmpty();
	  }

}
