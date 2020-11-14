package com.philips.alerttocare.models;
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

import com.philips.alerttocare.repositories.IcuRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This IcuJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Icu entities on
 * repository and finding them by Id's
 */

public class IcuJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  IcuRepository repository;

	  @Test
	  public void should_find_no_Icu_if_repository_is_empty() {
		  
		  Iterable<Icu> icus = repository.findAll();
		  assertThat(icus).isEmpty();
	  }

	  @Test
	  public void should_store_a_Icu() {
		  Icu icu = repository.save(new Icu(1, "ICU1"));
		  Date dateobj = icu.getCreatedAt();
		  
		  assertThat(icu).hasFieldOrPropertyWithValue("label", "ICU1");
		  assertThat(icu).hasFieldOrPropertyWithValue("createdAt", dateobj);
	  }

	  @Test
	  public void should_find_all_icus() {
		  Icu icu1 = new Icu(1, "ICU1");
		  entityManager.persist(icu1);
		  
		  Icu icu2 = new Icu(2, "ICU2");
		  entityManager.persist(icu2);

		  Icu icu3 = new Icu(3, "ICU3");
		  entityManager.persist(icu3);

		  Iterable<Icu> icus = repository.findAll();
		  assertThat(icus).hasSize(3).contains(icu1, icu2, icu3);
	  }

	  @Test
	  public void should_find_icu_by_id() {
		Icu icu1 = new Icu(1, "ICU1");
	    entityManager.persist(icu1);

	    Icu icu2 = new Icu(2, "ICU2");
	    entityManager.persist(icu2);

	    Icu foundIcu = repository.findById(2L).get();
	    assertThat(foundIcu).isEqualTo(icu2);
	  }

	  @Test
	  public void should_update_icu_by_id() {
		  Icu icu1 = new Icu(1, "ICU1");
		  entityManager.persist(icu1);
		  
		  Icu icu2 = new Icu(2, "ICU2");
		  entityManager.persist(icu2);

		  Icu updatedIcu = new Icu(3, "Updated ICU label");
		  entityManager.persist(updatedIcu);

		  updatedIcu.setLabel(icu2.getLabel());
		  assertThat(icu2.getLabel()).isEqualTo(updatedIcu.getLabel());
	  }

	  @Test
	  public void should_delete_icu_by_id() {
		  
		  Icu icu1 = new Icu(1, "ICU1");
		  entityManager.persist(icu1);

		  Icu icu2 = new Icu(2, "ICu2");
		  entityManager.persist(icu2);

		  Icu icu3 = new Icu(3, "ICU3");
		  entityManager.persist(icu3);

		  repository.deleteById(2L);

		  Iterable<Icu> icus = repository.findAll();
		  assertThat(icus).hasSize(2).contains(icu1, icu3);
	  }

	  @Test
	  public void should_delete_all_departments() {
		  
		  entityManager.persist(new Icu(1, "ICU1"));
		  entityManager.persist(new Icu(2, "ICU2"));

		  repository.deleteAll();
		  assertThat(repository.findAll()).isEmpty();
	  }
}
