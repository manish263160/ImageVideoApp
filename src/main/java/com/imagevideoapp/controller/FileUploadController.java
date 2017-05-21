package com.imagevideoapp.controller;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.ApplicationConstants;
import com.imagevideoapp.utils.ApplicationProperties;
import com.imagevideoapp.utils.GenUtilitis;

@Controller
public class FileUploadController {

	
private static final Logger logger = Logger.getLogger(FileUploadController.class);
	
	@Autowired
	UserService userService;
	
	private @Autowired ApplicationProperties applicationProperties;
	
	private static final int PROFILE_IMG_MAIN_WIDTH = 206;
	private static final int IMG_MAIN_HEIGHT = 206;
	private static final int IMG_HEADER_WIDTH = 60;
	private static final int IMG_HEADER_HEIGHT = 60;
	private static final int IMG_PEOPLE_WIDTH = 75;
	private static final int IMG_PEOPLE_HEIGHT = 75;
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam(value="file", required=false) MultipartFile file,ModelMap model
			,@ModelAttribute("uploadedImage") UploadedImage uploadedImage ) {
		logger.info(" uploadImage() Start------");
		String returnFilePath="";
		
		if(uploadedImage.getLinkType() == null){
			uploadedImage.setLinkType(1);
		}
		
		try {
			User user = GenUtilitis.getLoggedInUser();
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			
			String orgFileName = file.getOriginalFilename();
			String fileName = file.getOriginalFilename() ;
	/*		String fileNameHeader = String.valueOf(user.getUserId()) + "_HEADER" + fileExtension;
			String fileNamePeople = String.valueOf(user.getUserId()) + "_PEOPLE" + fileExtension;*/
			
			String imagePath = applicationProperties.getProperty(ApplicationConstants.IMAGE_FOLDER);
			imagePath = imagePath + user.getUserId() + applicationProperties.getProperty(ApplicationConstants.UPLOADED_IMAGE);
			
			File newFile = GenUtilitis.uploadFile(imagePath, orgFileName, file);
			
			if (newFile != null) {
				fileExtension = fileExtension.replaceFirst("\\.", "");
				BufferedImage originalImage = ImageIO.read(newFile);

				BufferedImage profileMain = GenUtilitis.getScaledInstance(originalImage, PROFILE_IMG_MAIN_WIDTH, IMG_MAIN_HEIGHT,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
				boolean isUploaded = ImageIO.write(originalImage, fileExtension, new File(imagePath + fileName));

				/*BufferedImage profileHeader = GenUtilitis.getScaledInstance(originalImage, IMG_HEADER_WIDTH, IMG_HEADER_HEIGHT,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
				ImageIO.write(profileHeader, fileExtension, new File(imagePath + fileNameHeader));

				BufferedImage peopleProfile = GenUtilitis.getScaledInstance(originalImage, IMG_PEOPLE_WIDTH, IMG_PEOPLE_HEIGHT,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
				ImageIO.write(peopleProfile, fileExtension, new File(imagePath + fileNamePeople));*/
				
				if (isUploaded) {
					String status = userService.insertFile(user, fileName, "imageUrl", "uploaded_image",uploadedImage);
					if (ApplicationConstants.SUCCESS.equals(status)) {
					String filepath=	setUserUploadedFilePath(user, fileName,"image");
					returnFilePath = filepath;
					model.addAttribute("imagepath", returnFilePath);
					}
				}
				
//				newFile.delete(); //deleting file after resizing
			}
			
			
			return "imageUpload/uploadSuccessFull";
			
		} catch (Exception e) {

			logger.info("error in file upload=="+e);
			return "redirect:user/uploadImage?error="+e.getMessage();
		}
		
	}

	
	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
	public String uploadVideo(@RequestParam("file") MultipartFile file,ModelMap model) {
		logger.info(" uploadVideo() Start------");
		String returnFilePath="";
		try {
			User user = GenUtilitis.getLoggedInUser();
			String orgFileName = file.getOriginalFilename();
			
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			
			String videopath = applicationProperties.getProperty(ApplicationConstants.IMAGE_FOLDER);
			videopath = videopath + user.getUserId() + applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO);
			
			File newFile = GenUtilitis.uploadFile(videopath, orgFileName, file);
			
			if (newFile != null) {

					String status = userService.insertFile(user, orgFileName, "video_url", "uploaded_video",null);
					if (ApplicationConstants.SUCCESS.equals(status)) {
					String filepath=	setUserUploadedFilePath(user, orgFileName,"video");
					returnFilePath = filepath;
					model.addAttribute("imagepath", returnFilePath);
					
				}
				
//				newFile.delete(); //deleting file after resizing
			}
			
			
			return "videoUpload/uploadVideoSuccessFull";
			
		} catch (Exception e) {

			logger.info("error in file upload=="+e);
			return "redirect:user/uploadImage?error="+e.getMessage();
		}
		
	}
	
	private String setUserUploadedFilePath(User user, String fileName ,String fileType) {

		String url =null;
		if(fileType.equals("image")){

			 url=applicationProperties.getProperty(ApplicationConstants.APP_PATH) + user.getUserId() + 
					applicationProperties.getProperty(ApplicationConstants.UPLOADED_IMAGE) + fileName;	
		}
		else if(fileType.equals("video")){
			url=applicationProperties.getProperty(ApplicationConstants.APP_PATH) + user.getUserId() + 
					applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO) + fileName;
		}
		return url;
	}
	
	@RequestMapping(value = "/editImageInfo", method = RequestMethod.GET)
	public String editImageInfo(@RequestParam("imageId" ) int editImageInfo,ModelMap model,@RequestParam(name="error",required=false) String error) {
	
		UploadedImage upload=userService.getImageByImgId(editImageInfo);
		model.addAttribute("imageInfo",upload);
		model.addAttribute("error",error);
		model.addAttribute("themecolor",applicationProperties.getProperty(ApplicationConstants.THEME_COLOR));
		return "imageUpload/editImageById";
	}
	
	@RequestMapping(value = "/editImageUpload", method = RequestMethod.POST)
	public String editImageUpload(@ModelAttribute("uploadedImage") UploadedImage uploadedImage,ModelMap model) {
	
		try {
			boolean upload=userService.editImageUpload(uploadedImage);
			model.addAttribute("isEdited",upload);
			model.addAttribute("themecolor",applicationProperties.getProperty(ApplicationConstants.THEME_COLOR));
			return "imageUpload/editImageById";
		} catch(EmptyResultDataAccessException e){
			logger.error(" editImageUpload() EmptyResultDataAccessException");
			return "redirect:editImageInfo?error="+e.getMessage();
		}catch(DataAccessException e){
			logger.error(" getRegistrationToken() DataAccessException");
			return "redirect:editImageInfo?error="+e.getMessage();
		}
	}
	@RequestMapping(value = "/deleteImages", method = RequestMethod.GET)
	@ResponseBody
	public String deleteImages(@RequestParam("imageId") String imageId) {
	
		try {
			boolean upload=userService.deleteImages(imageId);
			if(upload)
			return "success";
			else
				return "fail";
		} catch(EmptyResultDataAccessException e){
			logger.error(" deleteImages() EmptyResultDataAccessException");
			return "fail";
		}catch(DataAccessException e){
			logger.error(" deleteImages() DataAccessException");
			return "fail";
		}
	}
	
}
