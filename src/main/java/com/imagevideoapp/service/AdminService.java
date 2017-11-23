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

	List<CategrySeriesModels> getAllCategoryForImagesVideo(int catFor , String table);
	boolean insertCategory(String value ,String name, String catFor);

	boolean deleteCatSer(String value, int id);

	boolean editCategorySeries(String table, String name, int id);

	List<FetchVideoJson> fetchAllVidsWeb(String token);
	
	List<FetchVideoJson> fetchAllVids(String token, String start, String end);

	List<GetVideoByCatSerDto> SearchVuds(String data);

	List<UploadedImage> fetchBunchOfImage(String categoryName, String start, String end);

	List<UploadedVideo> fetchVideoByCatSeries(String categoryOrSeriesName, String start, String end, String queryFor);

	List<UploadedVideo> getAllVidsForUI(String categoryOrSeriesName, String tablename);

	List<ApplicationPropertyKeyVal> getAllProperties();

	List<UploadedImage> searchImage(String text);

	List<UploadedImage> getAllImageForUI(String categoryOrSeriesName);

	List<UploadedVideo> getAllWebSeriesVideo();

	List<UploadedVideo> allCategorywiseVidsForUI(String catId); 


}
