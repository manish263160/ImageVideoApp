package com.imagevideoapp.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
		boolean isNotificationInserted= notiFicationDao.sendNotification(notificationDetails);
		if(isNotificationInserted){
			if(notificationDetails.getSchedulingType()== NOTIFICATION_TYPE.SENDNOW.ID){
				List<String> getAllDevice = getAllDeviceId();
				JSONObject obj = new JSONObject();
				MulticastResult result = null;

				GOOGLE_SERVER_KEY = env.getProperty("gcm.apikey");
				
				final int retries = 3;
				try {
					obj.put("title", notificationDetails.getTitle());
					obj.put("description", notificationDetails.getDescription());
					String userMessage = obj.toString();
					Sender sender = new Sender(GOOGLE_SERVER_KEY);
					Message message = new Message.Builder().timeToLive(30)
							.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
					result = sender.send(message, getAllDevice, retries);
					logger.info("result=="+result.toString());
					return true;
				} catch (JSONException js) {
					logger.error("error="+js.getMessage());
				} catch (Exception e) {
					logger.error("pushStatus"+e.getMessage());
				}
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

	
}
