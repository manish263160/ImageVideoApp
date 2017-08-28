package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.Date;

public class GetVideoByCatSerDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	private String seriesName;
	private long id;
	private long userId;
	private long catId;
	private String  videoLink;
	private String videoThumbnail;
	private long categoryId;
	private long seriesId;
	private String timeLength;
	private String title;
	private String description;
	private Date createdOn;
	private String createdBy;
	private String videoName;
	private String newSetDate;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
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
	public long getCatId() {
		return catId;
	}
	public void setCatId(long catId) {
		this.catId = catId;
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
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
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
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getNewSetDate() {
		return newSetDate;
	}
	public void setNewSetDate(String newSetDate) {
		this.newSetDate = newSetDate;
	}
}
