//package com.philips.alerttocare.controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.philips.alerttocare.model.Icu;
//import com.philips.alerttocare.repository.IcuRepository;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = IcuController.class)
//public class HealthMonitorControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private IcuController icucontroller;
//	@MockBean
//	private IcuRepository icurepository;
//
//	@Test
//	public void testGetAllIcus() throws Exception {
//
//		Icu mockIcu1 = new Icu(1, "Icu 1");
//		Icu mockIcu2 = new Icu(2, "Icu 2");
//		List<Icu> icuList = new ArrayList<>();
//		int status = 0;
//		
//		icuList.add(mockIcu1);
//		icuList.add(mockIcu2);
//
//		Mockito.when(icucontroller.getAllIcus()).thenReturn(icuList);
//		String URI = "/api/alerttocare/icus";
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		String expectedJson = this.mapToJson(icuList);
//		String outputInJson = result.getResponse().getContentAsString();
//		status = result.getResponse().getStatus();
//		
//		assertThat(outputInJson).isEqualTo(expectedJson);
//		assertThat(status).isEqualTo(200);
//	}
//	
//	
//	@Test
//	public void testGetIcutById() throws Exception {
//		
//		Icu mockIcu1 = new Icu(1, "Icu 1");
//		int status = 0;
//		
//		Mockito.when(icucontroller.getIcuById(1L)).thenReturn(ResponseEntity.ok().body(mockIcu1));
//		String URI = "/api/alerttocare/icus/1";
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		String expectedJson = this.mapToJson(mockIcu1);
//		String outputInJson = result.getResponse().getContentAsString();
//		status = result.getResponse().getStatus();
//		
//		assertThat(outputInJson).isEqualTo(expectedJson);
//		assertThat(status).isEqualTo(200);
//	}
//
//	@Test
//	public void testDeleteIcu() throws Exception {
//
//		Icu mockIcu1 = new Icu(1, "Icu 1");
//		int status = 0;
//		icurepository.save(mockIcu1);
//		
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("Deleted", Boolean.TRUE);
//		
//		Mockito.when(icucontroller.deleteIcu(1L)).thenReturn(response);
//		String URI = "/api/alerttocare/icus/1";
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		
//		status = result.getResponse().getStatus();
//		assertThat(status).isEqualTo(200);
//	}
//	
//	@Test
//	public void testCreateIcu() throws Exception {
//		
//	       	Icu icu1 = new Icu(1, "Icu 1");
//	       	String inputJson = this.mapToJson(icu1);
//	       	int status = 0;
//	       	Mockito.when(icucontroller.createIcu(icu1)).thenReturn(icu1);
//
//	       	String URI = "/api/alerttocare/icus/";
//			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
//			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//			status = result.getResponse().getStatus();			
//			assertThat(status).isEqualTo(200);
//	 
//	    }
//	
//	@Test
//	public void testUpdateIcu() throws Exception {
//		
//	       	Icu icu1 = new Icu(1, "Icu 1");
//	    	Icu updateIcu = new Icu(1, "Icu 2");
//	       	String inputJson = this.mapToJson(updateIcu);
//	       	int status = 0;
//	      	icurepository.save(icu1);
//	       	Mockito.when(icucontroller.updateIcu(1L, updateIcu)).thenReturn(ResponseEntity.ok(updateIcu));
//	       	
//	       	String URI = "/api/alerttocare/icus/1";
//			RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).contentType(MediaType.APPLICATION_JSON).content(inputJson);
//			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//			status = result.getResponse().getStatus();
//			assertThat(status).isEqualTo(200);
//	    }
//	
//	/**
//	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
//	 */
//	private String mapToJson(Object object) throws JsonProcessingException {
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.writeValueAsString(object);
//	}
//	
//	public static String asJsonString(final Object obj) {
//
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//
//        } catch (Exception e) {
//
//            throw new RuntimeException(e);
//        }
//    }
//	
//	
//}
