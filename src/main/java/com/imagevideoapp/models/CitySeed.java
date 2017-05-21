package com.imagevideoapp.models;

import java.util.Date;

public class CitySeed {
	private long id ;
	private long stateId;
	private String name;
	private boolean status;
	private long country_id;
	/**
	 * @return the country_id
	 */
	public long getCountry_id() {
		return country_id;
	}
	/**
	 * @param country_id the country_id to set
	 */
	public void setCountry_id(long country_id) {
		this.country_id = country_id;
	}
	private Date createdOn;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
