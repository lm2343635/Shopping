package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.CategoryDao;
import com.xwkj.shopping.domain.Category;

public class CategoryDaoHibernate extends PageHibernateDaoSupport implements CategoryDao {

	@Override
	public Category get(String cid) {
		return getHibernateTemplate().get(Category.class, cid);
	}

	@Override
	public String save(Category category) {
		return (String)getHibernateTemplate().save(category);
	}

	@Override
	public void update(Category category) {
		getHibernateTemplate().update(category);
	}

	@Override
	public void delete(Category category) {
		getHibernateTemplate().delete(category);
	}

}
