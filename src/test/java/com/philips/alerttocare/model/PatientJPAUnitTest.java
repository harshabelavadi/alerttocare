package com.philips.alerttocare.model;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.PatientRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This PatientJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Patient entities on
 * repository and finding them by Id's
 * and tested a customized method of PatientRepository
 * where in you can find list of patients 
 * based on their name containing given string
 */

public class PatientJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  PatientRepository repository;

	  @Test
	  public void should_find_no_Patient_if_repository_is_empty() {
	    Iterable<Patient> patients = repository.findAll();

	    assertThat(patients).isEmpty();
	  }

	  @Test
	  public void should_store_a_Patient() {
		  		  
		  Patient patient = new Patient(1, "#patientname", "#pataddress" , 28 , "male" , "9885827367" , null);
		  Date dateobj = patient.getCreatedAt();

		  assertThat(patient).hasFieldOrPropertyWithValue("name", "#patientname");
		  assertThat(patient).hasFieldOrPropertyWithValue("address", "#pataddress");
		  assertThat(patient).hasFieldOrPropertyWithValue("age", 28);
		  assertThat(patient).hasFieldOrPropertyWithValue("gender", "male");
		  assertThat(patient).hasFieldOrPropertyWithValue("contact", "9885827367");
		  assertThat(patient).hasFieldOrPropertyWithValue("createdAt", dateobj);
	    
	  }

	  @Test
	  public void should_find_all_patients() {
		  
		Patient patient1 = new Patient(1, "#patientname1", "#pataddress1" , 28 , "male" , "9885827367" ,null);
	    entityManager.persist(patient1);

	    Patient patient2 = new Patient(2, "#patientname2", "#pataddress2" , 21 , "female" , "988988582" ,null);
	    entityManager.persist(patient2);

	    Patient patient3 = new Patient(3, "#patientname3", "#pataddress3" , 23 , "female" , "9059343595" , null);
	    entityManager.persist(patient3);

	    Iterable<Patient> patients = repository.findAll();
	    assertThat(patients).hasSize(3).contains(patient1, patient2, patient3);
	  }

	  @Test
	  public void should_find_patient_by_id() {
		 
		Patient patient1 = new Patient(1, "#patientname", "#pataddress" , 28 , "male" , "9885827367" , null);
	    entityManager.persist(patient1);

	    Patient patient2 = new Patient(2, "#patientname", "#pataddress" , 28 , "male" , "9059343595" , null);
	    entityManager.persist(patient2);

	    Patient foundPatient = repository.findById(2L).get();
	    assertThat(foundPatient).isEqualTo(patient2);
	  }
	  
	
	  @Test
	  public void should_update_patient_by_id() {
		  
		  Patient patient1 = new Patient(1, "#patientname1", "#pataddress1" , 28 , "male" , "9885827367" ,null);
		  entityManager.persist(patient1);

		  Patient patient2 = new Patient(2, "#patientname2", "#pataddress2" , 21 , "female" , "988988582" ,null);
		  entityManager.persist(patient2);

		  Patient updatedPatient = new Patient(3, "#Updatespatientname", "#Updatedpataddress" , 20 , "male" , "98367" , null);
		  entityManager.persist(updatedPatient);

		  updatedPatient.setName(patient2.getName());
		  updatedPatient.setAddress(patient2.getAddress());
		  updatedPatient.setAge(patient2.getAge());
		  updatedPatient.setGender(patient2.getGender());
		  updatedPatient.setContact(patient2.getContact());
		  updatedPatient.setStaffdetails(patient2.getStaffdetails());
		  
		  assertThat(patient2.getName()).isEqualTo(updatedPatient.getName());
		  assertThat(patient2.getAddress()).isEqualTo(updatedPatient.getAddress());
	      assertThat(patient2.getAge()).isEqualTo(updatedPatient.getAge());
	      assertThat(patient2.getGender()).isEqualTo(updatedPatient.getGender());
	      assertThat(patient2.getContact()).isEqualTo(updatedPatient.getContact());
	      assertThat(patient2.getStaffdetails()).isEqualTo(updatedPatient.getStaffdetails());
	    
	  }

	  @Test
	  public void should_delete_patient_by_id() {
		  
		  Patient patient1 = new Patient(1, "#patientname1", "#pataddress1" , 28 , "male" , "9488582767" , null);
		  entityManager.persist(patient1);

		  Patient patient2 = new Patient(2, "#patientname2", "#pataddress2" , 21 , "female" , "9588988582" , null);
		  entityManager.persist(patient2);

		  Patient patient3 = new Patient(3, "#patientname3", "#pataddress3" , 20 , "female" , "9588523556" , null);
		  entityManager.persist(patient3);

		  repository.deleteById(patient2.getId());
		  Iterable<Patient> patients = repository.findAll();
		  
		  assertThat(patients).hasSize(2).contains(patient1, patient3);
	  }

	  @Test
	  public void should_delete_all_departments() {
		  
	    entityManager.persist(new Patient(1, "#patientname1", "#pataddress1" , 28 , "male" , "9488582767" , null));
	    entityManager.persist(new Patient(2, "#patientname2", "#pataddress2" , 21 , "female" , "9588523556" , null));

	    repository.deleteAll();
	    assertThat(repository.findAll()).isEmpty();
	  }

	  
	  
}