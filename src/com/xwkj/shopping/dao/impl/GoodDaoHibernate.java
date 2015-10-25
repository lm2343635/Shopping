package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.GoodDao;
import com.xwkj.shopping.domain.Good;

public class GoodDaoHibernate extends PageHibernateDaoSupport implements GoodDao {

	@Override
	public Good get(String gid) {
		return getHibernateTemplate().get(Good.class, gid);
	}

	@Override
	public String save(Good good) {
		return (String)getHibernateTemplate().save(good);
	}

	@Override
	public void update(Good good) {
		getHibernateTemplate().update(good);
	}

	@Override
	public void delete(Good good) {
		getHibernateTemplate().delete(good);
	}

}
