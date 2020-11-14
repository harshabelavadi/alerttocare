package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IHealthMonitorService;
import com.philips.alerttocare.models.HealthMonitor;
import com.philips.alerttocare.repositories.HealthMonitorRepository;

@Service("monitorService")
public class HealthMonitorServiceImplementation implements IHealthMonitorService {

	@Autowired
	private HealthMonitorRepository monitorRepository;
	
	@Override
	public List<HealthMonitor> getAllMonitors() {
		return monitorRepository.findAll();
	}

	@Override
	public HealthMonitor getMonitorById(long id) throws ResourceNotFoundException {
		HealthMonitor monitor = monitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Monitor record not found for this id ::" + id));
		return monitor;
	}

	@Override
	public HealthMonitor saveMonitor(HealthMonitor healthMonitor) {
		return monitorRepository.save(healthMonitor);
	}

	@Override
	public void deleteMonitor(HealthMonitor healthMonitor) {
		monitorRepository.delete(healthMonitor);
	}

}
