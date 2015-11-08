package com.xwkj.shopping.dao;

import java.util.Date;
import java.util.List;

import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.domain.Sendee;

public interface OrderDao {
	Order get(String oid);
	String save(Order order);
	void update(Order order);
	void delete(Order order);
	
	/**
	 * 通过订单号查询订单
	 * @param ono
	 * @return
	 */
	Order findByOno(String ono);
	
	/**
	 * 根据收货人查询订单
	 * @param sendee
	 * @return
	 */
	List<Order> findBySendee(Sendee sendee);
	
	/**
	 * 查找超时未支付的订单
	 * @param time 超时时间点
	 * @return
	 */
	List<Order> findWillTimeoutOrders(Date time);
}
