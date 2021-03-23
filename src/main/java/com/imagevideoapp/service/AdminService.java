package com.imagevideoapp.service;

import java.util.List;

import com.imagevideoapp.models.ApplicationPropertyKeyVal;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.FetchVideoJson;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;

public interface AdminService {

	List<CategrySeriesModels> getAllCategorySeries(String fetchTable, String fromController);

	List<CategrySeriesModels> getAllCategoryForImagesVideo(int catFor);
	boolean insertCategory(String value ,String name, String catFor);

	boolean deleteCatSer(String value, int id);

	boolean editCategorySeries(String table, String name, int id);

	List<FetchVideoJson> fetchAllVidsWeb(String token);
	
	List<FetchVideoJson> fetchAllVids(String token, String start, String end);

	List<GetVideoByCatSerDto> searchVideo(String data);

	List<UploadedImage> fetchBunchOfImage(String categoryName, String start, String end);

	List<UploadedVideo> fetchVideoByCatSeries(String categoryOrSeriesName, String start, String end, String queryFor);

	List<UploadedVideo> getAllVidsForUI(String catId);

	List<ApplicationPropertyKeyVal> getAllProperties();

	List<UploadedImage> searchImage(String text);

	List<UploadedImage> getAllImageForUI(String categoryOrSeriesName);

	List<UploadedVideo> getAllWebSeriesVideo();

	List<CategrySeriesModels> getRestAllCategory(int catFor, String table);

	boolean updateVideoTitle(String id, String title, String c_date); 


}
