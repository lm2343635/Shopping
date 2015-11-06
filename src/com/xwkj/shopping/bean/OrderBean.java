package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Order;

public class OrderBean {
	
	private String oid;
	private String ono;
	private Date createDate;
	private boolean express;
	private int count;
	private double amount;
	private boolean payed;
	private boolean timeout;
	private Date payDate; 
	private String name;
	private String telephone;
	private String address;
	private String email;
	private SendeeBean sendee;
	
	public String getOid() {
		return oid;
	}
	public String getOno() {
		return ono;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public boolean isExpress() {
		return express;
	}
	public int getCount() {
		return count;
	}
	public double getAmount() {
		return amount;
	}
	public boolean isPayed() {
		return payed;
	}
	public boolean isTimeout() {
		return timeout;
	}
	public Date getPayDate() {
		return payDate;
	}
	public String getName() {
		return name;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public void setOno(String ono) {
		this.ono = ono;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setExpress(boolean express) {
		this.express = express;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setPayed(boolean payed) {
		this.payed = payed;
	}
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public SendeeBean getSendee() {
		return sendee;
	}
	public void setSendee(SendeeBean sendee) {
		this.sendee = sendee;
	}
	
	public OrderBean(Order order) {
		super();
		this.oid = order.getOid();
		this.ono = order.getOno();
		this.createDate = order.getCreateDate();
		this.express = order.getExpress();
		this.count = order.getCount();
		this.amount = order.getAmount();
		this.payed = order.getPayed();
		this.timeout = order.getTimeout();
		this.payDate = order.getPayDate();
		this.name = order.getName();
		this.telephone = order.getTelephone();
		this.address = order.getAddress();
		this.email = order.getEmail();
		this.sendee =	new SendeeBean(order.getSendee());
	}
}
