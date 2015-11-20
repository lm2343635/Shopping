package com.xwkj.shopping.service;

import java.util.List;
import java.util.Map;

import com.xwkj.shopping.bean.OrderBean;
import com.xwkj.shopping.bean.UserBean;

public interface OrderManager {
	
	int getPayTimeOut();
	
	int getPaySuccessSMSTemplateID();
	
	/**
	 * 新增订单
	 * @param user
	 * @param express
	 * @param name
	 * @param telephone
	 * @param address
	 * @param email
	 * @return
	 */
	Map<String, Object> addOrder(UserBean user, boolean express, String name, String telephone, String address, String zip, String email);
	
	/**
	 * 获取订单
	 * @param oid
	 * @return
	 */
	OrderBean getOrder(String oid);
	
	/**
	 * 通过订单号查询订单
	 * @param ono
	 * @return
	 */
	OrderBean getOrderByOno(String ono);
	
	/**
	 * 删除订单
	 * @param oid
	 * @return
	 */
	boolean removeOrder(String oid);
	
	/**
	 * 得到某个用户的所有订单
	 * @param uid
	 * @return
	 */
	List<OrderBean> getOrdersByUid(String uid);
	
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
	List<OrderBean> searchOrders(boolean payed, boolean timeout, boolean send, boolean receive, String ono, int page, int pageSize);
	
	/**
	 * 发货
	 * @param oid
	 * @param logistics
	 * @return
	 */
	void savetLogistics(String oid, String logistics);
}
