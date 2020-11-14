package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.StaffDetails;

public interface IStaffDetailsService {
	public List<StaffDetails> getAllStaffDetails();
	public StaffDetails getStaffDetailsById(long id) throws ResourceNotFoundException;
	public StaffDetails saveStaffDetails(StaffDetails staffDetails);
	public void deleteStaffDetails(StaffDetails staffDetails);
}
