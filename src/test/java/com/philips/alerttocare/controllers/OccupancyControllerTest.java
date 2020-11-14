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
public class OccupancyControllerTest {	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_CreateStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/icus/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"label\": \"Icu 1\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.label").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L))	
				.andExpect(jsonPath("$.label").value("Icu 1"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/beds/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"label\": \"Bed 1\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.label").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L))	
				.andExpect(jsonPath("$.label").value("Bed 1"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/patients/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 1, \"name\" : \"Patient 1\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Patient 1"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/occupancies/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"icu\": {\"id\":1}, \"bed\": {\"id\":1}, \"patient\": {\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.icu").exists())
				.andExpect(jsonPath("$.bed").exists())
				.andExpect(jsonPath("$.patient").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.icu.id").value(1L))
				.andExpect(jsonPath("$.bed.id").value(1L))
				.andExpect(jsonPath("$.patient.id").value(1L));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/occupancies/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 2, \"icu\": {\"id\":1}, \"bed\": {\"id\":1}, \"patient\": {\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.icu").exists())
				.andExpect(jsonPath("$.bed").exists())
				.andExpect(jsonPath("$.patient").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2L))
				.andExpect(jsonPath("$.icu.id").value(1L))
				.andExpect(jsonPath("$.bed.id").value(1L))
				.andExpect(jsonPath("$.patient.id").value(1L));
		
	}
	
	@Test
	public void test2_UpdateStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/beds/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 2, \"label\": \"Bed 2\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.label").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2L))	
				.andExpect(jsonPath("$.label").value("Bed 2"));		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/alerttocare/occupancies/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"icu\": {\"id\":1}, \"bed\": {\"id\":2}, \"patient\": {\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bed").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.bed.id").value(2L));
	    }	
	
	@Test
	public void test3_GetAllStatus() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/occupancies").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)));
	}	
	
	@Test
	public void test4_GetStatusById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/occupancies/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.icu").exists())
		.andExpect(jsonPath("$.bed").exists())
		.andExpect(jsonPath("$.patient").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id").value(1L))
		.andExpect(jsonPath("$.icu.id").value(1L))
		.andExpect(jsonPath("$.bed.id").value(2L))
		.andExpect(jsonPath("$.patient.id").value(1L));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/occupancies/3").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_DeleteStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/alerttocare/occupancies/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}	
}
