package com.imagevideoapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.NotificationDetails;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.utils.ApplicationProperties;

@Controller
@RequestMapping({ "/appNotification" })
public class NotificationController {
	
	private static final Logger logger = Logger.getLogger(NotificationController.class);
	@Autowired
	private ApplicationProperties applicationProperties;
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value = { "/notificationPage" }, method = { RequestMethod.GET })
	public String notificationPage(@RequestParam(name = "error", required = false) String error, @ModelAttribute User user,
			ModelMap map, HttpServletRequest request) {
		map.addAttribute("error", error);
		map.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		return "notification/notificationPage";
	}
	
	@RequestMapping(value = { "/pushNotification" }, method = { RequestMethod.POST })
	public String pushNotification(@RequestParam(name = "error", required = false) String error, @ModelAttribute NotificationDetails notificationDetails,
			ModelMap map, HttpServletRequest request) throws GenericException {
		map.addAttribute("error", error);
		map.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		boolean sendNotification = notificationService.sendNotification(notificationDetails);
		if(sendNotification){
			map.addAttribute("isSendNotification",true);
		}
		return "notification/notificationPage";
	}
}
