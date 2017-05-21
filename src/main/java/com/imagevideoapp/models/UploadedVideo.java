package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.Date;

public class UploadedVideo implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private long userId;
	private String videoUrl;
	private Date createdOn;
	private String createdBy;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
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
	
}
