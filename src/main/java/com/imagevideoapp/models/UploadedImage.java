package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.Date;

public class UploadedImage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long userId;
	private String imageUrl;
	private String imageLink;
	private String imageDescription;
	private Date createdOn;
	private String createdBy;
	private Integer linkType;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	public Integer getLinkType() {
		return linkType;
	}
	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
