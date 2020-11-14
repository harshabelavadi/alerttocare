package com.philips.alerttocare.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.Patient;
import com.philips.alerttocare.services.PatientServiceImplementation;

@RestController
@RequestMapping("/api/alerttocare/")
public class PatientController {
	
	@Autowired
	private PatientServiceImplementation patientService;
	
	// get patients
	@GetMapping("patients")
	public List<Patient> getAllPatients() {
		return this.patientService.getAllPatients();
	}
	
	// get patient by id
	@GetMapping("patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.patientService.getPatientById(id));
	}
	
	// save patient record
	@PostMapping("patients")
	public Patient createPatient(@RequestBody Patient patient) {
		return this.patientService.savePatient(patient);
	}
	
	// update occupancy record
	@PutMapping("patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Patient patientrecord) throws ResourceNotFoundException {
		Patient patient = this.patientService.getPatientById(id); 
				
		patient.setAddress(patientrecord.getAddress());
		patient.setAge(patientrecord.getAge());
		patient.setContact(patientrecord.getContact());
		patient.setGender(patientrecord.getGender());
		patient.setName(patientrecord.getName());
		patient.setStaffdetails(patientrecord.getStaffdetails());
		
		return ResponseEntity.ok(this.patientService.savePatient(patient));
	}
	
	// delete patient record
	@DeleteMapping("patients/{id}")
	public String deletePatient(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		this.patientService.deletePatient(this.patientService.getPatientById(id));
		return "Record with id :: "+id+" is deleted";
	}
		
}
