package com.imagevideoapp.Enums;

import java.io.Serializable;

public enum UserRoleType implements Serializable{
	SUPERADMIN("SUPERADMIN"),
	ADMIN("ADMIN"),
	USER("USER"),
	RESTORENT("RESTORENT");
	
	
	String userProfileType;
	
	private UserRoleType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
