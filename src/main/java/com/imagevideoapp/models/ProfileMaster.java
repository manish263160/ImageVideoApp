package com.imagevideoapp.models;

import java.io.Serializable;


public class ProfileMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8945688875059690248L;

	private Integer profileId;	

	private String type ;

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	



}
