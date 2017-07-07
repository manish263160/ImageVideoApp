package com.imagevideoapp.service;

import java.util.List;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.NotificationDetails;

public interface NotificationService {

	public boolean pushNotificationToGCM(String gcmRegId,String message);

	public boolean sendNotification(NotificationDetails notificationDetails) throws GenericException;
	
	public List<String> getAllDeviceId();

	public boolean insertDevice(String deviceId);

	public boolean pushNotificationCron();
}
