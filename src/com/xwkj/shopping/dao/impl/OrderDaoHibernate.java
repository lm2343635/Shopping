package com.xwkj.shopping.dao.impl;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.OrderDao;
import com.xwkj.shopping.domain.Order;

public class OrderDaoHibernate extends PageHibernateDaoSupport implements OrderDao {

	@Override
	public Order get(String oid) {
		return getHibernateTemplate().get(Order.class, oid);
	}

	@Override
	public String save(Order order) {
		return (String)getHibernateTemplate().save(order);
	}

	@Override
	public void update(Order order) {
		getHibernateTemplate().update(order);
	}

	@Override
	public void delete(Order order) {
		getHibernateTemplate().delete(order);
	}

}
