package com.sttri.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.sttri.bean.QueryResult;

public interface CommonDao{
	/**
	 * 获取记录总数
	 * @param entityClass 实体类
	 * @return 记录数量
	 */
	public <T> long getCount(Class<T> entityClass);
	/**
	 * 清除一级缓存的数据
	 */
	public void clear();
	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public void save(Object entity);
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public void update(Object entity);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityid 实体id
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	/**
	 * 获取实体
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
	/**
	 * 根据传入的参数查询实体
	 * @param <T>
	 * @param entityClass
	 * @param wherejpql 查询条件
	 * @param orderby 排序条件
	 * @param queryParams 查询条件参数
	 * @return 实体集合
	 */
	public <T> List<T> getResultList(Class<T> entityClass, String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams);
	/**
	 * 根据传入的参数查询关联的实体
	 * @param <T>
	 * @param entityClass 关联的实体
	 * @param entityName 实体名
	 * @param wherejpql
	 * @param orderby
	 * @param queryParams
	 * @return
	 */
	public <T> List<T> getResultList(Class<T> entityClass,String entityName,String joinPropertyName, String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams);
	
	public <T> List<T> getResultList(Class<T> entityClass,String[] propertyEntiry, String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams);
	
	/**
	 * 查询最大id
	 */
	public <T> long getMax(Class<T> entityClass);
	/**
	 * 统计登陆次数
	 */
	public <T> void getLoginCount(Class<T> entityClass);
	
	/**
	 * 获取分页数据
	 * @param <T>
	 * @param entityClass 实体类
	 * @param firstindex 开始索引
	 * @param maxresult 需要获取的记录数
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult, String wherejpql, 
			Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	/**
	 * 公共获取含外键的分页数据
	 * @param <T>
	 * @param firstindex
	 * @param maxresult
	 * @param listjpql
	 * @param countjpql
	 * @param queryParams
	 * @param orderby
	 * @return
	 */
	public <T> QueryResult<T> getPageData(int firstindex, int maxresult, String listjpql, String countjpql, 
			Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getPublicScrollData(Class<T> entityClass, int firstindex, int maxresult, String sql, String wherejpql, 
			Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	public <T> List<T> getCustomSql(String sql);
	
	public void getCustomSqlUpdate(String sql);
	
	/**
	 * 根据传入的参数查询实体
	 * @param <T>
	 * @param entityClass
	 * @param wherejpql 查询条件
	 * @param orderby 排序条件
	 * @param queryParams 查询条件参数
	 * @return 实体集合
	 */
	public <T> List<T> getResultList(Class<T> entityClass, String wherejpql);
}
