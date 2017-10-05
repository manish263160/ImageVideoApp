package com.imagevideoapp.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.imagevideoapp.Enums.NOTIFICATION_TYPE;
import com.imagevideoapp.dao.NotiFicationDao;
import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.NotificationDetails;
import com.imagevideoapp.service.NotificationService;
import com.imagevideoapp.utils.ApplicationProperties;

@Service
@PropertySource("classpath:application.properties")
public class NotificationServiceImpl implements NotificationService{

	private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);
	@Autowired
	NotiFicationDao notiFicationDao;
	@Autowired
	private Environment env;

	private String GOOGLE_SERVER_KEY; //="AIzaSyDPfaiaAL9qZnMyFuEXpP6lzm48_NRsV54";
	
	private @Autowired ApplicationProperties applicationProperties;
	
	static final String MESSAGE_KEY = "message";
	/**
	 * gcmRegId is the id which android app will give to server (one time)
	 */
	@Override
	public boolean pushNotificationToGCM(String gcmRegId, String message) {
		final int retries = 3;
		GOOGLE_SERVER_KEY=env.getProperty("gcm.apikey");
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message msg = new Message.Builder().addData("message",message).build();
		try {
		if(gcmRegId!=null) {
		Result result = sender.send(msg, gcmRegId, retries);
		/**
		* if you want to send to multiple then use below method
		* send(Message message, List<String> regIds, int retries)
		**/
		if (StringUtils.isEmpty(result.getErrorCodeName())) {
		System.out.println("GCM Notification is sent successfully" + result.toString());
		return true;
		}
		System.out.println("Error occurred while sending push notification :" + result.getErrorCodeName());
		}
		} catch (InvalidRequestException e) {
		System.out.println("Invalid Request");
		} catch (IOException e) {
		System.out.println("IO Exception");
		}
		return false;
	}
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public boolean sendNotification(NotificationDetails notificationDetails) throws GenericException {
		if(notificationDetails.getSchedulingType()== NOTIFICATION_TYPE.SENDNOW.ID){
			notificationDetails.setScheduleTime(null);
		
		}
		boolean isNotificationInserted= notiFicationDao.sendNotification(notificationDetails);
		List<NotificationDetails> listnotification=new ArrayList<NotificationDetails>();
		listnotification.add(notificationDetails);
		if(isNotificationInserted){
			if(notificationDetails.getSchedulingType()== NOTIFICATION_TYPE.SENDNOW.ID){
				return notificationPush(listnotification);
			
			}else if(notificationDetails.getSchedulingType()== NOTIFICATION_TYPE.SENDLATER.ID){
				return true;
			}
		}
		
		return false;
	}
	@Override
	public List<String> getAllDeviceId() {
		return notiFicationDao.getAllDeviceId();
	}
	@Override
	public boolean insertDevice(String deviceId) {
		return notiFicationDao.insertDevice(deviceId);
	}
	@Override
	public boolean pushNotificationCron(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		Date curdate=new Date();
		String currentTime = sdf.format(curdate);
		List<NotificationDetails> getAlltask= notiFicationDao.getAllScheduleTask(currentTime);
		boolean bool=notificationPush(getAlltask);
		
		return bool;
	} 

	boolean notificationPush(List<NotificationDetails> listnotificationDetails){
		
		List<String> getAllDevice = getAllDeviceId();
		logger.info("size of getAllDevice==="+getAllDevice.size());
		
		int partitionSize = 1000;
		
		List<List<String>> partitions = new LinkedList<List<String>>();
		
		for (int i = 0; i < getAllDevice.size(); i += partitionSize) {
		    partitions.add(getAllDevice.subList(i,
		            Math.min(i + partitionSize, getAllDevice.size())));
		}
		
		JSONObject obj = new JSONObject();
		MulticastResult result = null;

		GOOGLE_SERVER_KEY = env.getProperty("gcm.apikey");
		
		final int retries = 3;
		
		try {
			for (NotificationDetails list : listnotificationDetails) {
				obj.put("title", list.getTitle());
				obj.put("type", list.getType());
				obj.put("description", list.getDescription());
				String userMessage = obj.toString();
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
				for (int i = 0; i < partitions.size(); i ++) {
				result = sender.send(message,partitions.get(i), retries);
				logger.info("result=="+result.toString());
				}
				
			}
			
			return true;
		} catch (JSONException js) {
			logger.error("error="+js.getMessage());
		} catch (Exception e) {
			logger.error("pushStatus"+e.getMessage());
		}
	return false;
	}
	@Override
	public boolean checkVideoLink(String urllink , String from) {
		// TODO Auto-generated method stub
		return notiFicationDao.checkVideoLink(urllink , from);
	}
}
