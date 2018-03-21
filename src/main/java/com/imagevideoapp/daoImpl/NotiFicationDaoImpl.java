package com.imagevideoapp.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.imagevideoapp.dao.NotiFicationDao;
import com.imagevideoapp.models.NotificationDetails;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;

@Repository
public class NotiFicationDaoImpl  extends ImageVideoJdbcDaoSupport implements NotiFicationDao {
 
	private static final Logger logger = Logger.getLogger(NotiFicationDaoImpl.class);
	
	@Override
	public boolean sendNotification(NotificationDetails notificationDetails) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date curdate=new Date();
		String currentTime = sdf.format(curdate);
		logger.debug("::sendNotification()  start");
		try {
			String query = "INSERT INTO ImageVideoApp.notification_details (title, type, description, device_id, scheduling_type, schedule_time, created_on) VALUES (?,?,?,?,?,?,?) ;";
			getJdbcTemplate().update(query, notificationDetails.getTitle(),notificationDetails.getType(), notificationDetails.getDescription(),
					notificationDetails.getDeviceId(),notificationDetails.getSchedulingType(),notificationDetails.getScheduleTime(),currentTime);
			return true;
		} catch (EmptyResultDataAccessException e) {
			logger.error(" sendNotification() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" sendNotification() DataAccessException");
		}
		return false;
	}

	@Override
	public List<String> getAllDeviceId() {
		logger.debug("getAllDeviceId started");
		String query = "select * from mobile_device_id;";

		List<String> list = getJdbcTemplate().query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(2);
			}
		});
		return list;
	}

	@Override
	public boolean insertDevice(String deviceId) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date curdate=new Date();
		String currentTime = sdf.format(curdate);
		logger.debug("::insertDevice()  start");
		try {
			String query = "insert into mobile_device_id(device_id,created_on) values (?,?)";
			getJdbcTemplate().update(query, deviceId,currentTime);
			return true;
		} catch (EmptyResultDataAccessException e) {
			logger.error(" insertDevice() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" insertDevice() DataAccessException");
		}
		return false;
	}

	@Override
	public List<NotificationDetails> getAllScheduleTask(String currentTime) {
		logger.debug("::getAllScheduleTask()  start");
		StringBuilder query=new StringBuilder();
		List<NotificationDetails> alltask=null;
		try {
			query.append("select * from notification_details where schedule_time between '"+currentTime+":00' and  '"+currentTime+":59' ").append(" and scheduling_type = 2 order by schedule_time desc ;");
			logger.debug("query---"+query.toString());
			alltask = getJdbcTemplate().query(query.toString(), new BeanPropertyRowMapper<NotificationDetails>(NotificationDetails.class));
			return alltask; 
		} catch (EmptyResultDataAccessException e) {
			logger.error(" getRegistrationToken() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" getRegistrationToken() DataAccessException");
		}
		return alltask;
	}

	@Override
	public boolean checkVideoLink(String urllink, String from) {

		try {
			String query = null;
			if (from != null) {
				if (from.trim().equals("video")) {
					query = "select count(*) from uploaded_video where  video_link = ?";
				} else if (from.trim().equals("image")) {
					query = "select count(*) from uploaded_image where  image_link = ?";
				}
			}
			String str= getJdbcTemplate().queryForObject(query,new Object[] { urllink } , String.class);
			Integer converInt=Integer.parseInt(str);
			if(converInt > 0)
			return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(" checkVideoLink() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" checkVideoLink() DataAccessException");
		}
		return false;
	}

}
