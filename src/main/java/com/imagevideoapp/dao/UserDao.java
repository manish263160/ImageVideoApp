/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.imagevideoapp.dao;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import java.util.List;

public interface UserDao {
	User validateUser(String arg0, String arg1);

	User checkUserByEmailorID(String emailorID);

	long insertUser(User arg0);

	void insertRegistraionToken(Long arg0, String arg1, String arg2);

	User getRegistrationTokenAndStatus(long arg0);

	void activateUser(long arg0);

	List<String> getUserRoles(Long arg0);

	void insertUserRole(long arg0);

	String insertFile(User arg0, String arg1, String arg2, String arg3, Object arg4);

	List<String> getAllImagesForUser(Long arg0, String arg1);

	<T> List<T> getAllImages(Long arg0,String tablename, String arg1);

	<T> T getImageByImgId(int arg0, String tableName);

	boolean editImageUpload(Object arg0,String tableName);

	boolean deleteImages(String arg0, String tableName);

	boolean resetPassword(User isemailExist, String newpassword);

	boolean insertPassGenToken(Long userId, String token);

	String getpassGenToken(long userId);
}