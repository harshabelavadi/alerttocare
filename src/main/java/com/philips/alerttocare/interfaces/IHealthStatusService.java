package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.HealthStatus;

public interface IHealthStatusService {
	public List<HealthStatus> getAllStatus();
	public HealthStatus getStatusById(long id) throws ResourceNotFoundException;
	public HealthStatus saveStatus(HealthStatus healthstatus);
	public void deleteStatus(HealthStatus healthstatus);
}
