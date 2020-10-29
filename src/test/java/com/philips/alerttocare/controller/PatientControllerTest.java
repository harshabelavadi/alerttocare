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
import com.philips.alerttocare.model.Patient;
import com.philips.alerttocare.repository.PatientRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PatientController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientController patientcontroller;
	@MockBean
	private PatientRepository patientrepository;

	@Test
	public void testGetAllPatients() throws Exception {

		Patient mockpatient1 = new Patient(1, "name1", "address1", 27, "male", "9489765345", null);
		Patient mockpatient2 = new Patient(2, "name2", "address2", 28, "female", "7897294953", null);
		List<Patient> patientlist = new ArrayList<>();
		int status = 0;
		
		patientlist.add(mockpatient1);
		patientlist.add(mockpatient2);

		Mockito.when(patientcontroller.getAllPatients()).thenReturn(patientlist);
		String URI = "/api/alerttocare/patients";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(patientlist);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}
	
	
	@Test
	public void testGetPatientById() throws Exception {
		
		Patient mockpatient1 = new Patient(1, "name1", "address1", 27, "male", "9489765345", null);
		int status = 0;
		
		Mockito.when(patientcontroller.getPatientById(1L)).thenReturn(ResponseEntity.ok().body(mockpatient1));
		String URI = "/api/alerttocare/patients/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(mockpatient1);
		String outputInJson = result.getResponse().getContentAsString();
		status = result.getResponse().getStatus();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertThat(status).isEqualTo(200);
	}

	@Test
	public void testDeletePatient() throws Exception {

		Patient mockpatient1 = new Patient(1, "name1", "address1", 27, "male", "9489765345", null);
		int status = 0;
		patientrepository.save(mockpatient1);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		Mockito.when(patientcontroller.deletePatient(1L)).thenReturn(response);
		String URI = "/api/alerttocare/patients/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		status = result.getResponse().getStatus();
		assertThat(status).isEqualTo(200);
	}
	
	@Test
	public void testCreatePatient() throws Exception {
		
			Patient mockpatient1 = new Patient(1, "name1", "address1", 27, "male", "9489765345", null);
	       	String inputJson = this.mapToJson(mockpatient1);
	       	int status = 0;
	       	Mockito.when(patientcontroller.createPatient(mockpatient1)).thenReturn(mockpatient1);

	       	String URI = "/api/alerttocare/patients/";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			status = result.getResponse().getStatus();			
			assertThat(status).isEqualTo(200);
	 
	    }
	
	@Test
	public void testUpdatePatient() throws Exception {
		
			Patient mockpatient1 = new Patient(1, "name1", "address1", 27, "male", "9489765345", null);
			Patient updatepatient = new Patient(1, "name2", "address2", 32, "female", "7689765345", null);
	       	String inputJson = this.mapToJson(updatepatient);
	       	int status = 0;
	      	patientrepository.save(mockpatient1);
	       	Mockito.when(patientcontroller.updatePatient(1L, updatepatient)).thenReturn(ResponseEntity.ok(updatepatient));
	       	
	       	String URI = "/api/alerttocare/patients/1";
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
