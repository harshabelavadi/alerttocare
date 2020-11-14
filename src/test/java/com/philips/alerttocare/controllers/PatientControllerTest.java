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
public class PatientControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_CreatePatient() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/staffdetails/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 1}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/patients/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 1, \"name\" : \"Patient 1\", \"address\": \"Address\", \"age\":75, \"gender\": \"female\", \"contact\": \"985739247954\", \"staffdetails\":{\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.address").exists())
				.andExpect(jsonPath("$.age").exists())
				.andExpect(jsonPath("$.gender").exists())
				.andExpect(jsonPath("$.staffdetails").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Patient 1"))
				.andExpect(jsonPath("$.address").value("Address"))
				.andExpect(jsonPath("$.age").value(75))
				.andExpect(jsonPath("$.gender").value("female"))
				.andExpect(jsonPath("$.staffdetails.id").value(1L));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/patients/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\" : 2, \"name\" : \"Patient 2\", \"address\": \"Address\", \"age\":65, \"gender\": \"female\", \"contact\": \"9589098765\", \"staffdetails\":{\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.address").exists())
				.andExpect(jsonPath("$.age").exists())
				.andExpect(jsonPath("$.gender").exists())
				.andExpect(jsonPath("$.staffdetails").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Patient 2"))
				.andExpect(jsonPath("$.address").value("Address"))
				.andExpect(jsonPath("$.age").value(65))
				.andExpect(jsonPath("$.gender").value("female"))
				.andExpect(jsonPath("$.staffdetails.id").value(1L));
	}
	
	@Test
	public void test2_UpdatePatient() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/alerttocare/patients/1")
				.contentType(MediaType.APPLICATION_JSON)
		        .content("{\"name\" : \"Patient One\", \"address\": \"Dummy Address\", \"age\":56, \"gender\": \"male\", \"staffdetails\":{\"id\":1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.address").exists())
				.andExpect(jsonPath("$.age").exists())
				.andExpect(jsonPath("$.gender").exists())
				.andExpect(jsonPath("$.staffdetails").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.name").value("Patient One"))
				.andExpect(jsonPath("$.address").value("Dummy Address"))
				.andExpect(jsonPath("$.age").value(56))
				.andExpect(jsonPath("$.gender").value("male"))
				.andExpect(jsonPath("$.staffdetails.id").value(1L));
	    }	
	
	@Test
	public void test3_GetAllPatients() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/patients").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)));
	}	
	
	@Test
	public void test4_GetPatientById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/patients/1")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.address").exists())
		.andExpect(jsonPath("$.age").exists())
		.andExpect(jsonPath("$.gender").exists())
		.andExpect(jsonPath("$.staffdetails").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id").value(1L))
		.andExpect(jsonPath("$.name").value("Patient One"))
		.andExpect(jsonPath("$.address").value("Dummy Address"))
		.andExpect(jsonPath("$.age").value(56))
		.andExpect(jsonPath("$.gender").value("male"))
		.andExpect(jsonPath("$.staffdetails.id").value(1L));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/patients/3").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_DeletePatient() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/alerttocare/patients/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}	
}
