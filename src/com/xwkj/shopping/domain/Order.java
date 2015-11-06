package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1740553232189480871L;

	private String oid;
	private String ono;
	private Date createDate;
	private Boolean express;
	private Integer count;
	private Double amount;
	private Boolean payed;
	private Boolean timeout;
	private Date payDate; 
	private String name;
	private String telephone;
	private String address;
	private String email;
	private Sendee sendee;
	
	public String getOid() {
		return oid;
	}
	public String getOno() {
		return ono;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Boolean getExpress() {
		return express;
	}
	public Integer getCount() {
		return count;
	}
	public Double getAmount() {
		return amount;
	}
	public Boolean getPayed() {
		return payed;
	}
	public Boolean getTimeout() {
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
	public Sendee getSendee() {
		return sendee;
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
	public void setExpress(Boolean express) {
		this.express = express;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setPayed(Boolean payed) {
		this.payed = payed;
	}
	public void setTimeout(Boolean timeout) {
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
	public void setSendee(Sendee sendee) {
		this.sendee = sendee;
	}
	
	@Override
	public String toString() {
		String order="oid="+oid
				+ "ono="+ono
				+ "createDate="+createDate
				+ "express="+express
				+ "count="+count
				+ "amount="+amount
				+ "payed="+payed
				+ "timeout="+timeout
				+ "payDate="+payDate
				+ "name="+name
				+ "telephone="+telephone
				+ "address="+address
				+ "email="+email
				+ "sendee="+sendee;
		return order;
	}
}
