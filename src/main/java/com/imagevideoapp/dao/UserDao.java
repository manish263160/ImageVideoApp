/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.imagevideoapp.dao;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import java.util.List;

public interface UserDao {
	User validateUser(String arg0, String arg1);

	User checkUserByEmail(String arg0);

	long insertUser(User arg0);

	void insertRegistraionToken(Long arg0, String arg1, String arg2);

	User getRegistrationTokenAndStatus(long arg0);

	void activateUser(long arg0);

	List<String> getUserRoles(Long arg0);

	void insertUserRole(long arg0);

	String insertFile(User arg0, String arg1, String arg2, String arg3, UploadedImage arg4);

	List<String> getAllImagesForUser(Long arg0, String arg1);

	List<UploadedImage> getAllImages(Long arg0, String arg1);

	UploadedImage getImageByImgId(int arg0);

	boolean editImageUpload(UploadedImage arg0);

	boolean deleteImages(String arg0);

	boolean resetPassword(User isemailExist, String newpassword);
}