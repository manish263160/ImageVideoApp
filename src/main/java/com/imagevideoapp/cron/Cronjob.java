package com.imagevideoapp.cron;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imagevideoapp.service.UserService;

@Component
public class Cronjob {

	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(Cronjob.class);
	@Scheduled(cron="0 0 2 1/1 * ?")
//	@Scheduled(fixedDelay=5000)
	public void taskScheduler() {
		System.out.println("---print----");
		logger.info("*****************************cron has started this time***************************");
		try {
			String cronStart="cronStart";
			boolean upload=userService.deleteImages(cronStart);
		
		} catch(EmptyResultDataAccessException e){
			logger.error(" taskScheduler() EmptyResultDataAccessException"+e.getMessage());
		}catch(DataAccessException e){
			logger.error(" taskScheduler() DataAccessException"+e.getMessage());
		}
	}
}
