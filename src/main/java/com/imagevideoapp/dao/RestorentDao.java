package com.imagevideoapp.dao;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.User;

public interface RestorentDao {

	
	boolean insertRestorent(User user, long getuserId);


}
