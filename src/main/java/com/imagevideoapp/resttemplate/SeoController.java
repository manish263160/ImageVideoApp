package com.imagevideoapp.resttemplate;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.service.UserService;


@Controller
public class SeoController {

	private static final Logger logger = Logger.getLogger(SeoController.class);
	@Autowired
	UserService userService;
	
	private @Autowired VelocityEngine velocityEngine;
	
	@RequestMapping(value = "/PrerenderWeb/{specific}/{id}", method = RequestMethod.GET)
	public String sayHelloAgain(ModelMap model,@PathVariable String specific , @PathVariable String id ) {
		
	logger.info("id got heree iss specific ====="+specific+"===================================="+id);
	int idfor;
	if(id != null) {
		idfor = Integer.parseInt(id);
	}else {
		idfor =0;
	}
	logger.info("the value of Idfor ====="+idfor);
	String hostUrl= "http://showoff.tv/";
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
	
	@RequestMapping(value = "/robots.txt", method = RequestMethod.GET)
	@ResponseBody
	public String robot(HttpServletResponse response) {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				"email_Templates/robots.vm", "UTF-8", null);
	}
}
