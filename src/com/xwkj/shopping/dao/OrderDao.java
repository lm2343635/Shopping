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
	
	/**
	 * 查询指定条件下的订单数量
	 * @param payed 是否支付
	 * @param timeout 是否超时
	 * @param send 是否发货
	 * @param receive 是否收货
	 * @param ono 订单号
	 * @return
	 */
	int getOrdersCount(boolean payed, boolean timeout, boolean send, boolean receive, String ono);
	
	/**
	 * 搜索订单
	 * @param payed 是否支付
	 * @param timeout 是否超时
	 * @param send 是否发货
	 * @param receive 是否收货
	 * @param ono 订单号
	 * @param page 页码
	 * @param pageSize 页面长度
	 * @return
	 */
	List<Order> findOrders(boolean payed, boolean timeout, boolean send, boolean receive, String ono, int offset, int pageSize);
}
