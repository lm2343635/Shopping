package com.xwkj.shopping.dao.impl;

import java.util.List;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.PhotoDao;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Photo;
import com.xwkj.shopping.domain.Type;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> findByGood(Good good) {
		return getHibernateTemplate().find("from Photo where good=? order by upload", good);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> findByType(Type type) {
		return getHibernateTemplate().find("from Photo where good.category.type=? order by upload", type);
	}

}
