package com.imagevideoapp.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagevideoapp.dao.SeedDataDao;
import com.imagevideoapp.models.CitySeed;
import com.imagevideoapp.models.Country;
import com.imagevideoapp.service.SeedDataService;
import com.imagevideoapp.utils.ApplicationProperties;

@Service
public class SeedDataServiceImpl implements SeedDataService {

//	private @Autowired SeedDataDao seedDao;
	private @Autowired ApplicationProperties applicationProperties;
	
	public List<Country> getAllCountries(){
//		return applicationProperties.getAllCountries();
		return null;
		//return seedDao.getAllCountries(); 
	}
	
	public List<CitySeed> getAllCitiesFromCountryId(long countryId){
//		return applicationProperties.getAllCitiesByCountryId(countryId);
		return null;
		//return seedDao.getAllCitiesFromCountryId(countryId);
	}

	public Map<Integer, String> getAllCurrencyUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, String> getAllCurrencyUnitCode() {
		// TODO Auto-generated method stub
		return null;
	}

	}

