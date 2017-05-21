package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;





public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6130270477630445783L;

	private Long userId;

	private Integer userType;
	
	private String email;
		
	private String password;
	
	private String userImage;
	
	private String mobileNo;

	private String name;

	private String gender;
	
	private int status;
	
	@JsonIgnore
	private Date createdOn;
	
	private String createBy;
	
	@JsonIgnore
	private Date modifiedOn;
	
	private String modifiedBy;


	private String token; 


	private String shopkeeperName;
	private String shopName;
	private String shopDescription;
	private String shopAddress;
	private int shopType;

	public int getShopType() {
		return shopType;
	}

	public void setShopType(int shopType) {
		this.shopType = shopType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopkeeperName() {
		return shopkeeperName;
	}

	public void setShopkeeperName(String shopkeeperName) {
		this.shopkeeperName = shopkeeperName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		return true;
	}

	/*
	 * DO-NOT-INCLUDE passwords in toString function.
	 * It is done here just for convenience purpose.
	 */
	@Override
	public String toString() {
		return "User [id=" + userId + ", userType=" + userType + ", password=" + password
				+ ", Name=" + name+ ","
				+ ", email=" + email + "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getShopDescription() {
		return shopDescription;
	}

	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}


	
}
