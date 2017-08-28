package com.imagevideoapp.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.imagevideoapp.dao.AdminDao;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.User;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;
import com.imagevideoapp.utils.GenUtilitis;

@Repository
public class AdminDaoImpl extends ImageVideoJdbcDaoSupport implements AdminDao{

	private static final Logger logger = Logger.getLogger(AdminDaoImpl.class);
	@Override
	public List<CategrySeriesModels> getAllCategorySeries(String fetchTable ,Long userId) {

		String query = "select * from "+fetchTable+" where user_id=? order by id";

		List<CategrySeriesModels> list = getJdbcTemplate().query(query, new BeanPropertyRowMapper<CategrySeriesModels>(CategrySeriesModels.class),userId);
		return list;
	}
	@Override
	public boolean insertCategory(String value ,String name,Long userId) {
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date curdate1=new Date();
		String currentTime1 = sdf1.format(curdate1);
		
		String query = "INSERT INTO "+value+" (user_id,name,created_on,modified_on) "
				+ " VALUES (?,?,?,?);";
		int update=getJdbcTemplate().update(query, userId, name,currentTime1,currentTime1);
		return update > 0 ? true : false;
	}
	@Override
	public boolean deleteCatSer(String value, int id , Long userId) {
		String sql="delete from "+value+" where id=? and user_id= ? ";
		int rowcount = getJdbcTemplate().update(sql,id, userId);
		return rowcount > 0 ? true : false;
	}
	@Override
	public boolean editCategorySeries(String table, String name, int id) {
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date curdate1=new Date();
		String currentTime1 = sdf1.format(curdate1);
		User user = GenUtilitis.getLoggedInUser();
		String query = "update "+table+" set name=?,modified_on=? where user_id=? and id=? ";
		int update=getJdbcTemplate().update(query, name, currentTime1,user.getUserId(),id);
		return update > 0 ? true : false;
	}
	@Override
	public List<GetVideoByCatSerDto> fetchAllVids() {
		List<GetVideoByCatSerDto> get=null;
		try {
			String query="select c.name as category_name,c.id as catId,s.name as series_name ,uv.* from uploaded_video uv left join categories c on uv.category_id = c.id left outer join series s on uv.series_id = s.id order by c.id;";
			get = getJdbcTemplate().query(query, new BeanPropertyRowMapper<GetVideoByCatSerDto>(GetVideoByCatSerDto.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error(" validateUser() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" validateUser() DataAccessException");
		}
		return get;
	}
	@Override
	public List<GetVideoByCatSerDto> SearchVuds(String data) {
		List<GetVideoByCatSerDto> get=null;
		try {
			String query="select c.name as category_name,s.name as series_name ,uv.* from  uploaded_video uv left join categories c on uv.category_id = c.id left outer join series s on uv.series_id = s.id where "
						+ "uv.title like ? or uv.description like ? or c.name like ? or s.name like ? "
						+ " order by uv.title,uv.description ,c.name , s.name;";
			get = getJdbcTemplate().query(query, new BeanPropertyRowMapper<GetVideoByCatSerDto>(GetVideoByCatSerDto.class),"%"+data+"%","%"+data+"%","%"+data+"%","%"+data+"%");
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return get;
	}

	

	}

