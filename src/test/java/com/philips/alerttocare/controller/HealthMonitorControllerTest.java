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
import com.philips.alerttocare.model.HealthMonitor;
import com.philips.alerttocare.repository.HealthMonitorRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HealthMonitorController.class)
public class HealthMonitorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HealthMonitorController healthMonitorController;
	@MockBean
	private HealthMonitorRepository healthMonitorRepository;

	@Test
	public void testGetAllMonitors() throws Exception {

		HealthMonitor mockmonitor1 = new HealthMonitor(1, "Monitor 1");
		HealthMonitor mockmonitor2 = new HealthMonitor(2, "Monitor 2");
		List<HealthMonitor> monitorList = new ArrayList<>();
		int status = 0;
		
		monitorList.add(mockmonitor1);
		monitorList.add(mockmonitor2);

		Mockito.when(healthMonitorController.getAllMonitors()).thenReturn(monitorList);
		String URI = "/api/alerttocare/monitors";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(monitorList);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}
	
	
	@Test
	public void testGetMonitortById() throws Exception {
		
		HealthMonitor mockmonitor1 = new HealthMonitor(1, "Monitor 1");
		int status = 0;
		
		Mockito.when(healthMonitorController.getMonitorById(1L)).thenReturn(ResponseEntity.ok().body(mockmonitor1));
		String URI = "/api/alerttocare/monitors/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(mockmonitor1);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}

	@Test
	public void testDeleteMonitor() throws Exception {

		HealthMonitor mockmonitor1 = new HealthMonitor(1, "Monitor 1");
		int status = 0;
		healthMonitorRepository.save(mockmonitor1);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		Mockito.when(healthMonitorController.deleteMonitor(1L)).thenReturn(response);
		String URI = "/api/alerttocare/monitors/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	@Test
	public void testCreateMonitor() throws Exception {
		
		HealthMonitor mockmonitor1 = new HealthMonitor(1, "Monitor 1");
	       	String inputJson = this.mapToJson(mockmonitor1);
	       	int status = 0;
	       	Mockito.when(healthMonitorController.createMonitor(mockmonitor1)).thenReturn(mockmonitor1);

	       	String URI = "/api/alerttocare/monitors/";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			status = result.getResponse().getStatus();			
			assertThat(status).isEqualTo(200);
	 
	    }
	
	@Test
	public void testUpdateMonitor() throws Exception {
		
			HealthMonitor mockmonitor1 = new HealthMonitor(1, "Monitor 1");
			HealthMonitor updateMonitor = new HealthMonitor(2, "Monitor 2");
	       	String inputJson = this.mapToJson(updateMonitor);
	       	int status = 0;
	       	healthMonitorRepository.save(mockmonitor1);
	       	Mockito.when(healthMonitorController.updateMonitor(1L, updateMonitor)).thenReturn(ResponseEntity.ok(updateMonitor));
	       	
	       	String URI = "/api/alerttocare/monitors/1";
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
