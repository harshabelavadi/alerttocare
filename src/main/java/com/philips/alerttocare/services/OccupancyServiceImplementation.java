package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IOccupancyService;
import com.philips.alerttocare.models.Occupancy;
import com.philips.alerttocare.repositories.OccupancyRepository;

@Service("occupancyService")
public class OccupancyServiceImplementation implements IOccupancyService {

	@Autowired
	private OccupancyRepository occupancyRepository;
	
	@Override
	public List<Occupancy> getAllOccupancies() {
		return occupancyRepository.findAll();
	}

	@Override
	public Occupancy getOccupancyById(long id) throws ResourceNotFoundException {
		Occupancy occupancy = occupancyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Occupancy record not found for this id ::" + id));
		return occupancy;
	}

	@Override
	public Occupancy saveOccupancy(Occupancy occupancy) {
		return occupancyRepository.save(occupancy);
	}

	@Override
	public void deleteOccupancy(Occupancy occupancy) {
		occupancyRepository.delete(occupancy);
	}

}
