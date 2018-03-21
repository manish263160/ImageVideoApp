package com.imagevideoapp.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.AdminService;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.ApplicationConstants;
import com.imagevideoapp.utils.ApplicationProperties;
import com.imagevideoapp.utils.GenUtilitis;

@Controller
public class FileUploadController {
	private static final Logger logger = Logger.getLogger(FileUploadController.class);
	@Autowired
	UserService userService;
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value = { "/uploadImage" }, method = { RequestMethod.POST })
	public String uploadImage(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap model,
			@ModelAttribute("uploadedImage") UploadedImage uploadedImage) {
		logger.info(" uploadImage() Start------");
		if (uploadedImage.getLinkType() == null) {
			uploadedImage.setLinkType(Integer.valueOf(1));
		}

		try {
			
			List<String> categorArray=new ArrayList<String>();
			
			if(uploadedImage != null && uploadedImage.getCategoryId()!=null) {
				if(uploadedImage.getCategoryId().contains(",")) {
					categorArray= Arrays.asList(uploadedImage.getCategoryId().split("\\s*,\\s*"));
				}else {
					categorArray.add(uploadedImage.getCategoryId());
				}
			}
			
			User user = GenUtilitis.getLoggedInUser();
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
			Date date = new Date();
			String fileName = formatter.format(date) + file.getOriginalFilename();
			String imagePath = this.applicationProperties.getProperty("imageFolder");
			imagePath = imagePath + user.getUserId() + this.applicationProperties.getProperty("uploadImageFolder");
			File newFile = GenUtilitis.uploadFile(imagePath, fileName, file);
			if (newFile != null) {
				fileExtension = fileExtension.replaceFirst("\\.", "");
				BufferedImage originalImage = ImageIO.read(newFile);
				/*BufferedImage profileMain = GenUtilitis.getScaledInstance(originalImage, 206, 206,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);*/
				boolean isUploaded = ImageIO.write(originalImage, fileExtension, new File(imagePath + fileName));
				
				if (isUploaded) {
					String status =null;
					if(categorArray !=null && !categorArray.isEmpty())
					{
						for (String catid : categorArray) {
							uploadedImage.setCategoryId(catid);
							status = this.userService.insertFile(user, fileName, "imageUrl", "uploaded_image",
							uploadedImage);
						}
					}else {
						status = this.userService.insertFile(user, fileName, "imageUrl", "uploaded_image",
								uploadedImage);
					}
					if ("success".equals(status)) {
						String filepath = this.setUserUploadedFilePath(user, fileName, "image");
						model.addAttribute("imagepath", filepath);
					}
				}
			}

			return "imageUpload/uploadSuccessFull";
		} catch (Exception arg16) {
			logger.error("error in file upload==" + arg16);
			return "redirect:user/uploadImage?error=" + arg16.getMessage();
		}
	}

	@RequestMapping(value = { "/insertVideo" }, method = { RequestMethod.POST })
	public String uploadVideo(@RequestParam(value="file",required=false) MultipartFile file, ModelMap model,@ModelAttribute("UploadedVideo") UploadedVideo uploadedVideo) {
		logger.info(" uploadVideo() Start------");

		try {
			List<String> categorArray=new ArrayList<String>();
			
			if(uploadedVideo != null && uploadedVideo.getCategoryId()!=null) {
				if(uploadedVideo.getCategoryId().contains(",")) {
					categorArray= Arrays.asList(uploadedVideo.getCategoryId().split("\\s*,\\s*"));
				}else {
					categorArray.add(uploadedVideo.getCategoryId()); 
				}
			}
			
			User user = GenUtilitis.getLoggedInUser();
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			String orgFileName = file.getOriginalFilename();
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
			Date date = new Date();
			String fileName = formatter.format(date) + file.getOriginalFilename();
			
			String videopath = this.applicationProperties.getProperty("imageFolder");
			videopath = videopath + user.getUserId() + this.applicationProperties.getProperty("uploadVideoFolder");
			File newFile = GenUtilitis.uploadFile(videopath, fileName, file);
			if (newFile != null) {
				fileExtension = fileExtension.replaceFirst("\\.", "");
				BufferedImage originalImage = ImageIO.read(newFile);
			/*	BufferedImage profileMain = GenUtilitis.getScaledInstance(originalImage, 206, 206,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);*/
				boolean isUploaded = ImageIO.write(originalImage, fileExtension, new File(videopath + fileName));
				if (isUploaded) {
					String status =null;
					if(categorArray !=null && !categorArray.isEmpty())
					{
						for (String catid : categorArray) {
							uploadedVideo.setCategoryId(catid);
							status = this.userService.insertFile(user, fileName, "video_thumbnail", "uploaded_video",
									uploadedVideo);
						}
						
					}else {
						status = this.userService.insertFile(user, fileName, "video_thumbnail", "uploaded_video",
							uploadedVideo);
					}
					if ("success".equals(status)) {
						String filepath = this.setUserUploadedFilePath(user, orgFileName, "video");
						model.addAttribute("imagepath", filepath);
					}
				}
				
				
			}

			return "videoUpload/uploadVideoSuccessFull";
		} catch (Exception e) {
			logger.error("error in file upload==" + e);
			return "redirect:user/uploadVideo?error=" + e.getMessage();
		}
	}

	private String setUserUploadedFilePath(User user, String fileName, String fileType) {
		String url = null;
		if (fileType.equals("image")) {
			url = this.applicationProperties.getProperty("appPath") + user.getUserId()
					+ this.applicationProperties.getProperty("uploadImageFolder") + fileName;
		} else if (fileType.equals("video")) {
			url = this.applicationProperties.getProperty("appPath") + user.getUserId()
					+ this.applicationProperties.getProperty("uploadVideoFolder") + fileName;
		}

		return url;
	}

	@RequestMapping(value = { "/editImageInfo" }, method = { RequestMethod.GET })
	public String editImageInfo(@RequestParam("imageId") int editImageInfo, @RequestParam("tableName") String tableName,ModelMap model,
			@RequestParam(name = "error", required = false) String error) {
		model.addAttribute("error", error);
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		if(tableName.equals("uploaded_image")){
		UploadedImage upload = this.userService.getImageByImgId(editImageInfo,tableName, false);
		model.addAttribute("imageInfo", upload);
		}
		if(tableName.equals("uploaded_video")){
			UploadedVideo upload = this.userService.getImageByImgId(editImageInfo,tableName, false);
			model.addAttribute("imageInfo", upload);
			String fetchTable="series";
			List<CategrySeriesModels> serieslist=adminService.getAllCategorySeries(fetchTable , "editImageInfo");
			
			String fetchTablecate="categories";
			List<CategrySeriesModels> categorylist=adminService.getAllCategorySeries(fetchTablecate , "editImageInfo");
			
			model.addAttribute("categorylist", categorylist);
			model.addAttribute("serieslist", serieslist);
		}
		return tableName.equals("uploaded_image") ? "imageUpload/editImageById" : "videoUpload/editVideoPage";
	}

	@RequestMapping(value = { "/editImageUpload" }, method = { RequestMethod.POST })
	public String editImageUpload(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value="tableName", required=false) String tableName,
			@ModelAttribute("uploadedImage") UploadedImage uploadedImage,
			@ModelAttribute("uploadedVideo") UploadedVideo uploadedVideo,
			ModelMap model) throws IOException {
		if (uploadedImage.getLinkType() == null) {
			uploadedImage.setLinkType(Integer.valueOf(1));
		}
		User user = GenUtilitis.getLoggedInUser();
		try {
			boolean bool =false;
			boolean token= true;
			UploadedImage imageinfo=null;
			UploadedVideo vidInfo=null;
			String oldVideoName =null;
			if(tableName.equals("uploaded_image")){
			imageinfo=userService.getImageByImgId((int)uploadedImage.getId(),tableName,token);
			oldVideoName=imageinfo.getImageUrl();
			}
			if(tableName.equals("uploaded_video")){
				vidInfo =userService.getImageByImgId((int)uploadedVideo.getId(),tableName,token);
				oldVideoName=vidInfo.getVideoThumbnail();
			}
			
			uploadedVideo.setOldVideoName(oldVideoName);
			if(file != null ){
				if(file.getOriginalFilename().equals("")){
					if(tableName.equals("uploaded_image")){
					bool= userService.editImageUpload(uploadedImage ,tableName);
					}
					if(tableName.equals("uploaded_video")){
						bool= userService.editImageUpload(uploadedVideo,tableName);
					}
				}
				else
				{
					boolean filedelete = false;
					String imagePath = this.applicationProperties.getProperty("imageFolder");
					if(tableName.equals("uploaded_image")){
						imagePath = imagePath + user.getUserId() + this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_IMAGE);
						imagePath = imagePath + "/" + imageinfo.getImageUrl();
						
					}
						if(tableName.equals("uploaded_video")){
							imagePath = imagePath + user.getUserId() + this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO);
							imagePath = imagePath + "/" + vidInfo.getVideoThumbnail();
						}
					File isDeleted = new File(imagePath);
					
					filedelete = GenUtilitis.fileFolderdeteUtils(isDeleted);
//					if(filedelete){
					String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
							file.getOriginalFilename().length());
					SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
					Date date = new Date();
					String fileName = formatter.format(date) + file.getOriginalFilename();
					if(tableName.equals("uploaded_image")){
						uploadedImage.setImageUrl(fileName);
						imagePath = applicationProperties.getProperty("imageFolder") + user.getUserId() + this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_IMAGE);
						
					}
						if(tableName.equals("uploaded_video")){
							uploadedVideo.setVideoThumbnail(fileName);
							imagePath = applicationProperties.getProperty("imageFolder") + user.getUserId() + this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO);
						}
					File newFile = GenUtilitis.uploadFile(imagePath, fileName, file);
					if (newFile != null) {
						/*fileExtension = fileExtension.replaceFirst("\\.", "");
						BufferedImage originalImage = ImageIO.read(newFile);
						boolean isUploaded = ImageIO.write(originalImage, fileExtension, new File(imagePath));*/
//						if (isUploaded) {
						if(tableName.equals("uploaded_image")){
							bool= userService.editImageUpload(uploadedImage ,tableName);
							}
							if(tableName.equals("uploaded_video")){
								bool= userService.editImageUpload(uploadedVideo,tableName);
							}
							if (bool) {
								String filepath = this.setUserUploadedFilePath(user, fileName, "image");
								model.addAttribute("imagepath", filepath);
							}
//						}
//					}
					}
				}
			}
				if(tableName.equals("uploaded_image")){
					model.addAttribute("token", "image");
				}
				if(tableName.equals("uploaded_video")){
					model.addAttribute("token", "video");
				}
			model.addAttribute("isEdited", Boolean.valueOf(bool));
			model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
			return "imageUpload/editImageById";
		} catch (Exception e) {
			logger.error(" editImageUpload() Exception");
			return "redirect:editImageInfo?error=" + e.getMessage();
		}
	}

	@RequestMapping(value = { "/deleteImages" }, method = { RequestMethod.GET })
	@ResponseBody
	public String deleteImages(@RequestParam("imageId") String imageId,
			@RequestParam(value = "imageUrl", required = false) String imageUrl, @RequestParam(value = "tableName") String tableName) throws IOException {
		try {
			User user = GenUtilitis.getLoggedInUser();
			boolean filedete = false;
			String imagePath =this.applicationProperties.getProperty("imageFolder");;
			if(tableName.equals("uploaded_image")){
				
				imagePath = imagePath + user.getUserId() + this.applicationProperties.getProperty("uploadImageFolder");
			}
			if(tableName.equals("uploaded_video")){
				imagePath = imagePath + user.getUserId() + this.applicationProperties.getProperty("uploadVideoFolder");
			}
			if (imageId != null) {
				File isDeleted;
				if (imageId.equals("All")) {
					isDeleted = new File(imagePath);
					filedete = GenUtilitis.fileFolderdeteUtils(isDeleted);
				} else if (!imageId.equalsIgnoreCase("cronstart") && imageUrl != null
						&& !imageUrl.equals("undefined")) {
					imagePath = imagePath + "/" + imageUrl;
					isDeleted = new File(imagePath);
					filedete = GenUtilitis.fileFolderdeteUtils(isDeleted);
				}
			}

			boolean isDeleted1 = this.userService.deleteImages(imageId,tableName);
			return isDeleted1 ? "success" : "fail";
		} catch (EmptyResultDataAccessException arg6) {
			logger.error(" deleteImages() EmptyResultDataAccessException");
			return "fail";
		} catch (DataAccessException arg7) {
			logger.error(" deleteImages() DataAccessException");
			return "fail";
		}
	}

	@RequestMapping(value = { "/getImageBydate" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<UploadedImage> getImageBydate(@RequestParam("searchDate") String searchDate) throws IOException {
		try {
			User e = GenUtilitis.getLoggedInUser();
			boolean filedete = false;
			String imagePath = this.applicationProperties.getProperty("imageFolder");
			(new StringBuilder()).append(imagePath).append(e.getUserId())
					.append(this.applicationProperties.getProperty("uploadImageFolder")).toString();
			logger.debug("searchDate=====" + searchDate);
			List allfileList = this.userService.getAllImages(e.getUserId(),"uploaded_image", searchDate);
			return allfileList;
		} catch (EmptyResultDataAccessException arg5) {
			logger.error(" deleteImages() EmptyResultDataAccessException");
			return null;
		} catch (DataAccessException arg6) {
			logger.error(" deleteImages() DataAccessException");
			return null;
		}
	}
	
	@RequestMapping(value = { "/checkVideoLink" }, method = { RequestMethod.POST })
	@ResponseBody
	public boolean checkVideoLink(@RequestParam String urllink ,@RequestParam String from) throws GenericException {

		logger.debug("urllink is ===" + urllink +" from ="+from);
		boolean result = false;
		result = notificationService.checkVideoLink(urllink,from);
		return result;
	}
}