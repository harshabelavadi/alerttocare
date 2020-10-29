package com.philips.alerttocare.model;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.BedRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * This BedJPAunitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Bed entities on
 * repository and finding them by Id's
 */
public class BedTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  BedRepository repository;

	  @Test
	  public void should_find_no_Bed_if_repository_is_empty() {
	    
		Iterable<Bed> beds = repository.findAll();
	    assertThat(beds).isEmpty();
	  
	  }

	  @Test
	  public void should_store_a_Bed() {
		  Icu icuobj = new Icu();
		  Bed bed = new Bed(1, "Bed1" , true , true , icuobj);
		  Date dateobj = bed.getCreatedAt();
		  
		  assertThat(bed).hasFieldOrPropertyWithValue("label", "Bed1");
		  assertThat(bed).hasFieldOrPropertyWithValue("occupiedFlag", true);
		  assertThat(bed).hasFieldOrPropertyWithValue("alertstatus", true);
		  assertThat(bed).hasFieldOrPropertyWithValue("createdAt", dateobj);
		  assertThat(bed).hasFieldOrPropertyWithValue("icu", icuobj);
	  }

	  @Test
	  public void should_find_all_beds() {
	  
		  Bed bed1 = new Bed(1, "Bed 1" , true , false , null);
		  entityManager.persist(bed1);

		  Bed bed2 = new Bed(2, "Bed 2" , false , false , null);
		  entityManager.persist(bed2);

		  Bed bed3 = new Bed(3, "Bed 3" , true , false , null);
		  entityManager.persist(bed3);

		  Iterable<Bed> beds = repository.findAll();
		  assertThat(beds).hasSize(3).contains(bed1, bed2, bed3);
	  }

	  @Test
	  public void should_find_bed_by_id() {
		
		  Bed bed1 = new Bed(1, "Bed 1" , true , false , null);
		  entityManager.persist(bed1);

		  Bed bed2 = new Bed(2, "Bed 2" , false , false , null);
		  entityManager.persist(bed2);

		  Bed bed3 = new Bed(3, "Bed 3" , true , false , null);
		  entityManager.persist(bed3);
		  
		  Bed foundBed = repository.findById(2L).get();
		  assertThat(foundBed).isEqualTo(bed2);
	  }

	  @Test
	  public void should_update_bed_by_id() {
	  
		  Bed bed1 = new Bed(1, "Bed1" , true , false , null);
		  entityManager.persist(bed1);
	
		  Bed bed2 = new Bed(2, "Bed2" , true , false , null);
		  entityManager.persist(bed2);
	
		  Bed updatedBed = new Bed(3, "UpdatedBedLabel" , false , false , null);
		  entityManager.persist(updatedBed);
	
		  updatedBed.setLabel(bed2.getLabel());
		  updatedBed.setOccupiedFlag(bed2.getOccupiedFlag());
		  updatedBed.setIcu(bed2.getIcu());
	    
	      assertThat(bed2.getLabel()).isEqualTo(updatedBed.getLabel());
	      assertThat(bed2.getOccupiedFlag()).isEqualTo(updatedBed.getOccupiedFlag());
	      assertThat(bed2.getIcu()).isEqualTo(updatedBed.getIcu());
	   
	  }

	  @Test
	  public void should_delete_bed_by_id() {

		  Bed bed1 = new Bed(1, "Bed1" , true , false , null);
		  entityManager.persist(bed1);

		  Bed bed2 = new Bed(2, "Bed2" , true , true , null);
		  entityManager.persist(bed2);

		  Bed bed3 = new Bed(3, "Bed3" , true , false , null);
		  entityManager.persist(bed3);

		  repository.deleteById(2L);

		  Iterable<Bed> beds = repository.findAll();
		  assertThat(beds).hasSize(2).contains(bed1, bed3);
	  }

	  @Test
	  public void should_delete_all_beds() {
		  
		  entityManager.persist(new Bed(1, "Bed1" , true , false , null));
		  entityManager.persist(new Bed(2, "Bed2" , true , false , null));

		  repository.deleteAll();
		  assertThat(repository.findAll()).isEmpty();
	  }
}