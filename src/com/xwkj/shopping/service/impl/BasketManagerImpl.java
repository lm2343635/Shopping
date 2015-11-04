package com.xwkj.shopping.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xwkj.shopping.bean.BasketBean;
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Sendee;
import com.xwkj.shopping.service.BasketManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

public class BasketManagerImpl extends ManagerTemplate implements BasketManager {

	@Override
	public String addToBasket(String uid, String gid, int count) {
		Good good=goodDao.get(gid);
		Sendee sendee=sendeeDao.findByUid(uid);
		//该商品未加入购物车则加入购物车，已加入购物车就修改商品数量
		Basket basket=basketDao.findByGood(good, sendee);
		if(basket==null) {
			basket=new Basket();
			basket.setSendee(sendee);
			basket.setGood(good);
			basket.setCount(count);
			basket.setCreateDate(new Date());
			return basketDao.save(basket);
		} else {
			basket.setCount(basket.getCount()+count);
			basketDao.update(basket);
			return basket.getBid();
		}
	}

	@Override
	public List<BasketBean> getBasketGoodsByUid(String uid) {
		List<BasketBean> baskets=new ArrayList<>();
		Sendee sendee=sendeeDao.findByUid(uid);
		for(Basket basket: basketDao.findUnorderdBySendee(sendee))
			baskets.add(new BasketBean(basket));
		return baskets;
	}

	@Override
	public Map<String, Object> changeCount(String bid, int count) {
		Map<String, Object> data=new HashMap<>();
		Basket basket=basketDao.get(bid);
		int dcount=count-basket.getCount();
		basket.setCount(count);
		basketDao.update(basket);
		data.put("dcount", dcount);
		data.put("amount", basket.getGood().getPrice()*count);
		data.put("damount", basket.getGood().getPrice()*dcount);
		return data;
	}

	@Override
	public void removeGoodFromBasket(String bid) {
		Basket basket=basketDao.get(bid);
		basketDao.delete(basket);
	}

	@Override
	public void clearBasket(String uid) {
		Sendee sendee=sendeeDao.findByUid(uid);
		List<Basket> baskets=basketDao.findUnorderdBySendee(sendee);
		for(Basket basket: baskets)
			basketDao.delete(basket);
	}

}
