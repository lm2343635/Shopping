package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.SendeeDao;
import com.xwkj.shopping.domain.Sendee;

public class SandeeDaoHibernate extends PageHibernateDaoSupport implements SendeeDao {

	@Override
	public Sendee get(String sid) {
		return getHibernateTemplate().get(Sendee.class, sid);
	}

	@Override
	public String save(Sendee sendee) {
		return (String)getHibernateTemplate().save(sendee);
	}

	@Override
	public void update(Sendee sendee) {
		getHibernateTemplate().update(sendee);
	}

	@Override
	public void delete(Sendee sendee) {
		getHibernateTemplate().delete(sendee);
	}

}
