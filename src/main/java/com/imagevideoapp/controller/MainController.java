package com.imagevideoapp.controller;
 
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imagevideoapp.utils.ApplicationConstants;
import com.imagevideoapp.utils.ApplicationProperties;
 
@Controller
public class MainController {
 
	private static final Logger logger = Logger.getLogger(AppController.class);
	
	private @Autowired ApplicationProperties applicationProperties;
	
   @RequestMapping(value = {"/", "/welcome" }, method = RequestMethod.GET)
   public String welcomePage(Model model) {
       model.addAttribute("title", "Welcome");
       model.addAttribute("message", "This is welcome page!");
       return "welcomePage";
   }
   
   @RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out successfully.");
		}

		model.setViewName("loginPage");
		return model;
	}
 
   @RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/loginpage?logout";
	}
   
   @RequestMapping(value = "/admin", method = RequestMethod.GET)
   public String adminPage(Model model) {
       return "adminPage";
   }
 
   @RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(ModelMap model,HttpServletRequest request){
		logger.debug( "start has been done");
		model.addAttribute("themecolor",applicationProperties.getProperty(ApplicationConstants.THEME_COLOR));
		model.addAttribute("error",request.getParameter("error"));
		return "login";
	}
   
   /*@RequestMapping(value="/register",method=RequestMethod.GET)
	public String registeruser(){
		logger.debug( "registration method start");
		return "user/registrationpage";
	}*/
 
 
  
 
   @RequestMapping(value = "/403", method = RequestMethod.GET)
   public String accessDenied(Model model, Principal principal) {
        
       if (principal != null) {
           model.addAttribute("message", "Hi " + principal.getName()
                   + "<br> You do not have permission to access this page!");
       } else {
           model.addAttribute("msg",
                   "You do not have permission to access this page!");
       }
       return "403Page";
   }
}