package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1740553232189480871L;

	private String oid;
	private Date createDate;
	private Boolean express;
	private Integer count;
	private Double amount;
	private Boolean payed;
	private Boolean timeout;
	private Date payDate; 
	private Sendee sendee;
	
	public String getOid() {
		return oid;
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
	public Sendee getSendee() {
		return sendee;
	}
	public void setOid(String oid) {
		this.oid = oid;
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
	public void setSendee(Sendee sendee) {
		this.sendee = sendee;
	}
	
}
