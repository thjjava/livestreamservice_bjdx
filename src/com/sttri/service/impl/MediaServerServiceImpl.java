package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.MediaServer;
import com.sttri.pojo.TblDev;
import com.sttri.service.IMediaServerService;

@Service
public class MediaServerServiceImpl implements IMediaServerService {
	@Autowired
	private CommonDao dao;
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblDev.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblDev.class, array);
	}

	@Override
	public MediaServer getById(Object id) {
		return dao.find(MediaServer.class, id);
	}

	@Override
	public List<MediaServer> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(MediaServer.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<MediaServer> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(MediaServer.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(MediaServer mediaServer) {
		dao.save(mediaServer);
	}

	@Override
	public void update(MediaServer mediaServer) {
		dao.update(mediaServer);
	}

	@Override
	public List<MediaServer> getResultList(String sql) {
		// TODO Auto-generated method stub
		return dao.getResultList(MediaServer.class, sql);
	}

}
