package com.imagevideoapp.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagevideoapp.dao.AdminDao;
import com.imagevideoapp.daoImpl.AdminDaoImpl;
import com.imagevideoapp.models.CategrySeriesModels;
import com.imagevideoapp.models.FetchVideoJson;
import com.imagevideoapp.models.GetVideoByCatSerDto;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.AdminService;
import com.imagevideoapp.utils.ApplicationConstants;
import com.imagevideoapp.utils.ApplicationProperties;
import com.imagevideoapp.utils.GenUtilitis;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);
	@Autowired
	AdminDao adminDao;
	private @Autowired ApplicationProperties applicationProperties;

	@Override
	public List<CategrySeriesModels> getAllCategorySeries(String fetchTable) {
		User user = GenUtilitis.getLoggedInUser();
		return adminDao.getAllCategorySeries(fetchTable, user.getUserId());
	}

	@Override
	public boolean insertCategory(String value, String name) {

		User user = GenUtilitis.getLoggedInUser();
		return adminDao.insertCategory(value, name, user.getUserId());
	}

	@Override
	public boolean deleteCatSer(String value, int id) {
		User user = GenUtilitis.getLoggedInUser();
		return adminDao.deleteCatSer(value, id, user.getUserId());
	}

	@Override
	public boolean editCategorySeries(String table, String name, int id) {

		return adminDao.editCategorySeries(table, name, id);
	}

	@Override
	public List<FetchVideoJson> fetchAllVidsWeb(String token) {

		List<FetchVideoJson> finallist = new ArrayList<FetchVideoJson>();

		List<GetVideoByCatSerDto> list = adminDao.fetchAllVids(token, null, null);
		Set<String> catset = new LinkedHashSet<String>();

		list.forEach((ll) -> {
			if (token.equals("categoryWise")) {
				catset.add(ll.getCategoryName());
			} else if (token.equals("seriesWise")) {
				catset.add(ll.getSeriesName());
			}
		});

		catset.forEach((set) -> {
			if (set != null) {
				FetchVideoJson uploadVid = new FetchVideoJson();
				List<GetVideoByCatSerDto> getvidsobj = new ArrayList<GetVideoByCatSerDto>();
				if (token.equals("categoryWise")) {
					uploadVid.setCategoryName(set);
				}
				if (token.equals("seriesWise")) {
					uploadVid.setSeriesName(set);
				}
				list.forEach((ll) -> {
					if (ll != null) {
						String url = "";
						if (token.equals("categoryWise")) {
							if (set.equals(ll.getCategoryName())) {
								GetVideoByCatSerDto vid = new GetVideoByCatSerDto();
								vid.setCategoryName(ll.getCategoryName());
								;
								vid.setSeriesName(ll.getSeriesName());
								vid.setVideoLink(ll.getVideoLink());
								url = this.applicationProperties.getProperty("appPath") + ll.getUserId()
										+ this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO)
										+ ll.getVideoThumbnail();
								vid.setVideoThumbnail(url);
								vid.setId(ll.getId());
								vid.setVideoName(ll.getVideoThumbnail());
								vid.setTimeLength(ll.getTimeLength());
								vid.setTitle(ll.getTitle());
								vid.setDescription(ll.getDescription());
								vid.setCreatedBy(ll.getCreatedBy());
								vid.setCreatedOn(ll.getCreatedOn());
								getvidsobj.add(vid);
							}
						} else if (token.equals("seriesWise")) {
							if (set.equals(ll.getSeriesName())) {
								GetVideoByCatSerDto vid = new GetVideoByCatSerDto();
								vid.setCategoryName(ll.getCategoryName());
								;
								vid.setSeriesName(ll.getSeriesName());
								vid.setVideoLink(ll.getVideoLink());
								url = this.applicationProperties.getProperty("appPath") + ll.getUserId()
										+ this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO)
										+ ll.getVideoThumbnail();
								vid.setVideoThumbnail(url);
								vid.setId(ll.getId());
								vid.setVideoName(ll.getVideoThumbnail());
								vid.setTimeLength(ll.getTimeLength());
								vid.setTitle(ll.getTitle());
								vid.setDescription(ll.getDescription());
								vid.setCreatedBy(ll.getCreatedBy());
								vid.setCreatedOn(ll.getCreatedOn());
								getvidsobj.add(vid);
							}
						}
					}
				});

				Collections.sort(getvidsobj, new Comparator<GetVideoByCatSerDto>() {

					@Override
					public int compare(GetVideoByCatSerDto o1, GetVideoByCatSerDto o2) {
						if (o1.getCreatedOn() != null && o2.getCreatedOn() != null)
							return o2.getCreatedOn().compareTo(o1.getCreatedOn());
						else
							return 0;

					}
				});
				/*for (GetVideoByCatSerDto st : getvidsobj) {
					logger.info("dates======" + st.getCategoryName() + "   ----" + st.getCreatedOn());

				}*/
				if (token.equals("categoryWise")) {
					uploadVid.setCategoryList(getvidsobj);
				}
				if (token.equals("seriesWise")) {

					uploadVid.setSeriesList(getvidsobj);
				}
				finallist.add(uploadVid);

			}
		});

		return finallist;

	}

	@Override
	public List<FetchVideoJson> fetchAllVids(String token, String start, String end) {
		List<FetchVideoJson> finallist = new ArrayList<FetchVideoJson>();

		List<GetVideoByCatSerDto> list = adminDao.fetchAllVids(token, start, end);
		Set<String> catset = new LinkedHashSet<String>();

		list.forEach((ll) -> {
			if (token.equals("categoryWise")) {
				catset.add(ll.getCategoryName());
			} else if (token.equals("seriesWise")) {
				catset.add(ll.getSeriesName());
			}
		});

		catset.forEach((set) -> {
			if (set != null) {
				FetchVideoJson uploadVid = new FetchVideoJson();
				List<GetVideoByCatSerDto> getvidsobj = new ArrayList<GetVideoByCatSerDto>();
				if (token.equals("categoryWise")) {
					uploadVid.setCategoryName(set);
				}
				if (token.equals("seriesWise")) {
					uploadVid.setSeriesName(set);
				}
				list.forEach((ll) -> {
					if (ll != null) {
						String url = "";
						if (token.equals("categoryWise")) {
							if (set.equals(ll.getCategoryName())) {
								GetVideoByCatSerDto vid = new GetVideoByCatSerDto();
								vid.setCategoryName(ll.getCategoryName());
								;
								vid.setSeriesName(ll.getSeriesName());
								vid.setVideoLink(ll.getVideoLink());
								url = this.applicationProperties.getProperty("appPath") + ll.getUserId()
										+ this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO)
										+ ll.getVideoThumbnail();
								vid.setVideoThumbnail(url);
								vid.setId(ll.getId());
								vid.setVideoName(ll.getVideoThumbnail());
								vid.setTimeLength(ll.getTimeLength());
								vid.setTitle(ll.getTitle());
								vid.setDescription(ll.getDescription());
								vid.setCreatedBy(ll.getCreatedBy());
								getvidsobj.add(vid);
							}
						} else if (token.equals("seriesWise")) {
							if (set.equals(ll.getSeriesName())) {
								GetVideoByCatSerDto vid = new GetVideoByCatSerDto();
								vid.setCategoryName(ll.getCategoryName());
								;
								vid.setSeriesName(ll.getSeriesName());
								vid.setVideoLink(ll.getVideoLink());
								url = this.applicationProperties.getProperty("appPath") + ll.getUserId()
										+ this.applicationProperties.getProperty(ApplicationConstants.UPLOADED_VIDEO)
										+ ll.getVideoThumbnail();
								vid.setVideoThumbnail(url);
								vid.setId(ll.getId());
								vid.setVideoName(ll.getVideoThumbnail());
								vid.setTimeLength(ll.getTimeLength());
								vid.setTitle(ll.getTitle());
								vid.setDescription(ll.getDescription());
								vid.setCreatedBy(ll.getCreatedBy());
								getvidsobj.add(vid);
							}
						}
					}
				});

				if (token.equals("categoryWise")) {
					uploadVid.setCategoryList(getvidsobj);
				}
				if (token.equals("seriesWise")) {
					uploadVid.setSeriesList(getvidsobj);
				}
				finallist.add(uploadVid);

			}
		});

		return finallist;
	}

	@Override
	public List<GetVideoByCatSerDto> SearchVuds(String data) {
		List<GetVideoByCatSerDto> list = adminDao.SearchVuds(data);
		list.forEach((ll) -> {
			String url = this.applicationProperties.getProperty("appPath") + ll.getUserId()
					+ this.applicationProperties.getProperty("uploadVideoFolder") + ll.getVideoThumbnail();
			ll.setVideoThumbnail(url);
		});
		return list;
	}

}
