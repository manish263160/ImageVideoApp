package com.imagevideoapp.controller;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.ApplicationProperties;
import com.imagevideoapp.utils.GenUtilitis;

@Controller
public class FileUploadController {
	private static final Logger logger = Logger.getLogger(FileUploadController.class);
	@Autowired
	UserService userService;
	@Autowired
	private ApplicationProperties applicationProperties;
	private static final int PROFILE_IMG_MAIN_WIDTH = 206;
	private static final int IMG_MAIN_HEIGHT = 206;
	private static final int IMG_HEADER_WIDTH = 60;
	private static final int IMG_HEADER_HEIGHT = 60;
	private static final int IMG_PEOPLE_WIDTH = 75;
	private static final int IMG_PEOPLE_HEIGHT = 75;

	@RequestMapping(value = { "/uploadImage" }, method = { RequestMethod.POST })
	public String uploadImage(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap model,
			@ModelAttribute("uploadedImage") UploadedImage uploadedImage) {
		logger.info(" uploadImage() Start------");
		String returnFilePath = "";
		if (uploadedImage.getLinkType() == null) {
			uploadedImage.setLinkType(Integer.valueOf(1));
		}

		try {
			User e = GenUtilitis.getLoggedInUser();
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
			Date date = new Date();
			String fileName = formatter.format(date) + file.getOriginalFilename();
			String imagePath = this.applicationProperties.getProperty("imageFolder");
			imagePath = imagePath + e.getUserId() + this.applicationProperties.getProperty("uploadImageFolder");
			File newFile = GenUtilitis.uploadFile(imagePath, fileName, file);
			if (newFile != null) {
				fileExtension = fileExtension.replaceFirst("\\.", "");
				BufferedImage originalImage = ImageIO.read(newFile);
				BufferedImage profileMain = GenUtilitis.getScaledInstance(originalImage, 206, 206,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
				boolean isUploaded = ImageIO.write(originalImage, fileExtension, new File(imagePath + fileName));
				if (isUploaded) {
					String status = this.userService.insertFile(e, fileName, "imageUrl", "uploaded_image",
							uploadedImage);
					if ("success".equals(status)) {
						String filepath = this.setUserUploadedFilePath(e, fileName, "image");
						model.addAttribute("imagepath", filepath);
					}
				}
			}

			return "imageUpload/uploadSuccessFull";
		} catch (Exception arg16) {
			logger.info("error in file upload==" + arg16);
			return "redirect:user/uploadImage?error=" + arg16.getMessage();
		}
	}

	@RequestMapping(value = { "/uploadVideo" }, method = { RequestMethod.POST })
	public String uploadVideo(@RequestParam("file") MultipartFile file, ModelMap model) {
		logger.info(" uploadVideo() Start------");
		String returnFilePath = "";

		try {
			User e = GenUtilitis.getLoggedInUser();
			String orgFileName = file.getOriginalFilename();
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			String videopath = this.applicationProperties.getProperty("imageFolder");
			videopath = videopath + e.getUserId() + this.applicationProperties.getProperty("uploadVideoFolder");
			File newFile = GenUtilitis.uploadFile(videopath, orgFileName, file);
			if (newFile != null) {
				String status = this.userService.insertFile(e, orgFileName, "video_url", "uploaded_video",
						(UploadedImage) null);
				if ("success".equals(status)) {
					String filepath = this.setUserUploadedFilePath(e, orgFileName, "video");
					model.addAttribute("imagepath", filepath);
				}
			}

			return "videoUpload/uploadVideoSuccessFull";
		} catch (Exception arg10) {
			logger.info("error in file upload==" + arg10);
			return "redirect:user/uploadImage?error=" + arg10.getMessage();
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
	public String editImageInfo(@RequestParam("imageId") int editImageInfo, ModelMap model,
			@RequestParam(name = "error", required = false) String error) {
		UploadedImage upload = this.userService.getImageByImgId(editImageInfo);
		model.addAttribute("imageInfo", upload);
		model.addAttribute("error", error);
		model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
		return "imageUpload/editImageById";
	}

	@RequestMapping(value = { "/editImageUpload" }, method = { RequestMethod.POST })
	public String editImageUpload(@ModelAttribute("uploadedImage") UploadedImage uploadedImage, ModelMap model) {
		try {
			boolean e = this.userService.editImageUpload(uploadedImage);
			model.addAttribute("isEdited", Boolean.valueOf(e));
			model.addAttribute("themecolor", this.applicationProperties.getProperty("themecolor"));
			return "imageUpload/editImageById";
		} catch (EmptyResultDataAccessException arg3) {
			logger.error(" editImageUpload() EmptyResultDataAccessException");
			return "redirect:editImageInfo?error=" + arg3.getMessage();
		} catch (DataAccessException arg4) {
			logger.error(" getRegistrationToken() DataAccessException");
			return "redirect:editImageInfo?error=" + arg4.getMessage();
		}
	}

	@RequestMapping(value = { "/deleteImages" }, method = { RequestMethod.GET })
	@ResponseBody
	public String deleteImages(@RequestParam("imageId") String imageId,
			@RequestParam(value = "imageUrl", required = false) String imageUrl) throws IOException {
		try {
			User user = GenUtilitis.getLoggedInUser();
			boolean filedete = false;
			String imagePath = this.applicationProperties.getProperty("imageFolder");
			imagePath = imagePath + user.getUserId() + this.applicationProperties.getProperty("uploadImageFolder");
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

			boolean isDeleted1 = this.userService.deleteImages(imageId);
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
			logger.info("searchDate=====" + searchDate);
			List allfileList = this.userService.getAllImages(e.getUserId(), searchDate);
			return allfileList;
		} catch (EmptyResultDataAccessException arg5) {
			logger.error(" deleteImages() EmptyResultDataAccessException");
			return null;
		} catch (DataAccessException arg6) {
			logger.error(" deleteImages() DataAccessException");
			return null;
		}
	}
}