package com.imagevideoapp.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.imagevideoapp.Enums.STATUS;
import com.imagevideoapp.dao.AdminDao;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.UploadedVideo;
import com.imagevideoapp.models.User;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;
import com.imagevideoapp.utils.GenUtilitis;

@Repository
public class AdminDaoImpl extends ImageVideoJdbcDaoSupport implements AdminDao {

	private static final Logger logger = Logger.getLogger(AdminDaoImpl.class);

	@Override
	public List<CategrySeriesModels> getAllCategorySeries(String fetchTable, Long userId, String fromController) {

		String query = "select * from " + fetchTable + " where user_id=? ";
				if(fetchTable.equals("categories") && fromController.equals("uploadVideo")) {
				query += " and cat_for="+STATUS.VIDEO.ID;
				}
				query += " order by id";
		List<CategrySeriesModels> list = getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<CategrySeriesModels>(CategrySeriesModels.class), userId);
		return list;
	}
	
	@Override
	public List<CategrySeriesModels> getAllCategoryForImages(User user, int catFor) {
		String query = "select * from categories where user_id=? and cat_for=? order by id";
		List<CategrySeriesModels> list = getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<CategrySeriesModels>(CategrySeriesModels.class), user.getUserId(),catFor);
		return list;
	}

	@Override
	public boolean insertCategory(String value, String name, Long userId,String catFor) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curdate1 = new Date();
		String currentTime1 = sdf1.format(curdate1);
		int update =0;
		if(value.equals("categories")) {
			String query = "INSERT INTO " + value + " (user_id,name,cat_for,created_on,modified_on) " + " VALUES (?,?,?,?,?);";
			update= getJdbcTemplate().update(query, userId, name,catFor, currentTime1, currentTime1);
		}else {
		String query = "INSERT INTO " + value + " (user_id,name,created_on,modified_on) " + " VALUES (?,?,?,?);";
		update= getJdbcTemplate().update(query, userId, name, currentTime1, currentTime1);
		}
		return update > 0 ? true : false;
	}

	@Override
	public boolean deleteCatSer(String value, int id, Long userId) {
		String sql = "delete from " + value + " where id=? and user_id= ? ";
		int rowcount = getJdbcTemplate().update(sql, id, userId);
		return rowcount > 0 ? true : false;
	}

	@Override
	public boolean editCategorySeries(String table, String name, int id) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curdate1 = new Date();
		String currentTime1 = sdf1.format(curdate1);
		User user = GenUtilitis.getLoggedInUser();
		String query = "update " + table + " set name=?,modified_on=? where user_id=? and id=? ";
		int update = getJdbcTemplate().update(query, name, currentTime1, user.getUserId(), id);
		return update > 0 ? true : false;
	}

	@Override
	public List<GetVideoByCatSerDto> fetchAllVids(String token, String start, String end) {
		List<GetVideoByCatSerDto> get = null;
		try {
			if (token.equals("categoryWise")) {
				String query = "select c.name as category_name,c.id as catId,s.name as series_name ,uv.* from uploaded_video uv left join categories c on uv.category_id = c.id left outer join series s on uv.series_id = s.id ";
				if (start != null && !start.equals("") && end != null && !end.equals("")) {
					query += " where uv.id >= " + start + " and uv.id <=" + end;
				}
				query += " order by c.id;";
				logger.info("fetchAllVids query "+query);
				get = getJdbcTemplate().query(query,
						new BeanPropertyRowMapper<GetVideoByCatSerDto>(GetVideoByCatSerDto.class));
			} else if (token.equals("seriesWise")) {
				String query = "select c.name as category_name,c.id as catId,s.name as series_name,s.id as serID ,uv.* from uploaded_video uv left join categories c on uv.category_id = c.id left outer join series s on uv.series_id = s.id ";
				if (start != null && !start.equals("") && end != null && !end.equals("")) {
					query += " where uv.id >= " + start + " and uv.id <=" + end;
				}
				query += " order by s.id desc;";
				logger.info("fetchAllVids query "+query);
				get = getJdbcTemplate().query(query,
						new BeanPropertyRowMapper<GetVideoByCatSerDto>(GetVideoByCatSerDto.class));
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(" validateUser() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" validateUser() DataAccessException");
		}
		return get;
	}

	@Override
	public List<GetVideoByCatSerDto> SearchVuds(String data) {
		List<GetVideoByCatSerDto> get = null;
		try {
			String query = "select c.name as category_name,s.name as series_name ,uv.* from  uploaded_video uv left join categories c on uv.category_id = c.id left outer join series s on uv.series_id = s.id where "
					+ "uv.title like ? or uv.description like ? or c.name like ? or s.name like ? "
					+ " order by uv.title,uv.description ,c.name , s.name;";
			get = getJdbcTemplate().query(query,
					new BeanPropertyRowMapper<GetVideoByCatSerDto>(GetVideoByCatSerDto.class), "%" + data + "%",
					"%" + data + "%", "%" + data + "%", "%" + data + "%");
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return get;
	}

	@Override
	public List<UploadedImage> fetchBunchOfImage(String categoryName, String start, String end) {

		List<UploadedImage> getData = null;
		try {
			StringBuffer query = new StringBuffer(
					"select * from ( select @s:=@s+1 serial_number,ui.*, cat.name as category_name , (select count(*) from uploaded_image where category_id = cat.id  ) total_image_count from uploaded_image ui ");
			query.append("left join categories cat on ui.category_id = cat.id , (SELECT @s:= 0) AS s ");
			query.append(" where cat.name=? and cat.cat_for=" + STATUS.IMAGE.ID + " and ui.user_id = 3) tbl");
			query.append(" where tbl.serial_number between ? and ? ;");
			logger.info("fetchBunchOfImage query ===" + query);	
			getData = getJdbcTemplate().query(query.toString(),
					new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class), categoryName, start, end);
			logger.info("size of the list data===" + getData.size());
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return getData;
	}

	@Override
	public List<UploadedVideo> fetchVideoByCatSeries(String categoryOrSeriesName, String start, String end,
			String queryFor) {
		List<UploadedVideo> getData = null;
		try {
			StringBuffer query = null;

			if (queryFor.equals("category")) { // this is for category case
				query = new StringBuffer(
						"select * from (select  @s:=@s+1 serial_number, uv.*, cat.name as category_name , (select count(*) from uploaded_video where category_id = cat.id  ) total_video_count from uploaded_video uv ");
				query.append("left join categories cat on uv.category_id = cat.id , (SELECT @s:= 0) AS s ");
				query.append("where cat.name=? and cat.cat_for=" + STATUS.VIDEO.ID + " and uv.user_id = 3) tbl ");
				query.append(" where tbl.serial_number between ? and ? ;");
				getData = getJdbcTemplate().query(query.toString(),
						new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class), categoryOrSeriesName, start,
						end);
			} else if (queryFor.equals("series")) {
				query = new StringBuffer(
						"select * from (select  @s:=@s+1 serial_number, uv.*, ser.name as series_name , (select count(*) from uploaded_video where series_id = ser.id  ) total_video_count from uploaded_video uv ");
				query.append("left join series ser on uv.series_id = ser.id , (SELECT @s:= 0) AS s ");
				query.append("where ser.name=? and uv.user_id = 3) tbl");
				query.append(" where tbl.serial_number between ? and ? ;");
				getData = getJdbcTemplate().query(query.toString(),
						new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class), categoryOrSeriesName, start,
						end);
			}
			logger.info("query===" + query.toString());
			logger.info("size of the list data===" + getData.size());
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return getData;
	}
	

}
