package com.xwkj.shopping.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

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
		return getHibernateTemplate().find("from Order where sendee=? order by createDate desc", sendee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findWillTimeoutOrders(Date time) {
		String hql="from Order where payed=false and timeout=false and createDate<=?";
		return getHibernateTemplate().find(hql, time);
	}

	@Override
	public int getOrdersCount(boolean payed, boolean timeout, boolean send, boolean receive, String ono) {
		String hql="select count(*) from Order where payed=? and timeout=? and send=? and receive=? and ono like ?";
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setParameter(0, payed);
				query.setParameter(1, timeout);
				query.setParameter(2, send);
				query.setParameter(3, receive);
				query.setParameter(4, "%"+ono+"%");
				return (long)query.uniqueResult();
			}
		}).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findOrders(boolean payed, boolean timeout, boolean send, boolean receive, String ono, int offset, int pageSize) {
		String hql="from Order where payed=? and timeout=? and send=? and receive=? and ono like ?";
		if(payed&&!timeout&&!send&&!receive) { //待发货
			hql+=" order by payDate desc";
		} else if(payed&&!timeout&&send&&!receive) { //已发货
			hql+=" order by sendDate desc";
		} else if(payed&&!timeout&&send&&receive) { //已完成
			hql+=" order by receiveDate desc";
		} else if(!payed&&!timeout&&send&&receive) { //待退款
			hql+=" order by returnDate desc";
		} else if(!payed&&!timeout&&!send&&!receive) { //待付款
			hql+=" order by createDate desc";
		} else if(!payed&&timeout&&!send&&!receive) { //未付款
			hql+=" order by createDate desc";
		}
		return findByPage(hql, new Object[]{payed, timeout, send, receive, "%"+ono+"%"}, offset, pageSize);
	}

}
