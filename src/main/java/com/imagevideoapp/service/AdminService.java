package com.imagevideoapp.service;

import java.util.List;

import com.imagevideoapp.models.CategrySeriesModels;

public interface AdminService {

	List<CategrySeriesModels> getAllCategorySeries(String fetchTable);

	boolean insertCategory(String value ,String name);

	boolean deleteCatSer(String value, int id);

	boolean editCategorySeries(String table, String name, int id);

}
