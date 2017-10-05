package com.imagevideoapp.dao;

import java.util.List;

import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.GetVideoByCatSerDto;

public interface AdminDao {

	List<CategrySeriesModels> getAllCategorySeries(String fetchTable, Long userId);

	boolean insertCategory(String value ,String name, Long userId);

	boolean deleteCatSer(String value, int id, Long userID);

	boolean editCategorySeries(String table, String name, int id);

	List<GetVideoByCatSerDto> fetchAllVids(String token, String start, String end);

	List<GetVideoByCatSerDto> SearchVuds(String data);

}
