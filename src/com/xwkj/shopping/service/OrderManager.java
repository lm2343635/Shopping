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
}
