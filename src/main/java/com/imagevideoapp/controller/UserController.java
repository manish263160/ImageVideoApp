package com.imagevideoapp.controller;

import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.ApplicationProperties;
import com.imagevideoapp.utils.GenUtilitis;

@Controller
@RequestMapping({ "/user" })
@SessionAttributes({ "userdata" })
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	private ApplicationProperties applicationProperties;

	@RequestMapping(value = { "/userregistration" }, method = { RequestMethod.GET })
	public String register(@RequestParam(name = "error", required = false) String error, @ModelAttribute User user,
			ModelMap map, HttpServletRequest request) {
		logger.debug("register start");
		map.addAttribute("error", error);
		return "user/registrationpage";
	}

	@RequestMapping(value = { "/insertUser" }, method = { RequestMethod.POST })
	public String insertUser(@ModelAttribute User user, ModelMap map, HttpServletRequest request) {
		logger.debug("register start");

		try {
			this.userService.insertUser(user);
			return "user/userRegSuccess";
		} catch (GenericException arg5) {
			System.out.println(arg5.getMessage());
			return "redirect:userregistration?error=" + arg5.getMessage();
		}
	}

	@RequestMapping(value = { "/activateUser" }, method = { RequestMethod.GET })
	public String activateUser(@RequestParam String token, HttpServletRequest request) {
		try {
			if (GenUtilitis.getLoggedInUser() != null) {
				request.logout();
				SecurityContextHolder.getContext().setAuthentication((Authentication) null);
			}

			String e = this.userService.activateUser(token);
			return "redirect:/login.htm?message=" + e;
		} catch (Exception arg3) {
			arg3.printStackTrace();
			logger.error("::activateUser: Exception occurred!!");
			return "redirect:/login.htm";
		}
	}

	@RequestMapping(value = { "/homepage" }, method = { RequestMethod.GET })
	public String homePage(Model model, HttpServletRequest request) {
		User user = GenUtilitis.getLoggedInUser();
		model.addAttribute("user", user);
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		return "user/userHomepage";
	}

	@RequestMapping(value = { "/uploadImage" }, method = { RequestMethod.GET })
	public String uploadImage(@RequestParam(name = "error", required = false) String error, Model model,
			HttpServletRequest request) {
		User user = GenUtilitis.getLoggedInUser();
		model.addAttribute("user", user);
		model.addAttribute("error", error);
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		return "imageUpload/uploadImage";
	}

	@RequestMapping(value = { "/uploadVideo" }, method = { RequestMethod.GET })
	public String uploadVideo(@RequestParam(name = "error", required = false) String error, Model model,
			HttpServletRequest request) {
		User user = GenUtilitis.getLoggedInUser();
		model.addAttribute("user", user);
		model.addAttribute("error", error);
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		return "videoUpload/uploadVideo";
	}

	@RequestMapping(value = { "{pathvariable}/getAllFile" }, method = { RequestMethod.GET })
	public String getAllImages(@PathVariable String pathvariable,
			@RequestParam(name = "error", required = false) String error, Model model, HttpServletRequest request) {
		User user = GenUtilitis.getLoggedInUser();
		String tablename = null;
		if (pathvariable.equals("image")) {
			tablename = "uploaded_image";
		} else if (pathvariable.equals("video")) {
			tablename = "uploaded_video";
		}

		List<UploadedImage> allfileList = this.userService.getAllImages(user.getUserId(), "all");
		LinkedHashSet<String> uniqueDate = new LinkedHashSet<String>();
		allfileList.forEach((imgObj) -> {
			if (imgObj.getCreatedOn() != null) {
				String key = imgObj.getNewSetDate();
				uniqueDate.add(key.trim());
			}

		});
		logger.info("dataoooooooooooo==" + uniqueDate);
		model.addAttribute("user", user);
		model.addAttribute("error", error);
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		model.addAttribute("allfileList", allfileList);
		model.addAttribute("uniqueDate", uniqueDate);
		return pathvariable.equals("image") ? "imageUpload/userAllImagesGallery" : "videoUpload/userAllVideoGallery";
	}
}