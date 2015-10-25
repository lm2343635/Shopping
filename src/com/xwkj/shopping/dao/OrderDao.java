package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Order;

public interface OrderDao {
	Order get(String oid);
	String save(Order order);
	void update(Order order);
	void delete(Order order);
}
