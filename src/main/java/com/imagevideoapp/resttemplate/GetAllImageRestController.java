package com.imagevideoapp.resttemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.service.UserService;


@RestController
@RequestMapping("/restcontroller")
public class GetAllImageRestController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/getALlImages", method = RequestMethod.GET ,consumes="application/json")
    public ResponseEntity<List<UploadedImage>> listAllUsers() {
        List<UploadedImage> uploadedimage = userService.getAllImages(null);
        if(uploadedimage.isEmpty()){
            return new ResponseEntity<List<UploadedImage>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UploadedImage>>(uploadedimage, HttpStatus.OK);
    }
}
