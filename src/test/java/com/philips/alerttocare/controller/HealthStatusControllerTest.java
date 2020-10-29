package com.philips.alerttocare.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
import com.philips.alerttocare.model.HealthStatus;
import com.philips.alerttocare.repository.HealthStatusRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HealthStatusController.class)
public class HealthStatusControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HealthStatusController healthStatusController;
	@MockBean
	private HealthStatusRepository healthStatusRpository;

	@Test
	public void testGetAllHealthStatus() throws Exception {
		
		HealthStatus mockhealthstatus1 = new HealthStatus(1, 30.6, 40.7, 50.8, 60.9, null, null);
		HealthStatus mockhealthstatus2 = new HealthStatus(2, 50.6, 30.7, 45.8, 55.9, null, null);
		List<HealthStatus> healthstatuslist = new ArrayList<>();
		int status = 0;
		
		healthstatuslist.add(mockhealthstatus1);
		healthstatuslist.add(mockhealthstatus2);

		Mockito.when(healthStatusController.getAllStatus()).thenReturn(healthstatuslist);
		String URI = "/api/alerttocare/healthstatus";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(healthstatuslist);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}
	
	
	@Test
	public void testGetHealthStatusById() throws Exception {
		
		HealthStatus mockhealthstatus1 = new HealthStatus(1, 30.6, 40.7, 50.8, 60.9, null, null);
		int status = 0;
		
		Mockito.when(healthStatusController.getStatusById(1L)).thenReturn(ResponseEntity.ok().body(mockhealthstatus1));
		String URI = "/api/alerttocare/healthstatus/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(mockhealthstatus1);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}

	@Test
	public void testDeleteHealthStatus() throws Exception {

		HealthStatus mockhealthstatus1 = new HealthStatus(1, 30.6, 40.7, 50.8, 60.9, null, null);
		int status = 0;
		healthStatusRpository.save(mockhealthstatus1);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		Mockito.when(healthStatusController.deleteStatus(1L)).thenReturn(response);
		String URI = "/api/alerttocare/healthstatus/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	@Test
	public void testCreateHealthStatus() throws Exception {
		
		HealthStatus mockhealthstatus1 = new HealthStatus(1, 30.6, 40.7, 50.8, 60.9, null, null);
	       	String inputJson = this.mapToJson(mockhealthstatus1);
	       	int status = 0;
	       	Mockito.when(healthStatusController.createStatus(mockhealthstatus1)).thenReturn(mockhealthstatus1);

	       	String URI = "/api/alerttocare/healthstatus/";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			status = result.getResponse().getStatus();			
			assertThat(status).isEqualTo(200);
	 
	    }
	
	@Test
	public void testUpdateHealthStatus() throws Exception {
		
		HealthStatus mockhealthstatus1 = new HealthStatus(1, 30.6, 40.7, 50.8, 60.9, null, null);
		HealthStatus updateHealthstatus = new HealthStatus(1, 60.6, 50.7, 30.8, 45.9, null, null);
	       	String inputJson = this.mapToJson(updateHealthstatus);
	       	int status = 0;
	       	healthStatusRpository.save(mockhealthstatus1);
	       	Mockito.when(healthStatusController.updateStatus(1L, mockhealthstatus1)).thenReturn(ResponseEntity.ok(updateHealthstatus));
	       	
	       	String URI = "/api/alerttocare/healthstatus/1";
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
