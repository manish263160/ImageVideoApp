package com.imagevideoapp.dao;

import java.util.List;

import com.imagevideoapp.models.NotificationDetails;

public interface NotiFicationDao {

	boolean sendNotification(NotificationDetails notificationDetails);

	List<String> getAllDeviceId();

	boolean insertDevice(String deviceId);

	List<NotificationDetails> getAllScheduleTask(String currentTime);

}
