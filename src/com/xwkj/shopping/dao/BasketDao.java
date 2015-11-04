package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Basket;
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
}
