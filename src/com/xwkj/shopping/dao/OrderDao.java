package com.xwkj.shopping.dao;

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
	

}
