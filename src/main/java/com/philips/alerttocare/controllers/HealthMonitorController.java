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
import com.philips.alerttocare.models.HealthMonitor;
import com.philips.alerttocare.services.HealthMonitorServiceImplementation;

@RestController
@RequestMapping("/api/alerttocare/")
public class HealthMonitorController {

	@Autowired
	private HealthMonitorServiceImplementation monitorService;
	
	// get monitors
	@GetMapping("monitors")
	public List<HealthMonitor> getAllMonitors() {
		return this.monitorService.getAllMonitors();
	}
	
	// get monitor by id
	@GetMapping("monitors/{id}")
	public ResponseEntity<HealthMonitor> getMonitorById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.monitorService.getMonitorById(id));
	}

	// save monitor record
	@PostMapping("monitors")
	public HealthMonitor createMonitor(@RequestBody HealthMonitor monitor) {
		return this.monitorService.saveMonitor(monitor);
	}
	
	// update monitor record
	@PutMapping("monitors/{id}")
	public ResponseEntity<HealthMonitor> updateMonitor(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody HealthMonitor monitorRecord) throws ResourceNotFoundException {
		HealthMonitor monitor = this.monitorService.getMonitorById(id);
		monitor.setLabel(monitorRecord.getLabel());		
		return ResponseEntity.ok(this.monitorService.saveMonitor(monitor));
	}
	
	// delete monitor record
	@DeleteMapping("monitors/{id}")
	public String deleteMonitor(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		this.monitorService.deleteMonitor(this.monitorService.getMonitorById(id));
		return "Record with id :: "+id+" is deleted";
	}
}
