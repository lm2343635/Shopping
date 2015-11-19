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
	private String zip;
	private String email;
	private String trade;
	private boolean send;
	private Date sendDate; 
	private String logistics;
	private boolean receive;
	private Date receiveDate;
	private Date returnDate;
	private SendeeBean sendee;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOno() {
		return ono;
	}

	public void setOno(String ono) {
		this.ono = ono;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isExpress() {
		return express;
	}

	public void setExpress(boolean express) {
		this.express = express;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public SendeeBean getSendee() {
		return sendee;
	}

	public void setSendee(SendeeBean sendee) {
		this.sendee = sendee;
	}

	public boolean isReceive() {
		return receive;
	}

	public void setReceive(boolean receive) {
		this.receive = receive;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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
		this.zip = order.getZip();
		this.email = order.getEmail();
		this.trade = order.getTrade();
		this.send = order.getSend();
		this.sendDate= order.getSendDate();
		this.logistics = order.getLogistics();
		this.receive = order.getReceive();
		this.receiveDate = order.getReceiveDate();
		this.returnDate = order.getReturnDate();
		this.sendee =	new SendeeBean(order.getSendee());
	}
}
