package com.xwkj.shopping.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xwkj.shopping.bean.BasketBean;
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Sendee;
import com.xwkj.shopping.service.BasketManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

public class BasketManagerImpl extends ManagerTemplate implements BasketManager {

	@Override
	public String addToBasket(String uid, String gid, int count) {
		Basket basket=new Basket();
		Sendee sendee=sendeeDao.findByUid(uid);
		basket.setSendee(sendee);
		Good good=goodDao.get(gid);
		basket.setGood(good);
		basket.setCount(count);
		basket.setCreateDate(new Date());
		return basketDao.save(basket);
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
	public double changeCount(String bid, int count) {
		Basket basket=basketDao.get(bid);
		basket.setCount(count);
		basketDao.update(basket);
		return basket.getGood().getPrice()*count;
	}

	@Override
	public void removeGoodFromBasket(String bid) {
		Basket basket=basketDao.get(bid);
		basketDao.delete(basket);
	}

}
