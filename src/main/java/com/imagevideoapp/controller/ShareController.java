package com.imagevideoapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.service.UserService;

@Controller
public class ShareController {

	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/PrerenderWeb/{specific}/{id}", method = RequestMethod.GET)
	public String sayHelloAgain(ModelMap model,@PathVariable String specific , @PathVariable String id) {
		
	logger.info("id got heree iss specific ====="+specific+"===================================="+id);
	int idfor;
	if(id != null) {
		idfor = Integer.parseInt(id);
	}else {
		idfor =0;
	}
	logger.info("the value of Idfor ====="+idfor);
	String hostUrl= "http://205.147.101.198/";
	String tableName ="";
	Map<String, String> ogmap = new HashMap<String, String>();
		if(specific.equals("specificVideo")) {
			tableName ="uploaded_video";
			UploadedVideo vidsdata = userService.getImageByImgId(idfor , tableName , false);
			ogmap.put("ogurl", hostUrl+"/"+specific+"/"+id);
			ogmap.put("ogtitle", vidsdata.getTitle());
			ogmap.put("ogimage", vidsdata.getVideoThumbnail());
			ogmap.put("ogdescription", vidsdata.getDescription());
		}else if(specific.equals("specificStory")) {
			tableName ="uploaded_image";
			 UploadedImage imgdata = userService.getImageByImgId(idfor , tableName , false);
			 ogmap.put("ogurl", hostUrl+"/"+specific+"/"+id);
				ogmap.put("ogtitle", imgdata.getImageName());
				ogmap.put("ogimage", imgdata.getImageUrl());
				ogmap.put("ogdescription", imgdata.getImageLink());
		}
		model.addAttribute("ogmap", ogmap);
		return "sharedpaage";
	}
}
