package com.imagevideoapp.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FetchVideoJson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String seriesName;
	private List<GetVideoByCatSerDto> seriesList;
	
	private String categoryName;
	private List<GetVideoByCatSerDto> categoryList;
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public List<GetVideoByCatSerDto> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<GetVideoByCatSerDto> seriesList) {
		this.seriesList = seriesList;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<GetVideoByCatSerDto> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<GetVideoByCatSerDto> categoryList) {
		this.categoryList = categoryList;
	}
	
	
}
