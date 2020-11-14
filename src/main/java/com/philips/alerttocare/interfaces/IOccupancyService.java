package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.Occupancy;

public interface IOccupancyService {
	public List<Occupancy> getAllOccupancies();
	public Occupancy getOccupancyById(long id) throws ResourceNotFoundException;
	public Occupancy saveOccupancy(Occupancy occupancy);
	public void deleteOccupancy(Occupancy occupancy);
}
