package com.imagevideoapp.cron;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.ApplicationProperties;
import com.imagevideoapp.utils.GenUtilitis;

@Component
public class Cronjob {

	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationProperties applicationProperties;
	@Autowired 
	private NotificationService notificationService;
	
	private static final Logger logger = Logger.getLogger(Cronjob.class);
	@Scheduled(cron="0 0 2 1/1 * ?")
//	@Scheduled(fixedDelay=20000)
	public void taskScheduler() throws IOException{
		logger.info("*****************************cron has started this time***************************");
		try {
			String cronStart="cronStart";
			String image="uploaded_image";
			String video="uploaded_video";
			int userId =3;
			String path=this.applicationProperties.getProperty("imageFolder") +userId+ this.applicationProperties.getProperty("uploadImageFolder");
			List<UploadedImage> getallImg=userService.getAllImages(null,image,"all"); 
			boolean deletmsg=userService.deleteImages(cronStart,image);
			boolean deletVidmsg=userService.deleteImages(cronStart,video);
			if(deletmsg){
				logger.info("deleted file list size===="+getallImg.size());
				getallImg.forEach((imgObj) -> {
					String imagepaths =path+"/"+ imgObj.getImageName();
//					String imagepaths=imgObj.getImageUrl();
					logger.debug("image path to delet=="+imagepaths);
					File file=new File(imagepaths);
					try {
						GenUtilitis.fileFolderdeteUtils(file);
					} catch (IOException e) {
						e.printStackTrace();
					}

				});
			}
// commenting as per requirement on 28 aug.
 	if (deletVidmsg) {
				List<UploadedVideo> getallVids = userService.getAllImages(null, video, "all");

				getallVids.forEach((vidObj) -> {
					String videopaths = vidObj.getVideoThumbnail();
					logger.debug("video path to delet==" + videopaths );
					File file = new File(videopaths);
					try {
						GenUtilitis.fileFolderdeteUtils(file);
					} catch (IOException e) {
						e.printStackTrace();
					}

				});
			}  
		} catch(EmptyResultDataAccessException e){
			logger.error(" taskScheduler() EmptyResultDataAccessException"+e.getMessage());
		}catch(DataAccessException e){
			logger.error(" taskScheduler() DataAccessException"+e.getMessage());
		}
	}
	
	
//	@Scheduled(cron="0 0 2 1/1 * ?")
//	@Scheduled(fixedRate=60*1000)
	public void pushNotificationCron() throws GenericException{
		logger.debug("*****************************pushNotificationCron started this time***************************");
		try {
			
			notificationService.pushNotificationCron();
			
		} catch(EmptyResultDataAccessException e){
			logger.error(" pushNotificationCron() EmptyResultDataAccessException"+e.getMessage());
		}catch(DataAccessException e){
			logger.error(" pushNotificationCron() DataAccessException"+e.getMessage());
		}
	}
}
