package com.xwkj.shopping.dao.impl;

import java.util.List;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.CategoryDao;
import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Type;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findByType(Type type) {
		return getHibernateTemplate().find("from Category where type=? order by createDate desc", type);
	}

}
