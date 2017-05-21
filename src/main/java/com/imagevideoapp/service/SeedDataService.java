package com.imagevideoapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.imagevideoapp.models.CitySeed;
import com.imagevideoapp.models.Country;


public interface SeedDataService {
	
	List<Country> getAllCountries();
	
		
	List<CitySeed> getAllCitiesFromCountryId(long countryId);

	Map<Integer, String> getAllCurrencyUnit();
	Map<Integer, String> getAllCurrencyUnitCode();
	
}
