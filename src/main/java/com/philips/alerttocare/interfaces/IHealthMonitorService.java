package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.HealthMonitor;

public interface IHealthMonitorService {
	public List<HealthMonitor> getAllMonitors();
	public HealthMonitor getMonitorById(long id) throws ResourceNotFoundException;
	public HealthMonitor saveMonitor(HealthMonitor healthMonitor);
	public void deleteMonitor(HealthMonitor healthMonitor);
}
