package com.imagevideoapp.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagevideoapp.dao.AdminDao;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.UploadedVideo;
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

	@Override
	public  Map<String, List<GetVideoByCatSerDto>> fetchAllVids(String token) {
		Map<String, List<GetVideoByCatSerDto>> map =new LinkedHashMap<String, List<GetVideoByCatSerDto>>();
		List<GetVideoByCatSerDto> list= adminDao.fetchAllVids();
		Set<String> catset=new HashSet<String>();
		
		list.forEach((ll) ->{
			if(token.equals("categoryWise")){
			catset.add(ll.getCategoryName());
			}else if(token.equals("seriesWise")){
				catset.add(ll.getSeriesName());	
			}
		});
		
		catset.forEach((set) ->{
			List<GetVideoByCatSerDto> uploadVid=new ArrayList<GetVideoByCatSerDto>();
			list.forEach((ll) ->{
				if(token.equals("categoryWise")){
				if(set.equals(ll.getCategoryName())){
					GetVideoByCatSerDto vid=new GetVideoByCatSerDto();
					vid.setCategoryName(ll.getCategoryName());;
					vid.setSeriesName(ll.getSeriesName());
					vid.setVideoLink(ll.getVideoLink());
					vid.setVideoThumbnail(ll.getVideoThumbnail());
					vid.setTimeLength(ll.getTimeLength());
					vid.setTitle(ll.getTitle());
					vid.setDescription(ll.getDescription());
					vid.setCreatedBy(ll.getCreatedBy());
					uploadVid.add(vid);
				}
			}else if(token.equals("seriesWise")){
				if(set.equals(ll.getSeriesName())){
					GetVideoByCatSerDto vid=new GetVideoByCatSerDto();
					vid.setCategoryName(ll.getCategoryName());;
					vid.setSeriesName(ll.getSeriesName());
					vid.setVideoLink(ll.getVideoLink());
					vid.setVideoThumbnail(ll.getVideoThumbnail());
					vid.setTimeLength(ll.getTimeLength());
					vid.setTitle(ll.getTitle());
					vid.setDescription(ll.getDescription());
					vid.setCreatedBy(ll.getCreatedBy());
					uploadVid.add(vid);
				}
			}
			});
			map.put(set, uploadVid);
		}); 
		
		
		
		return map;
	}

	@Override
	public List<GetVideoByCatSerDto> SearchVuds(String data) {
		return adminDao.SearchVuds(data);
	}

}