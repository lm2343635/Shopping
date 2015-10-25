package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.PhotoDao;
import com.xwkj.shopping.domain.Photo;

public class PhotoDaoHibernate extends PageHibernateDaoSupport implements PhotoDao {

	@Override
	public Photo get(String pid) {
		return getHibernateTemplate().get(Photo.class, pid);
	}

	@Override
	public String save(Photo photo) {
		return (String)getHibernateTemplate().save(photo);
	}

	@Override
	public void update(Photo photo) {
		getHibernateTemplate().update(photo);
	}

	@Override
	public void delete(Photo photo) {
		getHibernateTemplate().delete(photo);
	}

}
