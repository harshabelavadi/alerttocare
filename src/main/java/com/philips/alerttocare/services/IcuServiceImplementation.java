package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IIcuService;
import com.philips.alerttocare.models.Icu;
import com.philips.alerttocare.repositories.IcuRepository;

@Service("icuService")
public class IcuServiceImplementation implements IIcuService {

	private IcuRepository icuRepository;
	
	@Autowired
	public IcuServiceImplementation(IcuRepository icuRepository) {
		this.icuRepository = icuRepository;
	}
	
	@Override
	public List<Icu> getAllIcus() {
		return icuRepository.findAll();
	}

	@Override
	public Icu getIcuById(long id) throws ResourceNotFoundException {
		Icu icu = icuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Icu record not found for this id ::" + id));
		return icu;
	}

	@Override
	public Icu saveIcu(Icu icu) {
		return icuRepository.save(icu);
	}

	@Override
	public void deleteIcu(Icu icu) {
		icuRepository.delete(icu);
	}

}
