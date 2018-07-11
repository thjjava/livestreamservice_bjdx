package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.SensitiveWord;
import com.sttri.service.ISensitiveWordService;

@Service
public class SensitiveWordServiceImpl implements ISensitiveWordService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(SensitiveWord.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(SensitiveWord.class, array);
	}

	@Override
	public SensitiveWord getById(Object id) {
		return dao.find(SensitiveWord.class, id);
	}

	@Override
	public List<SensitiveWord> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(SensitiveWord.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<SensitiveWord> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(SensitiveWord.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(SensitiveWord sensitiveWord) {
		dao.save(sensitiveWord);
	}

	@Override
	public void update(SensitiveWord sensitiveWord) {
		dao.update(sensitiveWord);
	}


}
