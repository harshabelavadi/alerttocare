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
import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.model.Icu;
import com.philips.alerttocare.repository.BedRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BedController.class)
public class BedControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BedController bedController;
	@MockBean
	private BedRepository bedRepository;

	@Test
	public void testGetAllBeds() throws Exception {

		Icu mockicu = new Icu(1, "Icu 1");
		Bed mockbed1 = new Bed(1, "Bed 1", true, false, mockicu);
		Bed mockbed2 = new Bed(2, "Bed 2", true, false, mockicu);
		List<Bed> bedList = new ArrayList<>();
		int status = 0;
		
		bedList.add(mockbed1);
		bedList.add(mockbed2);

		Mockito.when(bedController.getAllBeds()).thenReturn(bedList);
		String URI = "/api/alerttocare/beds";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(bedList);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}
	
	
	@Test
	public void testGetBedtById() throws Exception {
		
		Icu mockicu = new Icu(1, "Icu 1");
		Bed mockbed = new Bed(1, "Bed 1", true, false, mockicu);
		int status = 0;
		
		Mockito.when(bedController.getBedById(1L)).thenReturn(ResponseEntity.ok().body(mockbed));
		String URI = "/api/alerttocare/beds/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(mockbed);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}

	@Test
	public void testDeleteBed() throws Exception {

		Icu mockicu = new Icu(1, "Icu 1");
		Bed mockbed = new Bed(1, "Bed 1", true, false, mockicu);
		int status = 0;
		bedRepository.save(mockbed);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		Mockito.when(bedController.deleteBed(1L)).thenReturn(response);
		String URI = "/api/alerttocare/beds/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	@Test
	public void testCreateBed() throws Exception {
		
		Icu mockicu = new Icu(1, "Icu 1");
		Bed mockbed = new Bed(1, "Bed 1", true, false, mockicu);
	    String inputJson = this.mapToJson(mockbed);
	    int status = 0;
	    Mockito.when(bedController.createBed(mockbed)).thenReturn(mockbed);

	    String URI = "/api/alerttocare/beds/";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		status = result.getResponse().getStatus();			
		assertThat(status).isEqualTo(200);
	 
	    }
	
	@Test
	public void testUpdateBed() throws Exception {
		
		Icu mockicu = new Icu(1, "Icu 1");
		Bed mockbed = new Bed(1, "Bed 1", true, false, mockicu);
		Bed updatebed = new Bed(2, "Bed 2", true, false, mockicu);
		
	    String inputJson = this.mapToJson(updatebed);
	    int status = 0;
	    bedRepository.save(mockbed);
	    Mockito.when(bedController.updateBed(1L, updatebed)).thenReturn(ResponseEntity.ok(updatebed));
	       	
	    String URI = "/api/alerttocare/beds/1";
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
