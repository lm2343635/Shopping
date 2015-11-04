package com.xwkj.shopping.dao.impl;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Override
	public Sendee findByUid(String uid) {
		List<Sendee> sendees=getHibernateTemplate().find("from Sendee where uid=?", uid);
		if(sendees.size()==0)
			return null;
		return sendees.get(0);
	}

}
