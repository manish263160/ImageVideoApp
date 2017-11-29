package com.imagevideoapp.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.imagevideoapp.Enums.STATUS;
import com.imagevideoapp.dao.AdminDao;
import com.imagevideoapp.models.ApplicationPropertyKeyVal;
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
	public List<GetVideoByCatSerDto> searchVideo(String data) {
		List<GetVideoByCatSerDto> get = null;
		try {
			String query = "select c.name as category_name,s.name as series_name ,uv.* from  uploaded_video uv left join categories c on uv.category_id = c.id left outer join series s on uv.series_id = s.id where "
					+ " uv.title like ? or uv.description like ? or c.name like ? or s.name like ? "
					+ " order by uv.created_on desc;";
			get = getJdbcTemplate().query(query,
					new BeanPropertyRowMapper<GetVideoByCatSerDto>(GetVideoByCatSerDto.class), "%" + data + "%",
					"%" + data + "%", "%" + data + "%", "%" + data + "%");
			logger.info("SearchVuds query==="+query);
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
			StringBuffer query = new StringBuffer();
			if(categoryName != null && !categoryName.equals("all")) {
			query.append("select * from ( select @s:=@s+1 serial_number,ui.*, cat.name as category_name , (select count(*) from uploaded_image where category_id = cat.id  ) total_image_count from uploaded_image ui ");
			query.append("left join categories cat on ui.category_id = cat.id , (SELECT @s:= 0) AS s ");
			query.append(" where cat.name=? and cat.cat_for=" + STATUS.IMAGE.ID + " and ui.user_id = 3) tbl");
			}
			else {
				query.append("select * from ( select @s:=@s+1 serial_number,ui.*, (select count(*) from uploaded_image ) total_image_count from uploaded_image ui ");
			     query.append(" , (SELECT @s:= 0) AS s ");
				query.append(" where  ui.user_id = 3) tbl");
			}
			query.append(" where tbl.serial_number between ? and ? ;");
			logger.info("fetchBunchOfImage query ===" + query);	
			if(categoryName != null && !categoryName.equals("all")) {
			getData = getJdbcTemplate().query(query.toString(),
					new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class), categoryName, start, end);
			}
			else {
				getData = getJdbcTemplate().query(query.toString(),
						new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class), start, end);
			}
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
				if(categoryOrSeriesName != null && !categoryOrSeriesName.equals("")) {
				query.append("where cat.name=? and cat.cat_for=" + STATUS.VIDEO.ID + " and uv.user_id = 3) tbl ");
				query.append(" where tbl.serial_number between ? and ? ;");
				getData = getJdbcTemplate().query(query.toString(),
						new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class), categoryOrSeriesName, start,
						end);
				}else if(categoryOrSeriesName == null) {
					query.append("where cat.cat_for=" + STATUS.VIDEO.ID + " and uv.user_id = 3) tbl ");
					query.append(" where tbl.serial_number between ? and ? ;");
					getData = getJdbcTemplate().query(query.toString(),
							new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class), start,
							end);
				}
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

	@Override
	public List<UploadedVideo> getAllVidsForUI(String catId ) {
		String query =null;
//		if(tablename.equals("video")) {
			String tablename = "uploaded_video";
/*		}else {
			tablename = "uploaded_image";
		}*/
		List<UploadedVideo> list =null;
		try {
			if(catId != null && !catId.trim().equals("")) {
				query ="select ct.name as category_name ,uv.* from "+tablename+" uv left join categories ct on uv.category_id =ct.id where ct.id=? group by uv.video_link order by uv.created_on desc limit 500";
				list = getJdbcTemplate().query(query.toString(),
						new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class ) , catId.trim());
			}else {
			query ="select ct.name as category_name ,uv.* from "+tablename+" uv left join categories ct on uv.category_id =ct.id group by uv.video_link order by uv.created_on desc limit 500";
			list = getJdbcTemplate().query(query.toString(),
					new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class));
			}
			logger.info("getAllVidsForUI=====query====="+query);
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return list;
	}

	@Override
	public List<ApplicationPropertyKeyVal> getAllProperties() {
		
		String query ="select ap.name,ap.value from application_properties ap";
		List<ApplicationPropertyKeyVal> allPropertiesFromDb = getJdbcTemplate().query(query, new RowMapper<ApplicationPropertyKeyVal>() {
			public ApplicationPropertyKeyVal mapRow(ResultSet rs, int rowNum) throws SQLException {
				ApplicationPropertyKeyVal applicationProperty = new ApplicationPropertyKeyVal();
				applicationProperty.setKey(rs.getString("name"));
				applicationProperty.setValue(rs.getString("value"));
				return applicationProperty;
			}
		});
		
		return allPropertiesFromDb;
	}

	@Override
	public List<UploadedImage> searchImage(String text) {
		List<UploadedImage> get = null;
		try {
			String query = "SELECT ui.*,c.name as categoryName FROM uploaded_image ui left join categories c on ui.category_id =c.id where ui.image_description like ? or c.name like ?;";
			get = getJdbcTemplate().query(query,
					new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class), "%" + text + "%",
					"%" + text + "%");
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return get;
	}

	@Override
	public List<UploadedImage> getAllImageForUI(String categoryOrSeriesName) {
		String query =null;
		
			String tablename = "uploaded_image";
		List<UploadedImage> list =null;
		try {
			if(categoryOrSeriesName != null && !categoryOrSeriesName.trim().equals("")) {
				query ="select ct.name as category_name ,uv.* from "+tablename+" uv left join categories ct on uv.category_id =ct.id where ct.name=? order by uv.created_on desc";
				list = getJdbcTemplate().query(query.toString(),
						new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class ) , categoryOrSeriesName.trim());
			}else {
			query ="select ct.name as category_name ,uv.* from "+tablename+" uv left join categories ct on uv.category_id =ct.id order by uv.created_on desc";
			list = getJdbcTemplate().query(query.toString(),
					new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class));
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return list;
	}

	@Override
	public List<UploadedVideo> getAllWebSeriesVideo() {
		List<UploadedVideo> list =null;
		try {
			
			String query ="select s.name as series_name ,uv.* from uploaded_video uv left join series s on uv.series_id =s.id  order by uv.created_on desc;";
			list = getJdbcTemplate().query(query.toString(),
					new BeanPropertyRowMapper<UploadedVideo>(UploadedVideo.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error(" EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" DataAccessException");
		}
		return list;
	}

	@Override
	public List<CategrySeriesModels> getRestAllCategory(User user, int catFor, String table) {
		String query = "select  c.* from "+table+" u left join categories c  on u.category_id=c.id  where c.user_id=? and c.cat_for=? group by c.id order by c.id;" ;
		List<CategrySeriesModels> list = getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<CategrySeriesModels>(CategrySeriesModels.class), user.getUserId(),catFor);
		return list;
	}
	
	
	

}
