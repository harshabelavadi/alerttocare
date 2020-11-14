package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.Bed;

public interface IBedService {
	public List<Bed> getAllBeds();
	public Bed getBedById(long id) throws ResourceNotFoundException;
	public Bed saveBed(Bed bed);
	public void deleteBed(Bed bed);
}
