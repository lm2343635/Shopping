package com.xwkj.shopping.domain;

import java.io.Serializable;

public class Sendee implements Serializable {
	
	private static final long serialVersionUID = -7536144077545710462L;
	
	private String sid;
	private String uid;
	private String sname;
	private String email;
	private String address;
	private String telephone;
	private String zip;
	
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

}
