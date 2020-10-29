package com.philips.alerttocare.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philips.alerttocare.model.Occupancy;
import com.philips.alerttocare.repository.OccupancyRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OccupancyController.class)
public class OccupancyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OccupancyController occupancycontroller;
	@MockBean
	private OccupancyRepository occupancyrepository;

	@Test
	public void testGetAllOccupancies() throws Exception {

		Occupancy mockoccupancy1 = new Occupancy(1, new Date(), null, null, null);
		Occupancy mockoccupancy2 = new Occupancy(2, new Date(), null, null, null);
		List<Occupancy> occupancylist = new ArrayList<>();
		int status = 0;
		
		occupancylist.add(mockoccupancy1);
		occupancylist.add(mockoccupancy2);

		Mockito.when(occupancycontroller.getAllOccupancies()).thenReturn(occupancylist);
		String URI = "/api/alerttocare/occupancies";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	
	@Test
	public void testGetOccupancyById() throws Exception {
		
		Occupancy mockoccupancy1 = new Occupancy(1, new Date(), null, null, null);
		int status = 0;
		
		Mockito.when(occupancycontroller.getOccupancyById(1L)).thenReturn(ResponseEntity.ok().body(mockoccupancy1));
		String URI = "/api/alerttocare/occupancies/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}

	@Test
	public void testDeleteOccupancy() throws Exception {

		Occupancy mockoccupancy1 = new Occupancy(1, new Date(), null, null, null);
		int status = 0;
		occupancyrepository.save(mockoccupancy1);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		Mockito.when(occupancycontroller.deleteOccupancy(1L)).thenReturn(response);
		String URI = "/api/alerttocare/occupancies/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	@Test
	public void testCreateOccupancy() throws Exception {
		
		Occupancy mockoccupancy1 = new Occupancy(1, new Date(), null, null, null);
	       	String inputJson = this.mapToJson(mockoccupancy1);
	       	int status = 0;
	       	Mockito.when(occupancycontroller.createOccupancies(mockoccupancy1)).thenReturn(mockoccupancy1);

	       	String URI = "/api/alerttocare/occupancies/";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			status = result.getResponse().getStatus();			
			assertThat(status).isEqualTo(200);
	 
	    }
	
	@Test
	public void testUpdateOccupancies() throws Exception {
		
		Occupancy mockoccupancy1 = new Occupancy(1, new Date(), null, null, null);
		Occupancy updateoccupancy = new Occupancy(1, new Date(), null, null, null);
	       	String inputJson = this.mapToJson(updateoccupancy);
	       	int status = 0;
	       	occupancyrepository.save(mockoccupancy1);
	       	Mockito.when(occupancycontroller.updateOccupancy(1L, updateoccupancy)).thenReturn(ResponseEntity.ok(updateoccupancy));
	       	
	       	String URI = "/api/alerttocare/occupancies/1";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			status = result.getResponse().getStatus();
			assertThat(status).isEqualTo(200);
	    }
	
	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	public static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
	
	
}
