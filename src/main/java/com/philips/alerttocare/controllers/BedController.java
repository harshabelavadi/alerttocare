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
import com.philips.alerttocare.models.Bed;
import com.philips.alerttocare.services.BedServiceImplementation;

@RestController
@RequestMapping("/api/alerttocare/")
public class BedController {
	
	@Autowired
	private BedServiceImplementation bedService;
	
	// get beds
	@GetMapping("beds")
	public List<Bed> getAllBeds() {
		return this.bedService.getAllBeds();
	}
	
	// get bed by id
	@GetMapping("beds/{id}")
	public ResponseEntity<Bed> getBedById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.bedService.getBedById(id));
	}

	// save bed record
	@PostMapping("beds")
	public Bed createBed(@RequestBody Bed bed) {
		return this.bedService.saveBed(bed);
	}
	
	// update bed record
	@PutMapping("beds/{id}")
	public ResponseEntity<Bed> updateBed(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Bed bedRecord) throws ResourceNotFoundException {
		Bed bed = this.bedService.getBedById(id);
		bed.setLabel(bedRecord.getLabel());
		bed.setOccupiedFlag(bedRecord.getOccupiedFlag());
		bed.setAlertstatus(bedRecord.isAlertstatus());
		bed.setIcu(bedRecord.getIcu());
		
		return ResponseEntity.ok(this.bedService.saveBed(bed));
	}
	
	// delete bed record
	@DeleteMapping("beds/{id}")
	public String deleteBed(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		this.bedService.deleteBed(this.bedService.getBedById(id));
		return "Record with id :: "+id+" is deleted";
	}
}
