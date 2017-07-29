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

	String insertFile(User arg0, String arg1, String arg2, String arg3, Object obj) throws GenericException;

	List<String> getAllImagesForUser(Long arg0, String arg1);

	<T> List<T> getAllImages(Long arg0,String tablename, String arg1);

	<T> T getImageByImgId(int imageId,String tableName, boolean token);

	boolean editImageUpload(Object arg0, String tableName);

	boolean deleteImages(String arg0, String tableName);

	User checkUserByEmailorID(String email);

	boolean resetPassword(User isemailExist, String newpassword);

	boolean insertPassGenToken(Long userId, String token);

	String getpassGenToken(long parseLong);
}