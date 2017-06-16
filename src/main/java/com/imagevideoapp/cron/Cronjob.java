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

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.utils.GenUtilitis;

@Component
public class Cronjob {

	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(Cronjob.class);
	@Scheduled(cron="0 0 2 1/1 * ?")
//	@Scheduled(fixedDelay=5000)
	public void taskScheduler() throws IOException{
		System.out.println("---print----");
		logger.info("*****************************cron has started this time***************************");
		try {
			String cronStart="cronStart";
			boolean deletmsg=userService.deleteImages(cronStart);
			if(deletmsg){
				List<UploadedImage> getallImg=userService.getAllImages(null, "all");
				
				getallImg.forEach((imgObj) -> {
					String imagepaths=imgObj.getImageUrl();
					logger.info("image path to delet=="+imagepaths);
					File isDeleted=new File(imagepaths);
					try {
						GenUtilitis.fileFolderdeteUtils(isDeleted);
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
}
