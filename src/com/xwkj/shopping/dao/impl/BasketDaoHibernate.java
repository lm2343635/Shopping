package com.xwkj.shopping.dao.impl;

import java.util.List;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.BasketDao;
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.domain.Sendee;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Basket> findUnorderdBySendee(Sendee sendee) {
		String hql="from Basket where order=null and sendee=? order by createDate";
		return getHibernateTemplate().find(hql, sendee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Basket findByGood(Good good, Sendee sendee) {
		List<Basket> baskets=getHibernateTemplate().find("from Basket where good=? and order=null and sendee=?", good, sendee);
		if(baskets.size()==0)
			return null;
		return baskets.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Basket> findByOrder(Order order) {
		String hql="from Basket where order=? order by createDate";
		return getHibernateTemplate().find(hql, order);
	}

}
