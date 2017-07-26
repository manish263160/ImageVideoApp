package com.imagevideoapp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagevideoapp.dao.AdminDao;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.AdminService;
import com.imagevideoapp.utils.GenUtilitis;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;
	
	@Override
	public List<CategrySeriesModels> getAllCategorySeries(String fetchTable) {
		User user = GenUtilitis.getLoggedInUser();
		return adminDao.getAllCategorySeries(fetchTable ,user.getUserId());
	}

	@Override
	public boolean insertCategory(String value ,String name) {
		
		User user = GenUtilitis.getLoggedInUser();
		return adminDao.insertCategory(value ,name,user.getUserId());
	}

	@Override
	public boolean deleteCatSer(String value, int id) {
		User user = GenUtilitis.getLoggedInUser();
		return adminDao.deleteCatSer(value, id,user.getUserId());
	}

	@Override
	public boolean editCategorySeries(String table, String name, int id) {
		
		return adminDao.editCategorySeries(table, name, id);
	}

}
