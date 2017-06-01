package com.imagevideoapp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.ApplicationProperties;

/**
 * This is the main controller 
 *  @author manishm
 *
 */

@Controller
@RequestMapping("/")
@SessionAttributes("appcontroller")
public class AppController {
	
	private static final Logger logger = Logger.getLogger(AppController.class);
	
	@Autowired
	UserService userService;
	
	private @Autowired ApplicationProperties applicationProperties;
	
	/*@ModelAttribute("myRequestObject")
	public void addStuffToRequestScope() {
		System.out.println("Inside of addStuffToRequestScope");
	}*/
	
	

}
