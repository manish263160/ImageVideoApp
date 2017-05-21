package com.imagevideoapp.dao;

import java.util.List;

import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;

public interface UserDao {

	User validateUser(String email, String password);

	User checkUserByEmail(String email);

	long insertUser(User user);

	void insertRegistraionToken(Long userId, String plainText, String string);

	User getRegistrationTokenAndStatus(long userId);

	void activateUser(long userId);

	List<String> getUserRoles(Long userId);

	void insertUserRole(long userUid);

	String insertFile(User user, String value, String columnName,
			String tableName, UploadedImage uploadedImage);

	List<String> getAllImagesForUser(Long userId, String tablename);

	List<UploadedImage> getAllImages(Long userId);

	UploadedImage getImageByImgId(int editImageInfo);

	boolean editImageUpload(UploadedImage uploadedImage);

	boolean deleteImages(String imageId);

}
