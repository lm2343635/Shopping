package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.IconDao;
import com.xwkj.shopping.domain.Icon;

public class IconDaoHibernate extends PageHibernateDaoSupport implements IconDao {

	@Override
	public Icon get(String iid) {
		return getHibernateTemplate().get(Icon.class, iid);
	}

	@Override
	public String save(Icon icon) {
		return (String)getHibernateTemplate().save(icon);
	}

	@Override
	public void update(Icon icon) {
		getHibernateTemplate().update(icon);
	}

	@Override
	public void delete(Icon icon) {
		getHibernateTemplate().delete(icon);
	}

}
