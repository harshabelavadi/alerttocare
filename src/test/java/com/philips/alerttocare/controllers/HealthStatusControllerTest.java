package com.philips.alerttocare.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.philips.alerttocare.AlertToCareApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AlertToCareApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HealthStatusControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_CreateStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/occupancies/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L));	
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/monitors/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 1, \"label\" : \"Monitor 1\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.label").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.label").value("Monitor 1"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/healthstatus/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"heartrate\": 30.5, \"bp\": 40.5, \"spo2\": 50.35, \"respiratoryrate\": 47.35,"
		        		+ "\"occupancy\": {\"id\":1}, \"monitor\": {\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.heartrate").exists())
				.andExpect(jsonPath("$.bp").exists())
				.andExpect(jsonPath("$.spo2").exists())
				.andExpect(jsonPath("$.respiratoryrate").exists())
				.andExpect(jsonPath("$.occupancy").exists())
				.andExpect(jsonPath("$.monitor").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.heartrate").value(30.5))
				.andExpect(jsonPath("$.bp").value(40.5))
				.andExpect(jsonPath("$.spo2").value(50.35))
				.andExpect(jsonPath("$.respiratoryrate").value(47.35))
				.andExpect(jsonPath("$.occupancy.id").value(1L))
				.andExpect(jsonPath("$.monitor.id").value(1L));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/healthstatus/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 2, \"heartrate\": 40.5, \"bp\": 50.5, \"spo2\": 40.35, \"respiratoryrate\": 37.35,"
		        		+ "\"occupancy\": {\"id\":1}, \"monitor\": {\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.heartrate").exists())
				.andExpect(jsonPath("$.bp").exists())
				.andExpect(jsonPath("$.spo2").exists())
				.andExpect(jsonPath("$.respiratoryrate").exists())
				.andExpect(jsonPath("$.occupancy").exists())
				.andExpect(jsonPath("$.monitor").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2L))
				.andExpect(jsonPath("$.heartrate").value(40.5))
				.andExpect(jsonPath("$.bp").value(50.5))
				.andExpect(jsonPath("$.spo2").value(40.35))
				.andExpect(jsonPath("$.respiratoryrate").value(37.35))
				.andExpect(jsonPath("$.occupancy.id").value(1L))
				.andExpect(jsonPath("$.monitor.id").value(1L));
		
	}
	
	@Test
	public void test2_UpdateStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/alerttocare/healthstatus/2")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"heartrate\": 36.5, \"bp\": 45.5, \"spo2\": 74.35, \"respiratoryrate\": 40.35}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2L))
				.andExpect(jsonPath("$.heartrate").value(36.5))
				.andExpect(jsonPath("$.bp").value(45.5))
				.andExpect(jsonPath("$.spo2").value(74.35))
				.andExpect(jsonPath("$.respiratoryrate").value(40.35));
	    }	
	
	@Test
	public void test3_GetAllStatus() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/healthstatus").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)));
	}	
	
	@Test
	public void test4_GetStatusById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/healthstatus/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.heartrate").exists())
		.andExpect(jsonPath("$.bp").exists())
		.andExpect(jsonPath("$.spo2").exists())
		.andExpect(jsonPath("$.respiratoryrate").exists())
		.andExpect(jsonPath("$.occupancy").exists())
		.andExpect(jsonPath("$.monitor").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id").value(1L))
		.andExpect(jsonPath("$.heartrate").value(30.5))
		.andExpect(jsonPath("$.bp").value(40.5))
		.andExpect(jsonPath("$.spo2").value(50.35))
		.andExpect(jsonPath("$.respiratoryrate").value(47.35))
		.andExpect(jsonPath("$.occupancy.id").value(1L))
		.andExpect(jsonPath("$.monitor.id").value(1L));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/healthstatus/3").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_DeleteStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/alerttocare/healthstatus/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}	
}
