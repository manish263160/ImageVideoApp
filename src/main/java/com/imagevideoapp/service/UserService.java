package com.imagevideoapp.service;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import java.util.List;

public interface UserService {
	String isValid();

	User userLogin(String arg0, String arg1) throws GenericException;

	long insertUser(User arg0) throws GenericException;

	void sendUserActivationMail(User arg0, String arg1) throws Exception;

	String activateUser(String arg0) throws Exception;

	List<String> getUserRoles(Long arg0);

	String insertFile(User arg0, String arg1, String arg2, String arg3, UploadedImage arg4) throws GenericException;

	List<String> getAllImagesForUser(Long arg0, String arg1);

	List<UploadedImage> getAllImages(Long arg0, String arg1);

	UploadedImage getImageByImgId(int arg0);

	boolean editImageUpload(UploadedImage arg0);

	boolean deleteImages(String arg0);
}