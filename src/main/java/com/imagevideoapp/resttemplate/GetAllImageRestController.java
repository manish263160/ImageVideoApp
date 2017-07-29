package com.imagevideoapp.resttemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.service.UserService;


@RestController
@RequestMapping("/restcontroller")
public class GetAllImageRestController {

	@Autowired
	UserService userService;
	
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
}
