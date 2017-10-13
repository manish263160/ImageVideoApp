package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.Date;

public class UploadedVideo implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private long id;
	private long userId;
	private String  videoLink;
	private String videoThumbnail;
	private String categoryId;
	private String seriesId;
	private String timeLength;
	private String title;
	private String description;
	private Date createdOn;
	private String createdBy;
	private String videoName;
	private String newSetDate;
	private String oldVideoName;
	public String getNewSetDate() {
		return newSetDate;
	}
	public void setNewSetDate(String newSetDate) {
		this.newSetDate = newSetDate;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public String getVideoThumbnail() {
		return videoThumbnail;
	}
	public void setVideoThumbnail(String videoThumbnail) {
		this.videoThumbnail = videoThumbnail;
	}
	
	public String getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		if(categoryId== null) {
			categoryId = "";
		}
		this.categoryId = categoryId;
	}
	public String getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}
	public String getOldVideoName() {
		return oldVideoName;
	}
	public void setOldVideoName(String oldVideoName) {
		this.oldVideoName = oldVideoName;
	}
	
	}
