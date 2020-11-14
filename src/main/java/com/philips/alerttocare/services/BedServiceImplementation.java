package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IBedService;
import com.philips.alerttocare.models.Bed;
import com.philips.alerttocare.repositories.BedRepository;

@Service("bedService")
public class BedServiceImplementation implements IBedService{

	@Autowired
	private BedRepository bedRepository;
	
	@Override
	public List<Bed> getAllBeds() {
		return bedRepository.findAll();
	}

	@Override
	public Bed getBedById(long id) throws ResourceNotFoundException{
		Bed bed = bedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bed record not found for this id ::" + id));
		return bed;
	}

	@Override
	public Bed saveBed(Bed bed) {
		return bedRepository.save(bed);
	}

	@Override
	public void deleteBed(Bed bed) {
		bedRepository.delete(bed);
	}

}
