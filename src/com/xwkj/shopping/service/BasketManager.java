package com.xwkj.shopping.service;

import java.util.List;

import com.xwkj.shopping.bean.BasketBean;

public interface BasketManager {
	
	/**
	 * 新增商品到购物筐
	 * @param uid 用户id
	 * @param gid 商品id
	 * @param count 商品数量
	 * @return
	 */
	String addToBasket(String uid, String good, int count);
	
	/**
	 * 得到一个用户的购物筐内容
	 * @param uid
	 * @return
	 */
	List<BasketBean> getBasketGoodsByUid(String uid);
	
	/**
	 * 变更购买件数
	 * @param bid
	 * @param count
	 * @return 总价
	 */
	double changeCount(String bid, int count);
	
	/**
	 * 从购物车中移除商品
	 * @param bid
	 */
	void removeGoodFromBasket(String bid);
}
