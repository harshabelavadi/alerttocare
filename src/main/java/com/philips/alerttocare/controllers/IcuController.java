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
import com.philips.alerttocare.models.Icu;
import com.philips.alerttocare.services.IcuServiceImplementation;

@RestController
@RequestMapping("/api/alerttocare/")
public class IcuController {
	@Autowired
	private IcuServiceImplementation icuService;
	
	// get icu
	@GetMapping("icus")
	public List<Icu> getAllIcus() {
		return this.icuService.getAllIcus();
	}
	
	// get icu by id
	@GetMapping("icus/{id}")
	public ResponseEntity<Icu> getIcuById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Icu icu = icuService.getIcuById(id);
		return ResponseEntity.ok().body(icu);
	}

	// save icu record
	@PostMapping("icus")
	public Icu createIcu(@RequestBody Icu icu) {
		return this.icuService.saveIcu(icu);
	}
	
	// update icu record
	@PutMapping("icus/{id}")
	public ResponseEntity<Icu> updateIcu(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Icu icuRecord) throws ResourceNotFoundException {
		Icu icu = icuService.getIcuById(id);
		icu.setLabel(icuRecord.getLabel());
		
		return ResponseEntity.ok(this.icuService.saveIcu(icu));
	}
	
	// delete icu record
	@DeleteMapping("icus/{id}")
	public String deleteIcu(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		this.icuService.deleteIcu(this.icuService.getIcuById(id));
		return "Record with id :: "+id+" is deleted";
	}
}
