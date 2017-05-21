package com.imagevideoapp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.imagevideoapp.Enums.STATUS;
import com.imagevideoapp.dao.RestorentDao;
import com.imagevideoapp.models.User;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;

@Lazy
@Component
public class RestorentDaoImpl extends ImageVideoJdbcDaoSupport implements RestorentDao  {

	private static final Logger logger = Logger.getLogger(RestorentDaoImpl.class);

	public boolean insertRestorent(final User user,final long getuserId){
        
		logger.info( "::insertRestorent()");
		final String query="INSERT INTO restorent(user_id,restorent_email,restorent_phone,shop_name,shop_address,shop_type,status,created_on,created_by) VALUES  "
								+ " (?,?,?,?,?,?,?,now(),?) ";

//		try {
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection con)throws SQLException {
					PreparedStatement ps=con.prepareStatement(query);
					int i = 1;
					ps.setLong(i++, getuserId);
					ps.setString(i++, user.getEmail());
					ps.setString(i++, user.getMobileNo());
					ps.setString(i++, user.getShopName());
					ps.setString(i++, user.getShopAddress());
					ps.setInt(i++, user.getShopType());
					ps.setInt(i++, STATUS.INACTIVE.ID);
					ps.setString(i++, user.getShopName());
					
					return ps;
				}
			});
			
//			return true;
		/*} catch (DataAccessException e) {
			logger.println(IMessage.ERROR, new StringBuilder(CLASS_NAME).append("::insertRestorent() has exception.... "+e.getMessage()));
		}*/
		logger.info( "::insertRestorent() ends  employmentId.... ");
		return true;
	}



}
