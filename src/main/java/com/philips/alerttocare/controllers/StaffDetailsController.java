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
import com.philips.alerttocare.models.StaffDetails;
import com.philips.alerttocare.services.StaffDetailsServiceImplementation;

@RestController
@RequestMapping("/api/alerttocare/")
public class StaffDetailsController {

	@Autowired
	private StaffDetailsServiceImplementation staffdetailsService;
	
	// get staff details
	@GetMapping("staffdetails")
	public List<StaffDetails> getAllStaffDetails() {
		return this.staffdetailsService.getAllStaffDetails();
	}
	
	// get staff details by user id
	@GetMapping("staffdetails/{id}")
	public ResponseEntity<StaffDetails> getStaffDetailsById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.staffdetailsService.getStaffDetailsById(id));
	}
	
	// save staff details record
	@PostMapping("staffdetails")
	public StaffDetails createStaffDetails(@RequestBody StaffDetails staffDetails) {
		return this.staffdetailsService.saveStaffDetails(staffDetails);
	}
	
	// update staff details record by id
	@PutMapping("staffdetails/{id}")
	public ResponseEntity<StaffDetails> updateStaffDetails(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody StaffDetails staffDetailsRecord) throws ResourceNotFoundException {
		StaffDetails staffDetails = this.staffdetailsService.getStaffDetailsById(id);
		
		staffDetails.setAdminPrevilige(staffDetailsRecord.isAdminPrevilige());
		staffDetails.setDesignation(staffDetailsRecord.getDesignation());
		staffDetails.setPassword(staffDetailsRecord.getPassword());
		staffDetails.setUsername(staffDetailsRecord.getUsername());
		
		return ResponseEntity.ok(this.staffdetailsService.saveStaffDetails(staffDetails));
	}
	
	// delete staff details record by id
	@DeleteMapping("staffdetails/{id}")
	public String deleteStaffDetails(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		this.staffdetailsService.deleteStaffDetails(this.staffdetailsService.getStaffDetailsById(id));
		return "Record with id :: "+id+" is deleted";
	}	
	
}
