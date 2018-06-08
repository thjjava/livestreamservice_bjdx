package com.sttri.dao.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;

public class CommonDaoImpl extends HibernateDaoSupport implements CommonDao{
	public void clear(){
		getSession().clear();
	}
	public void save(Object entity){
		getHibernateTemplate().save(entity);
	}
	@Transactional
	public void update(Object entity){
		getHibernateTemplate().update(entity);
	}

	public <T> void delete(Class<T> entityClass, Object entityid){
		delete(entityClass, new Object[]{ entityid });
	}

	public <T> void delete(Class<T> entityClass, Object[] entityids){
		for (Object id : entityids){
			getHibernateTemplate().delete(find(entityClass, id));
		}
	}
	//直接按一个id进行删除
	public <T> void deleteid(Class<T> entityClass){
		getHibernateTemplate().delete(entityClass);
	}

	public <T> T find(Class<T> entityClass, Object entityId){
		return (T) getHibernateTemplate().get(entityClass,(Serializable) entityId);
	}

	public <T> long getCount(Class<T> entityClass){
		return (Long) getSession().createQuery("select count(o.id) from "+ getEntityName(entityClass)+ " o").uniqueResult();
	} 
	//登陆次数
	public <T> void getLoginCount(Class<T> entityClass){
		getSession().createQuery("update "+ getEntityName(entityClass)+ " o set lonigNum=loginNum+1");
	}
	//最大的max
	public <T> long getMax(Class<T> entityClass){
		return (Integer) getSession().createQuery("select max(o.id) from "+ getEntityName(entityClass)+ " o").uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> entityClass, String wherejpql,
		LinkedHashMap<String, String> orderby, Object... queryParams){
		String entityname = getEntityName(entityClass);
		String hql="select o from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby);
		return (List<T>)getHibernateTemplate().find(hql, queryParams);
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> entityClass,String[]propertyEntiry, String wherejpql,
		LinkedHashMap<String, String> orderby, Object... queryParams){
		String entityname = getEntityName(entityClass);
		StringBuffer joinFetch=new StringBuffer();
		if(propertyEntiry!=null&&propertyEntiry.length>0){
			for(String entity : propertyEntiry){
			  joinFetch.append("left join fetch o.").append(entity+" ");
			}
		}
		String hql="select distinct(o) from "+ entityname+ " o "+joinFetch+(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby);
	    return (List<T>)getHibernateTemplate().find(hql, queryParams);
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> entityClass,String entityName,String joinPropertyName, String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams){
		String hql="select o."+joinPropertyName+" from "+ entityName+ " o "+(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby);
	    return (List<T>)getHibernateTemplate().find(hql, queryParams);
	}
	
	protected <T> String getEntityName(Class<T> entityClass){
		String entityName = entityClass.getSimpleName();
		return entityName;
	}
	/**
	 * 组装order by语句
	 * @param orderby
	 * @return
	 */
	protected String buildOrderby(LinkedHashMap<String, String> orderby){
		StringBuffer orderbyql = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			orderbyql.append(" order by ");
			for(String key : orderby.keySet()){
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length()-1);
		}
		return orderbyql.toString();
	}
	protected void setQueryParams(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			for(int i=0; i<queryParams.length; i++){
				query.setParameter(i, queryParams[i]);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstindex, int maxresult, String wherejpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby){
		Session session=getSession();
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		Query query = session.createQuery("select o from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstindex!=-1 && maxresult!=-1) {
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		}
		query.setCacheable(true);
		qr.setResultlist(query.list());
		query = session.createQuery("select count(o.id) from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql));
		setQueryParams(query, queryParams);
		query.setCacheable(true);
		qr.setTotalRecord((Long)query.uniqueResult());
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	public <T> QueryResult<T> getPageData(int firstindex, int maxresult, String listjpql,String countjpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby){
		Session session=getSession();
		QueryResult<T> qr = new QueryResult<T>();
		Query query = session.createQuery(listjpql+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstindex!=-1 && maxresult!=-1) {
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		}
		qr.setResultlist(query.list());
		query = session.createQuery(countjpql);
		setQueryParams(query, queryParams);
		qr.setTotalRecord((Long)query.uniqueResult());
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	public <T> QueryResult<T> getPublicScrollData(Class<T> entityClass, int firstindex, int maxresult, String sql, String wherejpql, 
			Object[] queryParams,LinkedHashMap<String, String> orderby){
		String entityname = getEntityName(entityClass);
		Session session=getSession();
		QueryResult<T> qr = new QueryResult<T>();
		Query query = session.createQuery(sql +(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstindex!=-1 && maxresult!=-1) {
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		}
		qr.setResultlist(query.list());
		query = session.createQuery("select count(o.id) from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql));
		setQueryParams(query, queryParams);
		qr.setTotalRecord((Long)query.uniqueResult());
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getCustomSql(String sql) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			return query.list();
		} catch (Exception e) {
			throw new RuntimeException(" " + e);
		} finally {
			CloseResource(session);
		}
	}
	
	public void getCustomSqlUpdate(String sql) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			//session.createQuery(sql);
			SQLQuery sqlQuery=session.createSQLQuery(sql);
			sqlQuery.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(" " + e);
		} finally {
			CloseResource(session);
		}
	}
	
	private void CloseResource(Object object){
		if (null == object) {
			return;
		}
		if (object instanceof Session) {
			Session session = (Session) object;
			session.close();
		}

	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(Class<T> entityClass, String wherejpql) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			SQLQuery sqlQuery=session.createSQLQuery(wherejpql);
			sqlQuery.addEntity(entityClass);
			return sqlQuery.list();
		} catch (Exception e) {
			throw new RuntimeException(" " + e);
		} finally {
			CloseResource(session);
		}
	}
	
}
