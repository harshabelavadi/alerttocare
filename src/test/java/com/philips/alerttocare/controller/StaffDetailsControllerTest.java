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
import com.philips.alerttocare.model.StaffDetails;
import com.philips.alerttocare.repository.StaffDetailsRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StaffDetailsController.class)
public class StaffDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StaffDetailsController staffdetailscontroller;
	@MockBean
	private StaffDetailsRepository staffdetailsrepository;

	@Test
	public void testGetAllStaffdetails() throws Exception {

		StaffDetails mockstaff1 = new StaffDetails(1, "uname1", "pwd1", "doctor", true);
		StaffDetails mockstaff2 = new StaffDetails(2, "uname2", "pwd2", "nurse", false);
		List<StaffDetails> stafflist = new ArrayList<>();
		int status = 0;
		
		stafflist.add(mockstaff1);
		stafflist.add(mockstaff2);

		Mockito.when(staffdetailscontroller.getAllStaffDetails()).thenReturn(stafflist);
		String URI = "/api/alerttocare/staffdetails";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(stafflist);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}
	
	
	@Test
	public void testGetStaffDetailsById() throws Exception {
		
		StaffDetails mockstaff1 = new StaffDetails(1, "uname1", "pwd1", "doctor", true);
		int status = 0;
		
		Mockito.when(staffdetailscontroller.getStaffDetailsById(1L)).thenReturn(ResponseEntity.ok().body(mockstaff1));
		String URI = "/api/alerttocare/staffdetails/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(mockstaff1);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}

	@Test
	public void testDeleteStaffdetails() throws Exception {

		StaffDetails mockstaff1 = new StaffDetails(1, "uname1", "pwd1", "doctor", true);
		int status = 0;
		staffdetailsrepository.save(mockstaff1);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		Mockito.when(staffdetailscontroller.deleteStaffDetailsByUserId(1L)).thenReturn(response);
		String URI = "/api/alerttocare/staffdetails/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	@Test
	public void testCreateStaffdetails() throws Exception {
		
			StaffDetails mockstaff1 = new StaffDetails(1, "uname1", "pwd1", "doctor", true);
	       	String inputJson = this.mapToJson(mockstaff1);
	       	int status = 0;
	       	Mockito.when(staffdetailscontroller.createStaffDetails(mockstaff1)).thenReturn(mockstaff1);

	       	String URI = "/api/alerttocare/staffdetails/";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			status = result.getResponse().getStatus();			
			assertThat(status).isEqualTo(200);
	 
	    }
	
	@Test
	public void testUpdateStaffdetails() throws Exception {
		
		StaffDetails mockstaff1 = new StaffDetails(1, "uname1", "pwd1", "doctor", true);
		StaffDetails updatestaff = new StaffDetails(1, "uname2", "pwd2", "nurse", false);
	       	String inputJson = this.mapToJson(updatestaff);
	       	int status = 0;
	      	staffdetailsrepository.save(mockstaff1);
	       	Mockito.when(staffdetailscontroller.updateStaffDetailsByStaffId(1L, updatestaff)).thenReturn(ResponseEntity.ok(updatestaff));
	       	
	       	String URI = "/api/alerttocare/staffdetails/1";
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
