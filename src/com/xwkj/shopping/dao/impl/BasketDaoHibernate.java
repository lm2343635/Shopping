package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.BasketDao;
import com.xwkj.shopping.domain.Basket;

public class BasketDaoHibernate extends PageHibernateDaoSupport implements BasketDao {

	@Override
	public Basket get(String bid) {
		return getHibernateTemplate().get(Basket.class, bid);
	}

	@Override
	public String save(Basket basket) {
		return (String)getHibernateTemplate().save(basket);
	}

	@Override
	public void update(Basket basket) {
		getHibernateTemplate().update(basket);
	}

	@Override
	public void delete(Basket basket) {
		getHibernateTemplate().delete(basket);
	}

}
