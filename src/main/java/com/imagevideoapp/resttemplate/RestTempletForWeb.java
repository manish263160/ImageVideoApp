package com.imagevideoapp.resttemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagevideoapp.Enums.STATUS;
import com.imagevideoapp.models.ApplicationPropertyKeyVal;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.FetchVideoJson;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.service.AdminService;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.service.UserService;

@RestController
@RequestMapping("/restTempletForWeb")
public class RestTempletForWeb {

	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value = "/getAllProperties", method = RequestMethod.GET )
	public ResponseEntity<List<ApplicationPropertyKeyVal>> getAllProperties() {
		List<ApplicationPropertyKeyVal> applicationproperties= adminService.getAllProperties();
		
		return new ResponseEntity<List<ApplicationPropertyKeyVal>>(applicationproperties, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getALlImages", method = RequestMethod.GET )
    public ResponseEntity<List<UploadedImage>> getALlImages() {
		Long userId=3l;//This is for showofff.hello@gmail.com (null,"uploaded_image" "all")
        List<UploadedImage> uploadedimage = userService.getAllImages(userId,"uploaded_image", "all");
        if(uploadedimage.isEmpty()){
            return new ResponseEntity<List<UploadedImage>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UploadedImage>>(uploadedimage, HttpStatus.OK);
    }
	@RequestMapping(value = "/pushDeviceId", method = RequestMethod.GET )
    public ResponseEntity<Boolean> pushDeviceId(@RequestParam(value="deviceId") String deviceId) {
        boolean isDeviceIdInsert = notificationService.insertDevice(deviceId);
        
        return new ResponseEntity<Boolean>(isDeviceIdInsert, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/fetchAllVids", method = RequestMethod.GET )
    public ResponseEntity<Map<String, List<FetchVideoJson>>> fetchAllVids(@RequestParam(required = false)String start ,@RequestParam(required = false) String end) {
		Map<String, List<FetchVideoJson>> finalmap=new HashMap<String, List<FetchVideoJson>>();
		
		String token="categoryWise";
		String token1="seriesWise";
		
		List<FetchVideoJson> categoriesWise = adminService.fetchAllVids(token,start,end);
		List<FetchVideoJson> seriesWise = adminService.fetchAllVids(token1,start,end);
		
        finalmap.put("categoriesData", categoriesWise);
        finalmap.put("seriesData", seriesWise);
        return new ResponseEntity<Map<String, List<FetchVideoJson>>> (finalmap, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/webSeries", method = RequestMethod.GET )
    public ResponseEntity<List<FetchVideoJson>> webSeries(@RequestParam(required = false)String start ,@RequestParam(required = false) String end) {
		
		String token="categoryWise";
		String token1="seriesWise";
		
//		List<FetchVideoJson> categoriesWise = adminService.fetchAllVids(token,start,end);
		List<FetchVideoJson> seriesWise = adminService.fetchAllVids(token1,start,end);
		
        return new ResponseEntity<List<FetchVideoJson>> (seriesWise, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/searchVideo", method = RequestMethod.GET )
    public ResponseEntity<List<GetVideoByCatSerDto>> searchVideo(@RequestParam(value="data") String data) {
		
		List<GetVideoByCatSerDto> getdata=adminService.searchVideo(data);
		
		return new ResponseEntity<List<GetVideoByCatSerDto>>(getdata, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/fetchBunchOfImage", method = RequestMethod.GET )
    public ResponseEntity<List<UploadedImage>> fetchBunchOfImage(@RequestParam String  start,@RequestParam String  end
    		,@RequestParam(required=false) String  categoryName) {
		List<UploadedImage> getData = adminService.fetchBunchOfImage(categoryName, start, end);
		return new ResponseEntity<List<UploadedImage>>(getData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fetchVideoByCatSeries", method = RequestMethod.GET)
	public ResponseEntity<List<UploadedVideo>> fetchVideoByCatSeries(@RequestParam String start,
			@RequestParam String end, @RequestParam(required = false) String categoryOrSeriesName,
			@RequestParam String token) {
		String queryFor = "category" ;
		if(token.equals("1")) {  // this is for category case.
			queryFor="category";
		}else if(token.equals("2")){      // this is for serties case.
			queryFor ="series";
		}
		List<UploadedVideo> getData = adminService.fetchVideoByCatSeries(categoryOrSeriesName, start, end , queryFor);
		return new ResponseEntity<List<UploadedVideo>>(getData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getRestAllCategory", method = RequestMethod.GET)
	public ResponseEntity<List<CategrySeriesModels>> getRestAllCategory(@RequestParam String token) {
		List<CategrySeriesModels> getCat =null;
		if(token.equals("video")) {
			String table="uploaded_video";
			getCat = adminService.getRestAllCategory(STATUS.VIDEO.ID, table); 
		}
		if(token.equals("image")) {
			String table="uploaded_image";
			getCat = adminService.getRestAllCategory(STATUS.IMAGE.ID, table);	
		}
		return new ResponseEntity<List<CategrySeriesModels>>(getCat, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getRestAllSeries", method = RequestMethod.GET)
	public ResponseEntity<List<CategrySeriesModels>> getRestAllSeries() {
		List<CategrySeriesModels> getCat =null;
		String fetchTable="series";
		List<CategrySeriesModels> serieslist=adminService.getAllCategorySeries(fetchTable , null);
		return new ResponseEntity<List<CategrySeriesModels>>(serieslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allCategorywiseVidsForUI", method = RequestMethod.GET )
	public ResponseEntity<List<UploadedVideo>> allCategorywiseVidsForUI(@RequestParam(required = false) String catId) {
		
		List<UploadedVideo> list=adminService.getAllVidsForUI(catId);
		
		return new ResponseEntity<List<UploadedVideo>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchImage", method = RequestMethod.GET )
    public ResponseEntity<List<UploadedImage>> searchImage(@RequestParam(value="text") String text) {
		
		List<UploadedImage> getdata=adminService.searchImage(text);
		
		return new ResponseEntity<List<UploadedImage>>(getdata, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/allCategoryWiseImageForUI", method = RequestMethod.GET )
	public ResponseEntity<List<UploadedImage>> allCategoryWiseImageForUI(@RequestParam(required = false) String categoryOrSeriesName) {
		
		List<UploadedImage> list=adminService.getAllImageForUI(categoryOrSeriesName );
		
		return new ResponseEntity<List<UploadedImage>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllWebSeriesVideo", method = RequestMethod.GET )
	public ResponseEntity<List<FetchVideoJson>> getAllWebSeriesVideo() {
		
		String token= "seriesWise";
		List<FetchVideoJson> seriesWise = adminService.fetchAllVidsWeb(token);
		
		return new ResponseEntity<List<FetchVideoJson>>(seriesWise, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSpecificVids", method = RequestMethod.GET )
	public ResponseEntity<List<UploadedVideo>> getSpecificVids(int id) {
		
		 String tableName ="uploaded_video";
		 List<UploadedVideo> finallist=new ArrayList<UploadedVideo>();
		UploadedVideo seriesWise = userService.getImageByImgId(id , tableName , false);
		finallist.add(seriesWise);
		return new ResponseEntity<List<UploadedVideo>>(finallist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSpecificImage", method = RequestMethod.GET )
	public ResponseEntity<List<UploadedImage>> getSpecificImage(int id) {
		 List<UploadedImage> finallist=new ArrayList<UploadedImage>();
		 String tableName ="uploaded_image";
		 UploadedImage seriesWise = userService.getImageByImgId(id , tableName , false);
		 finallist.add(seriesWise);
		return new ResponseEntity<List<UploadedImage>>(finallist, HttpStatus.OK);
	}
	
}
