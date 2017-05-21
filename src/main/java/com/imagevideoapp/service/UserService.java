package com.imagevideoapp.service;

import java.util.List;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;

public interface UserService {
	
	public String isValid();
	
	User userLogin(String email,String password) throws GenericException;
	
	long insertUser(User user) throws GenericException;

	
	public void sendUserActivationMail(User user,String requestUrl) throws Exception;

	String activateUser(String token) throws Exception;

	List<String> getUserRoles(Long userId);

	String insertFile(User user, String value, String columnName,  String tableName, UploadedImage uploadedImage) throws GenericException;

	List<String> getAllImagesForUser(Long userId, String tablename);

	public List<UploadedImage> getAllImages(Long userId);

	public UploadedImage getImageByImgId(int editImageInfo);

	public boolean editImageUpload(UploadedImage uploadedImage);

	public boolean deleteImages(String imageId);

	
}
