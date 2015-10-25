package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Basket;

public interface BasketDao {

	Basket get(String bid);
	String save(Basket basket);
	void update(Basket basket);
	void delete(Basket basket);
}
