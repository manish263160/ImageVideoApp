package com.imagevideoapp.utils;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.imagevideoapp.models.CitySeed;
import com.imagevideoapp.models.Country;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;

public class ApplicationProperties extends ImageVideoJdbcDaoSupport {

	private static final Logger logger = Logger.getLogger(ApplicationProperties.class);
	private static final String GET_ALL_CITY = "select city.id,city.name,st.country_id,city.state_id,city.status,city.created_on from cities_m city " 
						+" inner join states_m st on  st.id = city.state_id and st.status=1 "
						+ " where city.status =1 order by st.country_id";
	private static final String GET_ALL_COUNTRY = "select * from countries_m where status = 1 order by name;";
	
	private class ApplicationProperty {
		private String key;
		private String value;


		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key the key to set
		 */
		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private Map<String, String> properties = new HashMap<String, String>();
	private List<Country> countries = new ArrayList<Country>();
	private Map<Long,List<CitySeed>> cityCountryMap = new HashMap<Long, List<CitySeed>>();
//	private static List<String>  swearWords=new ArrayList<String>();
	
	/**
	 * loads all properties
	 */
	
	public void init() {
		logger.info("ApplicationProperties.init() LOADING ALL PROPERTIES...");
		String sql ="select ap.name,ap.value from application_properties ap";
		List<ApplicationProperty> allPropertiesFromDb = getJdbcTemplate().query(sql, new RowMapper<ApplicationProperty>() {
			public ApplicationProperty mapRow(ResultSet rs, int rowNum) throws SQLException {
				ApplicationProperty applicationProperty = new ApplicationProperty();
				applicationProperty.setKey(rs.getString("name"));
				applicationProperty.setValue(rs.getString("value"));
				return applicationProperty;
			}
		});
		
//		sectors.clear();
		countries.clear();
		cityCountryMap.clear();
		properties.clear();
//		swearWords.clear();
		
		
		for (ApplicationProperty applicationProperty : allPropertiesFromDb) {
			properties.put(applicationProperty.getKey(), applicationProperty.getValue());
		}
		
		try {
//			sectors=getJdbcTemplate().query(GET_ALL_SECTOR,new BeanPropertyRowMapper<OrgSector>(OrgSector.class));
			countries=getJdbcTemplate().query(GET_ALL_COUNTRY,new BeanPropertyRowMapper<Country>(Country.class));
			
			cityCountryMap = getJdbcTemplate().query(GET_ALL_CITY,new ResultSetExtractor<Map<Long,List<CitySeed>>>(){
				public Map<Long, List<CitySeed>> extractData(ResultSet rs)throws SQLException {
					Map<Long, List<CitySeed>> cityMap = new HashMap<Long, List<CitySeed>>();
					while(rs.next()){
						long countryId = rs.getLong("country_id");
						CitySeed citySeed = new CitySeed();
						if (cityMap.containsKey(countryId)) {
							setCityData(rs, citySeed, countryId);
							cityMap.get(countryId).add(citySeed);
						} else {
							setCityData(rs, citySeed, countryId);
							List<CitySeed> cList = new ArrayList<CitySeed>();
							cList.add(citySeed);
							cityMap.put(countryId, cList);
						}	
					}
					return cityMap;
				}
				
			});
		/*	swearWords = getJdbcTemplate().query(GET_ALL_SWEAR_WORDS, new ResultSetExtractor<List<String>>(){
				public List<String> extractData(ResultSet rs) throws SQLException,DataAccessException {	
					List<String> swearWordNameList = new ArrayList<String>();
			        while(rs.next()){
			        	swearWordNameList.add(rs.getString("name"));
			        }
			        return swearWordNameList;
				}
			});*/
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info( new StringBuilder().append("Error in ApplicationProperties.").append(". init()"));
		} 
		
		logger.info( "ApplicationProperties.init() Countries size():" +countries.size());
		logger.info( "ApplicationProperties.init() Cities Map size():" +cityCountryMap.size());
//		logger.info( "ApplicationProperties.init() swearWords size():" +swearWords.size());
		logger.info( "ApplicationProperties.init() LOADING ALL PROPERTIES... DONE");
	}
	
	private void setCityData(ResultSet rs, CitySeed citySeed,long countryId) throws SQLException {
		citySeed.setId(rs.getLong("id"));
		citySeed.setName(rs.getString("name"));
		citySeed.setCountry_id(countryId);
		citySeed.setStateId(rs.getLong("state_id"));
		citySeed.setStatus(rs.getBoolean("status"));
	} 
/**
 *  method to get property by passing propertyName
 * @param propertyName -application property name
 * @return propertyValue
 */
	public String getProperty(String propertyName) {
		logger.debug( "ApplicationProperties.getProperty() looking property: " + propertyName);
		
		String propertyValue = properties.get(propertyName);
		logger.debug( "ApplicationProperties.getProperty() property value: " + propertyValue);
		
		return propertyValue;
	}
/**
 * overridden method to get property by passing propertyName and Default
 * @param propertyName-application property name
 * @param Default-default property
 * @return propertyValue
 */
	public String getProperty(String propertyName, String Default) {
		logger.debug( "ApplicationProperties.getProperty(Default) looking property: " + propertyName);
		
		String propertyValue = properties.get(propertyName) == null? Default : properties.get(propertyName);
		logger.debug( "ApplicationProperties.getProperty(Default) property value: " + propertyValue);
		
		return propertyValue;
	}
	
	/**
	 * 
	 * @return country list
	 */
	public List<Country> getAllCountries() {
		return countries != null ? countries : new ArrayList<Country>();
	}
	
	/*public Map<Integer, String> getShopType() {
		return shopType;
	}

	public void setShopType(Map<Integer, String> shopType) {
		this.shopType = shopType;
	}*/

	
	
	
	/**
	 * 
	 * @param countryId
	 * @return List of country based on countryId
	 */
	public List<CitySeed> getAllCitiesByCountryId(long countryId) {
		List<CitySeed> cityList = cityCountryMap.get(countryId);
		if (cityList!=null && cityList.size()>0) {
			Collections.sort(cityList, new Comparator<CitySeed>() {
				public int compare(CitySeed one, CitySeed other) {
					return one.getName().compareTo(other.getName());
				}
			}); 
		}
		return cityList;
	}
	/*public static   List<String>  getAllSwearWords(){
		return swearWords !=null ? swearWords:new ArrayList<String>();
	}*/
}
