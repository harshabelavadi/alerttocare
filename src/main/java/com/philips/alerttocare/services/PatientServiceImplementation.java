package com.philips.alerttocare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.interfaces.IPatientService;
import com.philips.alerttocare.models.Patient;
import com.philips.alerttocare.repositories.PatientRepository;

@Service("patientService")
public class PatientServiceImplementation implements IPatientService{

	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public Patient getPatientById(long id) throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient record not found for this id ::" + id));
		return patient;
	}

	@Override
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public void deletePatient(Patient patient) {
		patientRepository.delete(patient);
	}

}
