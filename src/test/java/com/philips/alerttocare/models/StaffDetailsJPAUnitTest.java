package com.philips.alerttocare.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repositories.StaffDetailsRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This StaffDetailsJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of StaffDetails entity on
 * repository and finding them by Id's
 * and tested a customized method of StaffDetailsRepository
 * where in you can find list of StaffPeople 
 * based on their designation containing given string
 */

public class StaffDetailsJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  StaffDetailsRepository repository;

	  @Test
	  public void should_find_no_StaffDetails_if_repository_is_empty() {
	    Iterable<StaffDetails> staffdetails = repository.findAll();
  
	    assertThat(staffdetails).isEmpty();
	  }

	  @Test
	  public void should_store_a_StaffDetails() {
		  
		  StaffDetails staffdetail = new StaffDetails(1, "Pranay", "vasu123" , "Doctor" , true);
		  Date dateobj = staffdetail.getCreatedAt();
		  staffdetail.setAdminPrevilige(false);

		  assertThat(staffdetail).hasFieldOrPropertyWithValue("username", "Pranay");
		  assertThat(staffdetail).hasFieldOrPropertyWithValue("password", "vasu123");
		  assertThat(staffdetail).hasFieldOrPropertyWithValue("designation", "Doctor");
		  assertThat(staffdetail).hasFieldOrPropertyWithValue("adminPrevilige", false);
		  assertThat(staffdetail).hasFieldOrPropertyWithValue("createdAt", dateobj);
	  }

	  @Test
	  public void should_find_all_staffdetails() {
		StaffDetails staffdetail1 = new StaffDetails(1, "Pranay", "vasu123" , "Doctor" , true);
	    entityManager.persist(staffdetail1);

	    StaffDetails staffdetail2 = new StaffDetails(2, "Harsha", "ha123" , "Doctor" , true);
	    entityManager.persist(staffdetail2);

	    StaffDetails staffdetail3 = new StaffDetails(3, "Praveen", "pra123" , "Nurse" , false);
	    entityManager.persist(staffdetail3);

	    Iterable<StaffDetails> staffdetails = repository.findAll();
	    assertThat(staffdetails).hasSize(3).contains(staffdetail1, staffdetail2, staffdetail3);
	  }

	  @Test
	  public void should_find_staffdetail_by_id() {
		  
		  StaffDetails staffdetail1 = new StaffDetails(1, "Pranay", "vasu123" , "Doctor" , true);
		  entityManager.persist(staffdetail1);
		  
		  StaffDetails staffdetail2 = new StaffDetails(2, "Praveen", "pra123" , "Nurse" , false);
		  entityManager.persist(staffdetail2);

	      StaffDetails foundStaffDetails = repository.findById(2L).get();
	      assertThat(foundStaffDetails).isEqualTo(staffdetail2);
	  }
	
	  @Test
	  public void should_update_staffdetail_by_id() {
		  
		  StaffDetails staffdetail1 = new StaffDetails(1, "Praveen", "pra123" , "Nurse" , false);
		  entityManager.persist(staffdetail1);
		  
		  StaffDetails staffdetail2 = new StaffDetails(2, "Pranay", "vasu123" , "Doctor" , true);
		  entityManager.persist(staffdetail2);

	   	  StaffDetails updatedStaffDetails = new StaffDetails(3, "PranayBunari", "va@123" , "Doctor" , true);
	   	  entityManager.persist(updatedStaffDetails);

	   	  updatedStaffDetails.setUsername(staffdetail2.getUsername());
	   	  updatedStaffDetails.setPassword(staffdetail2.getPassword());
	   	  updatedStaffDetails.setDesignation(staffdetail2.getDesignation());
	   	  
	   	  assertThat(staffdetail2.getUsername()).isEqualTo(updatedStaffDetails.getUsername());
	   	  assertThat(staffdetail2.getPassword()).isEqualTo(updatedStaffDetails.getPassword());
	   	  assertThat(staffdetail2.getDesignation()).isEqualTo(updatedStaffDetails.getDesignation());
	   
	  }

	  @Test
	  public void should_delete_staffdetail_by_id() {
		  
		  StaffDetails staffdetail1 = new StaffDetails(1, "Pranay", "vasu123" , "Doctor" , true);
		  entityManager.persist(staffdetail1);
		  
		  StaffDetails staffdetail2 = new StaffDetails(2, "Praveen", "pra123" , "Nurse" , false);
		  entityManager.persist(staffdetail2);

		  StaffDetails staffdetail3 = new StaffDetails(3, "Harsha", "ha123" , "Doctor" , true);
		  entityManager.persist(staffdetail3);

		  repository.deleteById(2L);

		  Iterable<StaffDetails> staffdetails = repository.findAll();
		  assertThat(staffdetails).hasSize(2).contains(staffdetail1, staffdetail3);
	  }

	  @Test
	  public void should_delete_all_departments() {
		  
	    entityManager.persist(new StaffDetails(1, "Pranay", "vasu123" , "Doctor" , true));
	    entityManager.persist(new StaffDetails(2, "Praveen", "pra123" , "Nurse" , false));

	    repository.deleteAll();
	    assertThat(repository.findAll()).isEmpty();
	  }
}
