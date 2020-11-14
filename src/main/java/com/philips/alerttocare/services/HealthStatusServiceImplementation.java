package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IHealthStatusService;
import com.philips.alerttocare.models.HealthStatus;
import com.philips.alerttocare.repositories.HealthStatusRepository;

@Service("statusService")
public class HealthStatusServiceImplementation implements IHealthStatusService {

	@Autowired
	private HealthStatusRepository statusRepository;
	
	@Override
	public List<HealthStatus> getAllStatus() {
		return statusRepository.findAll();
	}

	@Override
	public HealthStatus getStatusById(long id) throws ResourceNotFoundException {
		HealthStatus status = statusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Status record not found for this id ::" + id));
		return status;
	}

	@Override
	public HealthStatus saveStatus(HealthStatus healthstatus) {
		return statusRepository.save(healthstatus);
	}

	@Override
	public void deleteStatus(HealthStatus healthstatus) {
		statusRepository.delete(healthstatus);
	}

}
