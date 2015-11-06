package com.xwkj.shopping.dao.impl;

import java.util.List;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.OrderDao;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.domain.Sendee;

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

	@SuppressWarnings("unchecked")
	@Override
	public Order findByOno(String ono) {
		List<Order> orders=getHibernateTemplate().find("from Order where ono=?", ono);
		if(orders.size()==0)
			return null;
		return orders.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findBySendee(Sendee sendee) {
		return getHibernateTemplate().find("from Order where sendee=?", sendee);
	}

}
