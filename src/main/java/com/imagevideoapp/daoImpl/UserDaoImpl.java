package com.imagevideoapp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.imagevideoapp.Enums.STATUS;
import com.imagevideoapp.dao.UserDao;
import com.imagevideoapp.models.UploadedImage;
import com.imagevideoapp.models.User;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;
import com.imagevideoapp.utils.GenUtilitis;

@Repository
public class UserDaoImpl extends ImageVideoJdbcDaoSupport implements UserDao {

	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	private static final String GET_USER = "select u.* from user u " + " where u.email=? and u.password=?";

	public User validateUser(final String emailId, final String password) {
		logger.debug("validateUser() email: " + emailId);
		User user = null;
		try {
			user = getJdbcTemplate().queryForObject(GET_USER, new ValidatedUserRowMapper(), emailId, password);
		} catch (EmptyResultDataAccessException e) {
			logger.error(" validateUser() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" validateUser() DataAccessException");
		}
		return user;
	}

	private class ValidatedUserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getLong("user_id"));
			user.setEmail(rs.getString("email"));
			user.setStatus(rs.getInt("status"));
			user.setUserImage(rs.getString("user_image"));
			user.setName(rs.getString("name"));
			user.setGender(rs.getString("gender"));
			user.setMobileNo(rs.getString("mobile_no"));
			return user;
		}

	}

	public User checkUserByEmail(final String email) {
		logger.info("::checkUserByEmail()");
		User user = null;
		final String query = "select * from user where email=?";
		try {
			user = getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper<User>(User.class), email);
		} catch (EmptyResultDataAccessException e) {
			logger.error(" checkUserByEmail() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" checkUserByEmail() DataAccessException");
		}
		return user;
	}

	public long insertUser(final User user) {

		logger.info("::insertUser()");
		final String query = "INSERT INTO user(email,mobile_no,password,name,status,created_on)VALUES(?,?,?,?,?,now());";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
				int i = 1;
				ps.setString(i++, user.getEmail());
				ps.setString(i++, user.getMobileNo());
				ps.setString(i++, user.getPassword());
				if (!StringUtils.isEmpty(user.getName())) {
					ps.setString(i++, user.getName());
				} else {
					ps.setString(i++, null);
				}

				ps.setInt(i++, STATUS.INACTIVE.ID);

				return ps;
			}
		}, keyHolder);
		user.setUserId(keyHolder.getKey().longValue());

		return keyHolder.getKey().longValue();
	}

	public void insertRegistraionToken(final Long userId, final String token, final String otp) {

		String query = "insert into user_reg_history(user_id,token,otp)values(?,?,?);";
		getJdbcTemplate().update(query, userId, token, otp);

	}

	public User getRegistrationTokenAndStatus(final long userId) {
		User user = null;
		try {
			String query = "select token,status,otp from user_reg_history urh inner join user u on urh.user_id=u.user_id where u.user_id=?;";
			user = getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper<User>(User.class), userId);
		} catch (EmptyResultDataAccessException e) {
			logger.error(" getRegistrationToken() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" getRegistrationToken() DataAccessException");
		}
		return user;
	}

	public void activateUser(final long userId) {
		String query = "update user set status=1 where user_id=?;";
		getJdbcTemplate().update(query, userId);

	}

	@Override
	public List<String> getUserRoles(Long userId) {

		String query = "select rm.type from user_role_relation urr left outer join role_m rm on urr.profile_id=rm.profile_id where urr.user_id= ?;";

		List<String> list = getJdbcTemplate().query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		}, userId);
		return list;
	}

	@Override
	public void insertUserRole(long userUid) {

		String query = "insert into user_role_relation(user_id,profile_id)values(?,?);";
		getJdbcTemplate().update(query, userUid, 3);

	}

	@Override
	public String insertFile(User user, String value, String columnName, String tableName,
			UploadedImage uploadedImage) {
		logger.info("updateUserDetails userId" + user.getUserId() + "value.." + value + " column name " + columnName
				+ " tableName:" + tableName);
		long userid = user.getUserId();
		// String idColumn =
		// tableName.equals("uploaded_image")||tableName.equals("uploaded_video")?"user_id":"id";
		String Sqlquery = "";
		int rowInsert = 0;
		if (tableName.equals("uploaded_image") && uploadedImage != null) {
			Sqlquery = "INSERT INTO " + tableName + " ( user_id , " + columnName
					+ " , created_on ,created_by ,image_link , image_description , link_type) VALUES (?,?,now(),?, ? ,? ,?)";
			rowInsert = getJdbcTemplate().update(Sqlquery, userid, value, user.getName(), uploadedImage.getImageLink(),
					uploadedImage.getImageDescription(), uploadedImage.getLinkType());
		} else if (tableName.equals("uploaded_video")) {
			Sqlquery = "INSERT INTO " + tableName + " ( user_id , " + columnName
					+ " , created_on ,created_by) VALUES (?,?,now(),?)";
			rowInsert = getJdbcTemplate().update(Sqlquery, userid, value, user.getName());
		}

		return rowInsert > 0 ? "success" : "fail";

	}

	@Override
	public List<String> getAllImagesForUser(Long userId, String tablename) {
		List<String> imglist = null;
		String Columnname = null;

		if (tablename.equals("uploaded_image")) {
			Columnname = "imageUrl";
		} else if (tablename.equals("uploaded_video")) {
			Columnname = "video_url";
		}
		try {
			// String query="select imageUrl from uploaded_image where
			// user_id=?;";

			StringBuilder query = new StringBuilder("select ");
			query.append(Columnname).append(" from ").append(tablename).append(" where user_id=?; ");

			imglist = getJdbcTemplate().query(query.toString(), new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1);
				}
			}, userId);
		} catch (EmptyResultDataAccessException e) {
			logger.error(" getAllImagesForUser() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" getAllImagesForUser() DataAccessException");
		}
		return imglist;

	}

	@Override
	public List<UploadedImage> getAllImages(Long userId) {
		List<UploadedImage> allImages = null;
		try {

			String query = "select * from uploaded_image order by id desc;";
			allImages = getJdbcTemplate().query(query, new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class));

		} catch (EmptyResultDataAccessException e) {
			logger.error(" getRegistrationToken() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" getRegistrationToken() DataAccessException");
		}
		return allImages;
	}

	@Override
	public UploadedImage getImageByImgId(int editImageInfo) {

		UploadedImage umg = null;
		try {
			String query = "select * from uploaded_image where id=?;";
			umg = getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper<UploadedImage>(UploadedImage.class),
					editImageInfo);
		} catch (EmptyResultDataAccessException e) {
			logger.error(" getRegistrationToken() EmptyResultDataAccessException");
		} catch (DataAccessException e) {
			logger.error(" getRegistrationToken() DataAccessException");
		}

		return umg;
	}

	@Override
	public boolean editImageUpload(UploadedImage uploadedImage) {
		boolean returndata = false;
		logger.info("-------------------editImageUpload() start");
		final StringBuilder sql = new StringBuilder();
		int rowcount = 0;

		sql.append(
				"update uploaded_image set image_link = ?, image_description = ?,link_type = ? , modified_on = now() where id= ?");
		rowcount = getJdbcTemplate().update(sql.toString(), uploadedImage.getImageLink(),
				uploadedImage.getImageDescription(), uploadedImage.getLinkType(), uploadedImage.getId());

		if (rowcount > 0) {
			returndata = true;
		}

		return returndata;
	}

	@Override
	public boolean deleteImages(String imageId) {
		boolean returndata = false;
		User user = GenUtilitis.getLoggedInUser();
		logger.info("------imageIg---" + imageId);
		final StringBuilder sql = new StringBuilder();
		int rowcount = 0;
		if (imageId != null && imageId.equals("All")) {
			sql.append("delete from uploaded_image where user_id= ? ");
			rowcount = getJdbcTemplate().update(sql.toString(), user.getUserId());

		} else if (imageId != null && imageId.equalsIgnoreCase("cronstart")) {
			sql.append("delete from  uploaded_image where  created_on < (DATE_SUB(NOW(), INTERVAL 7 DAY));");
			rowcount = getJdbcTemplate().update(sql.toString());

		} else {

			sql.append("delete from  uploaded_image where user_id= ? and id =?");
			rowcount = getJdbcTemplate().update(sql.toString(), user.getUserId(), imageId);

		}

		if (rowcount > 0) {
			returndata = true;
		}

		return returndata;
	}

}
