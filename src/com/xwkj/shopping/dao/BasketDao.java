package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.domain.Sendee;

public interface BasketDao {

	Basket get(String bid);
	String save(Basket basket);
	void update(Basket basket);
	void delete(Basket basket);
	
	/**
	 * 查询某个收货人未下单的商品
	 * @param sendee
	 * @return
	 */
	List<Basket> findUnorderdBySendee(Sendee sendee);
	
	/**
	 * 根据商品查询购物车项目
	 * @param good
	 * @param sendee
	 * @return
	 */
	Basket findByGood(Good good, Sendee sendee);
	
	/**
	 * 根据订单查询购物车
	 * @param order
	 * @return
	 */
	List<Basket> findByOrder(Order order);
}
