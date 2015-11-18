package com.xwkj.shopping.dao.impl;

import java.util.List;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.TypeDao;
import com.xwkj.shopping.domain.Type;

public class TypeDaoHibernate extends PageHibernateDaoSupport implements TypeDao {

	@Override
	public Type get(String tid) {
		return getHibernateTemplate().get(Type.class, tid);
	}

	@Override
	public String save(Type type) {
		return (String)getHibernateTemplate().save(type);
	}

	@Override
	public void update(Type type) {
		getHibernateTemplate().update(type);
	}

	@Override
	public void delete(Type type) {
		getHibernateTemplate().delete(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Type> findAll(boolean enable) {
		String hql="from Type order by createDate desc";
		if(enable) 
			hql="from Type where enable=true order by createDate desc";
		return getHibernateTemplate().find(hql);
	}

}
