package com.imagevideoapp.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.imagevideoapp.models.User;
import com.imagevideoapp.service.MailingService;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.AESEncrypter;
import com.imagevideoapp.utils.ApplicationConstants;
import com.imagevideoapp.utils.ApplicationProperties;

@Controller
public class MainController {
	private static final Logger logger = Logger.getLogger(AppController.class);
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private MailingService mailService;
	
	private @Autowired VelocityEngine velocityEngine;
	
	@Value("${mail.username}")
	private String senderMailId;
	
	@Autowired
	private UserService userService ;

	@RequestMapping(value = { "/", "/welcome" }, method = { RequestMethod.GET })
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is welcome page!");
		return "welcomePage";
	}

	@RequestMapping(value = { "/loginpage" }, method = { RequestMethod.GET })
	public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
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

	@RequestMapping(value = { "/logout" }, method = { RequestMethod.GET })
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			(new SecurityContextLogoutHandler()).logout(request, response, auth);
		}

		return "redirect:/loginpage?logout";
	}

	@RequestMapping(value = { "/forgotpassword" }, method = { RequestMethod.GET})
	@ResponseBody
	public String deleteImages(@RequestParam("email") String email , @RequestParam(value="newpassword",required=false) String newpassword,HttpServletRequest request) throws IOException {
		try {
			logger.info("email for forgot password==="+email+" newpassword ="+newpassword);
			 User existuser= userService.checkUserByEmailorID(email);
			 boolean bool=false;
			 if(existuser == null){
				 return "NOT_FOUND";
			 }else{
//				 bool= userService.resetPassword(isemailExist,newpassword);
				 try {
					 String plainText = System.currentTimeMillis() + "##" + existuser.getUserId();
					 String token = AESEncrypter.encrypt(plainText);
					 String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
					 url+="/user/generateNewPass/"+URLEncoder.encode(token, "UTF-8");
					 logger.info("url for mail ==="+url);
					 userService.insertPassGenToken(existuser.getUserId(),token);
						Map<String, Object> storemap = new HashMap<String, Object>();
						storemap.put("fromUserName", ApplicationConstants.TEAM_NAME);
						storemap.put("url", url);
						String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
								"email_Templates/forgotPasswordEmail.vm", "UTF-8", storemap);

						mailService.sendMail(senderMailId, new String[] { existuser.getEmail() }, null, "Forgot Password", text);
						return "success";
					} catch (Exception e) {
						logger.error("::runProfileIncompleteCron()  exception ==" + e);
						return "fail";
					} 
				 
			 }
		
			
		} catch (EmptyResultDataAccessException arg6) {
			logger.error(" EmptyResultDataAccessException");
			return "fail";
		} catch (DataAccessException arg7) {
			logger.error(" DataAccessException");
			return "fail";
		}
	}
	
	@RequestMapping(value = { "/admin" }, method = { RequestMethod.GET })
	public String adminPage(Model model) {
		return "adminPage";
	}

	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public String login(ModelMap model, HttpServletRequest request) {
		logger.debug("start has been done");
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		model.addAttribute("error", request.getParameter("error"));
		return "login";
	}

	@RequestMapping(value = { "/403" }, method = { RequestMethod.GET })
	public String accessDenied(Model model, Principal principal) {
		if (principal != null) {
			model.addAttribute("message",
					"Hi " + principal.getName() + "<br> You do not have permission to access this page!");
		} else {
			model.addAttribute("msg", "You do not have permission to access this page!");
		}

		return "403Page";
	}
}