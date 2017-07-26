package com.imagevideoapp.dao;

import java.util.List;

import com.imagevideoapp.models.CategrySeriesModels;

public interface AdminDao {

	List<CategrySeriesModels> getAllCategorySeries(String fetchTable, Long userId);

	boolean insertCategory(String value ,String name, Long userId);

	boolean deleteCatSer(String value, int id, Long userID);

	boolean editCategorySeries(String table, String name, int id);

}
