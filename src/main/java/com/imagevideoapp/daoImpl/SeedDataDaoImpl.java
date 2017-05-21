package com.imagevideoapp.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.imagevideoapp.dao.SeedDataDao;
import com.imagevideoapp.support.ImageVideoJdbcDaoSupport;

@Repository
public class SeedDataDaoImpl extends ImageVideoJdbcDaoSupport  implements SeedDataDao {

	private static final Logger logger = Logger.getLogger(SeedDataDaoImpl.class);
	
}
