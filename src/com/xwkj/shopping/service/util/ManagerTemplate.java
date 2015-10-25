package com.xwkj.shopping.service.util;

import com.xwkj.shopping.dao.BasketDao;
import com.xwkj.shopping.dao.CategoryDao;
import com.xwkj.shopping.dao.GoodDao;
import com.xwkj.shopping.dao.IconDao;
import com.xwkj.shopping.dao.OrderDao;
import com.xwkj.shopping.dao.PhotoDao;
import com.xwkj.shopping.dao.SendeeDao;
import com.xwkj.shopping.dao.TypeDao;

public class ManagerTemplate {
	protected BasketDao basketDao;
	protected CategoryDao categoryDao;
	protected GoodDao goodDao;
	protected IconDao iconDao;
	protected OrderDao orderDao;
	protected PhotoDao photoDao;
	protected SendeeDao sendeeDao;
	protected TypeDao typeDao;
	
	public BasketDao getBasketDao() {
		return basketDao;
	}
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	public GoodDao getGoodDao() {
		return goodDao;
	}
	public IconDao getIconDao() {
		return iconDao;
	}
	public OrderDao getOrderDao() {
		return orderDao;
	}
	public PhotoDao getPhotoDao() {
		return photoDao;
	}
	public SendeeDao getSendeeDao() {
		return sendeeDao;
	}
	public TypeDao getTypeDao() {
		return typeDao;
	}
	public void setBasketDao(BasketDao basketDao) {
		this.basketDao = basketDao;
	}
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	public void setGoodDao(GoodDao goodDao) {
		this.goodDao = goodDao;
	}
	public void setIconDao(IconDao iconDao) {
		this.iconDao = iconDao;
	}
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}
	public void setSendeeDao(SendeeDao sendeeDao) {
		this.sendeeDao = sendeeDao;
	}
	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}

}
