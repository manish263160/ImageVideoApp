package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable {
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
		return this.shopType;
	}

	public void setShopType(int shopType) {
		this.shopType = shopType;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getShopAddress() {
		return this.shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopkeeperName() {
		return this.shopkeeperName;
	}

	public void setShopkeeperName(String shopkeeperName) {
		this.shopkeeperName = shopkeeperName;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public int hashCode() {
		boolean prime = true;
		byte result = 1;
		int result1 = 31 * result + (this.userId == null ? 0 : this.userId.hashCode());
		result1 = 31 * result1 + (this.userType == null ? 0 : this.userType.hashCode());
		return result1;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof User)) {
			return false;
		} else {
			User other = (User) obj;
			if (this.userId == null) {
				if (other.userId != null) {
					return false;
				}
			} else if (!this.userId.equals(other.userId)) {
				return false;
			}

			if (this.userType == null) {
				if (other.userType != null) {
					return false;
				}
			} else if (!this.userType.equals(other.userType)) {
				return false;
			}

			return true;
		}
	}

	public String toString() {
		return "User [id=" + this.userId + ", userType=" + this.userType + ", password=" + this.password + ", Name="
				+ this.name + ",, email=" + this.email + "]";
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserImage() {
		return this.userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getShopDescription() {
		return this.shopDescription;
	}

	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}
}