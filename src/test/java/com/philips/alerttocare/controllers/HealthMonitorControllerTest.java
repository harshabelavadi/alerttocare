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
public class HealthMonitorControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_CreateMonitor() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/monitors/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 1, \"label\" : \"Monitor 1\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.label").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.label").value("Monitor 1"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/monitors/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 2, \"label\" : \"Monitor 2\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.label").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.label").value("Monitor 2"));
	}
	
	@Test
	public void test2_UpdateMonitor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/alerttocare/monitors/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"label\" : \"Monitor One\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.label").value("Monitor One"));
	    }	
	
	@Test
	public void test3_GetAllMonitors() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/monitors").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)));
	}	
	
	@Test
	public void test4_GetMonitorById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/monitors/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.label").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id").value(2))
		.andExpect(jsonPath("$.label").value("Monitor 2"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/monitors/3").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_DeleteMonitor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/alerttocare/monitors/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}	
}
