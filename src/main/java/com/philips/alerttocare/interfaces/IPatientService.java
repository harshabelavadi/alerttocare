package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.Patient;

public interface IPatientService {
	public List<Patient> getAllPatients();
	public Patient getPatientById(long id) throws ResourceNotFoundException;
	public Patient savePatient(Patient patient);
	public void deletePatient(Patient patient);
}
