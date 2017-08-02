package com.imagevideoapp.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.FetchVideoJson;
import com.imagevideoapp.models.GetVideoByCatSerDto;

public interface AdminService {

	List<CategrySeriesModels> getAllCategorySeries(String fetchTable);

	boolean insertCategory(String value ,String name);

	boolean deleteCatSer(String value, int id);

	boolean editCategorySeries(String table, String name, int id);

	List<FetchVideoJson> fetchAllVids(String token);

	List<GetVideoByCatSerDto> SearchVuds(String data);

}
