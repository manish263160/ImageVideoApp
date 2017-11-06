package com.imagevideoapp.dao;

import java.util.List;

import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.models.User;

public interface AdminDao {

	List<CategrySeriesModels> getAllCategorySeries(String fetchTable, Long userId, String fromController);

	List<CategrySeriesModels> getAllCategoryForImages(User user, int catFor);
	boolean insertCategory(String value ,String name, Long userId, String catFor);

	boolean deleteCatSer(String value, int id, Long userID);

	boolean editCategorySeries(String table, String name, int id);

	List<GetVideoByCatSerDto> fetchAllVids(String token, String start, String end);

	List<GetVideoByCatSerDto> SearchVuds(String data);

	List<UploadedImage> fetchBunchOfImage( String categoryName, String start, String end);

	List<UploadedVideo> fetchVideoByCatSeries(String categoryOrSeriesName, String start, String end, String queryFor);


}
