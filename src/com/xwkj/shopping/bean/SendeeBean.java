package com.xwkj.shopping.bean;

import com.xwkj.shopping.domain.Sendee;

public class SendeeBean {
	private String sid;
	private String uid;
	private String sname;
	private String email;
	private String address;
	private String telephone;
	
	public String getSid() {
		return sid;
	}
	public String getUid() {
		return uid;
	}
	public String getSname() {
		return sname;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public SendeeBean(Sendee sendee) {
		super();
		this.sid = sendee.getSid();
		this.uid = sendee.getUid();
		this.sname = sendee.getSname();
		this.email = sendee.getEmail();
		this.address = sendee.getAddress();
		this.telephone = sendee.getTelephone();
	}
	
}
