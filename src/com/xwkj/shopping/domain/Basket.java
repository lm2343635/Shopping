package com.xwkj.shopping.domain;

import java.io.Serializable;

public class Basket implements Serializable {

	private static final long serialVersionUID = 1394247478400431704L;

	private String bid;
	private Integer count;
	private Good good;
	private Sendee sendee;
	private Order order;
	
	public String getBid() {
		return bid;
	}
	public Integer getCount() {
		return count;
	}
	public Good getGood() {
		return good;
	}
	public Sendee getSendee() {
		return sendee;
	}
	public Order getOrder() {
		return order;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setGood(Good good) {
		this.good = good;
	}
	public void setSendee(Sendee sendee) {
		this.sendee = sendee;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
