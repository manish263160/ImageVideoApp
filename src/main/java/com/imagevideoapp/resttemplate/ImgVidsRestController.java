package com.imagevideoapp.resttemplate;

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

import com.imagevideoapp.models.FetchVideoJson;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.service.AdminService;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.service.UserService;


@RestController
@RequestMapping("/restcontroller")
public class ImgVidsRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value = "/getALlImages", method = RequestMethod.GET ,consumes="application/json")
    public ResponseEntity<List<UploadedImage>> listAllUsers() {
		Long userId=3l;//This is for showofff.hello@gmail.com (null,"uploaded_image" "all")
        List<UploadedImage> uploadedimage = userService.getAllImages(userId,"uploaded_image", "all");
        if(uploadedimage.isEmpty()){
            return new ResponseEntity<List<UploadedImage>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UploadedImage>>(uploadedimage, HttpStatus.OK);
    }
	@RequestMapping(value = "/pushDeviceId", method = RequestMethod.GET ,consumes="application/json")
    public ResponseEntity<Boolean> pushDeviceId(@RequestParam(value="deviceId") String deviceId) {
        boolean isDeviceIdInsert = notificationService.insertDevice(deviceId);
        
        return new ResponseEntity<Boolean>(isDeviceIdInsert, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/fetchAllVids", method = RequestMethod.GET ,consumes="application/json")
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
	
	@RequestMapping(value = "/Search", method = RequestMethod.GET ,consumes="application/json")
    public ResponseEntity<List<GetVideoByCatSerDto>> SearchVuds(@RequestParam(value="data") String data) {
		
		List<GetVideoByCatSerDto> getdata=adminService.SearchVuds(data);
		
		return new ResponseEntity<List<GetVideoByCatSerDto>>(getdata, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/fetchBunchOfImage", method = RequestMethod.GET ,consumes="application/json")
    public ResponseEntity<List<UploadedImage>> fetchBunchOfImage(@RequestParam String  start,@RequestParam String  end
    		,@RequestParam String  categoryName) {
		List<UploadedImage> getData = adminService.fetchBunchOfImage(categoryName, start, end);
		return new ResponseEntity<List<UploadedImage>>(getData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fetchVideoByCatSeries", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<UploadedVideo>> fetchVideoByCatSeries(@RequestParam String start,
			@RequestParam String end, @RequestParam String categoryOrSeriesName,
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
}
