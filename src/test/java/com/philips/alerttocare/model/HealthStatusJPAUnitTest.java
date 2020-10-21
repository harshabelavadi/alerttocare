package com.philips.alerttocare.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.HealthStatusRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * This HealthStatusJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of HealthStatus entities on
 * repository and finding them by Id's
 */

public class HealthStatusJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  HealthStatusRepository repository;

	  @Test
	  public void should_find_no_HealthStatus_if_repository_is_empty() {
	    
		  Iterable<HealthStatus> healthstatus = repository.findAll();
		  assertThat(healthstatus).isEmpty();
		  
	  }

	  @Test
	  public void should_store_a_HealthStatus() {
		  
		  Occupancy occupancy = new Occupancy();
		  HealthMonitor healthMonitor = new HealthMonitor();
		  HealthStatus healthstatus = new HealthStatus(1, 88.9 , 22.5 , 90.23 , 77.87 ,  occupancy, healthMonitor);
		  Date dateobj = healthstatus.getCreatedAt();

		  assertThat(healthstatus).hasFieldOrPropertyWithValue("heartrate", 88.9);
		  assertThat(healthstatus).hasFieldOrPropertyWithValue("bp", 22.5);
		  assertThat(healthstatus).hasFieldOrPropertyWithValue("spo2", 90.23);
		  assertThat(healthstatus).hasFieldOrPropertyWithValue("respiratoryrate", 77.87);
		  assertThat(healthstatus).hasFieldOrPropertyWithValue("createdAt", dateobj);
		  assertThat(healthstatus).hasFieldOrPropertyWithValue("occupancy", occupancy);
		  assertThat(healthstatus).hasFieldOrPropertyWithValue("monitor", healthMonitor);
	  }

	  @Test
	  public void should_find_all_healthstatuss() {
			  
		HealthStatus healthstatus1 = new HealthStatus(1, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus1);

	    HealthStatus healthstatus2 = new HealthStatus(2, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus2);

	    HealthStatus healthstatus3 = new HealthStatus(3, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus3);

	    Iterable<HealthStatus> healthstatuss = repository.findAll();

	    assertThat(healthstatuss).hasSize(3).contains(healthstatus1, healthstatus2, healthstatus3);
	  }

	  @Test
	  public void should_find_healthstatus_by_id() {
		  
		HealthStatus healthstatus1 = new HealthStatus(1, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus1);

	    HealthStatus healthstatus2 = new HealthStatus(2, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus2);

	    HealthStatus foundHealthStatus = repository.findById(2L).get();
	    assertThat(foundHealthStatus).isEqualTo(healthstatus2);
	    
	  }

	  @Test
	  public void should_update_healthstatus_by_id() {
		  
		  Occupancy occupancy1 =  new Occupancy();
		  HealthMonitor healthmonitor1 = new HealthMonitor();
		  
		  Occupancy occupancy2 =  new Occupancy();
		  HealthMonitor healthmonitor2 = new HealthMonitor();
		  
		  Occupancy occupancy3 =  new Occupancy();
		  HealthMonitor healthmonitor3 = new HealthMonitor();
		  
		  HealthStatus healthstatus1 = new HealthStatus(1, 88.9 , 22.5 , 90.23 , 77.87 , occupancy1 , healthmonitor1);
		  entityManager.persist(healthstatus1);

		  HealthStatus healthstatus2 = new HealthStatus(2, 88.9 , 22.5 , 90.23 , 77.87 , occupancy2 , healthmonitor2);
		  entityManager.persist(healthstatus2);

		  HealthStatus updatedHealthStatus = new HealthStatus(3, 85.9 , 58.5 , 70.23 , 83.87 , occupancy3 , healthmonitor3);
		  entityManager.persist(updatedHealthStatus);

		  updatedHealthStatus.setHeartrate(healthstatus2.getHeartrate());
		  updatedHealthStatus.setBp(healthstatus2.getBp());
		  updatedHealthStatus.setRespiratoryrate(healthstatus2.getRespiratoryrate());
		  updatedHealthStatus.setSpo2(healthstatus2.getSpo2());
		  updatedHealthStatus.setMonitor(healthstatus2.getMonitor());
		  updatedHealthStatus.setOccupancy(healthstatus2.getOccupancy());
	    
		  assertThat(healthstatus2.getHeartrate()).isEqualTo(updatedHealthStatus.getHeartrate());
		  assertThat(healthstatus2.getBp()).isEqualTo(updatedHealthStatus.getBp());
		  assertThat(healthstatus2.getRespiratoryrate()).isEqualTo(updatedHealthStatus.getRespiratoryrate());
		  assertThat(healthstatus2.getSpo2()).isEqualTo(updatedHealthStatus.getSpo2());
		  assertThat(healthstatus2.getMonitor()).isEqualTo(updatedHealthStatus.getMonitor());
		  assertThat(healthstatus2.getOccupancy()).isEqualTo(updatedHealthStatus.getOccupancy());
	   
	  }

	  @Test
	  public void should_delete_healthstatus_by_id() {
		  
		  HealthStatus healthstatus1 = new HealthStatus(1, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
		  entityManager.persist(healthstatus1);

		  HealthStatus healthstatus2 = new HealthStatus(2, 88.9 , 22.5 , 90.23 , 77.87 , null , null);
		  entityManager.persist(healthstatus2);

		  HealthStatus healthstatus3 = new HealthStatus(3, 85.9 , 58.5 , 70.23 , 83.87 , null , null);
		  entityManager.persist(healthstatus3);

		  repository.deleteById(2L);

		  Iterable<HealthStatus> healthstatuss = repository.findAll();

		  assertThat(healthstatuss).hasSize(2).contains(healthstatus1, healthstatus3);
	  }

	  @Test
	  public void should_delete_all_departments() {
		  		  
	    entityManager.persist(new HealthStatus(1, 88.9 , 22.5 , 90.23 , 77.87 , null , null));
	    entityManager.persist(new HealthStatus(2, 88.9 , 22.5 , 90.23 , 77.87 , null , null));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }
}
