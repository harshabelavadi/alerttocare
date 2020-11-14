package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IStaffDetailsService;
import com.philips.alerttocare.models.StaffDetails;
import com.philips.alerttocare.repositories.StaffDetailsRepository;

@Service("staffdetailsService")
public class StaffDetailsServiceImplementation implements IStaffDetailsService {

	@Autowired
	private StaffDetailsRepository staffdetailsRepository;
	
	@Override
	public List<StaffDetails> getAllStaffDetails() {
		return staffdetailsRepository.findAll();
	}

	@Override
	public StaffDetails getStaffDetailsById(long id) throws ResourceNotFoundException {
		StaffDetails staffdetails = staffdetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff details record not found for this id ::" + id));
		return staffdetails; 
	}

	@Override
	public StaffDetails saveStaffDetails(StaffDetails staffDetails) {
		return staffdetailsRepository.save(staffDetails);
	}

	@Override
	public void deleteStaffDetails(StaffDetails staffDetails) {
		staffdetailsRepository.delete(staffDetails);
	}

}
