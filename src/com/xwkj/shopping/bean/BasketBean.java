package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Basket;

public class BasketBean {
	
	private String bid;
	private Integer count;
	private Date createDate;
	private GoodBean good;
	private String sid;
	private String oid;
	
	public String getBid() {
		return bid;
	}
	public Integer getCount() {
		return count;
	}
	public GoodBean getGood() {
		return good;
	}
	public String getSid() {
		return sid;
	}
	public String getOid() {
		return oid;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setGood(GoodBean good) {
		this.good = good;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public BasketBean(Basket basket) {
		super();
		this.bid = basket.getBid();
		this.count = basket.getCount();
		this.good = new GoodBean(basket.getGood());
		this.sid = basket.getSendee().getSid();
		this.oid = basket.getOrder()==null? null: basket.getOrder().getOid();
	}
	
}
