package com.philips.alerttocare.interfaces;

import java.util.List;

import com.philips.alerttocare.exceptions.ResourceNotFoundException;
import com.philips.alerttocare.models.Icu;

public interface IIcuService {
	public List<Icu> getAllIcus();
	public Icu getIcuById(long id) throws ResourceNotFoundException;
	public Icu saveIcu(Icu icu);
	public void deleteIcu(Icu icu);
}
