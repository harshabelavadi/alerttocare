package com.philips.alerttocare.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;
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
public class StaffDetailsControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_CreateStaffdetails() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/staffdetails/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"username\" : \"admin1\", \"password\": \"admin\", \"designation\":\"designation1\", \"adminPrevilige\":false}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.username").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.designation").exists())
				.andExpect(jsonPath("$.adminPrevilige").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.username").value("admin1"))
				.andExpect(jsonPath("$.password").value("admin"))
				.andExpect(jsonPath("$.designation").value("designation1"))
				.andExpect(jsonPath("$.adminPrevilige").value(false));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/alerttocare/staffdetails/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 2, \"username\" : \"admin2\", \"password\": \"admin\", \"designation\":\"designation2\", \"adminPrevilige\":true}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.username").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.designation").exists())
				.andExpect(jsonPath("$.adminPrevilige").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(2L))
				.andExpect(jsonPath("$.username").value("admin2"))
				.andExpect(jsonPath("$.password").value("admin"))
				.andExpect(jsonPath("$.designation").value("designation2"))
				.andExpect(jsonPath("$.adminPrevilige").value(true));
	}
	
	@Test
	public void test2_UpdateStaffdetails() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/alerttocare/staffdetails/1")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"id\": 1, \"username\" : \"admin1\", \"password\": \"newPassword\", \"designation\":\"designation1\", \"adminPrevilige\":true}")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.username").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.designation").exists())
				.andExpect(jsonPath("$.adminPrevilige").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.username").value("admin1"))
				.andExpect(jsonPath("$.password").value("newPassword"))
				.andExpect(jsonPath("$.designation").value("designation1"))
				.andExpect(jsonPath("$.adminPrevilige").value(true));
	    }	
	
	@Test
	public void test3_GetAllStaffdetails() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/staffdetails").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	
	@Test
	public void test4_GetStaffdetailsById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/staffdetails/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.username").exists())
		.andExpect(jsonPath("$.password").exists())
		.andExpect(jsonPath("$.designation").exists())
		.andExpect(jsonPath("$.adminPrevilige").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id").value(2L))
		.andExpect(jsonPath("$.username").value("admin2"))
		.andExpect(jsonPath("$.password").value("admin"))
		.andExpect(jsonPath("$.designation").value("designation2"))
		.andExpect(jsonPath("$.adminPrevilige").value(true));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/alerttocare/staffdetails/3").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_DeleteStaffdetails() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/alerttocare/staffdetails/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
