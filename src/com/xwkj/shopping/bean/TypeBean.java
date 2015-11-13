package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Type;

public class TypeBean {
	private String tid;
	private String tname;
	private boolean enable;
	private Date createDate;
	private int categories;
	private IconBean icon;
	
	public String getTid() {
		return tid;
	}
	public String getTname() {
		return tname;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public int getCategories() {
		return categories;
	}
	public IconBean getIcon() {
		return icon;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setCategories(int categories) {
		this.categories = categories;
	}
	public void setIcon(IconBean icon) {
		this.icon = icon;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public TypeBean(Type type) {
		super();
		this.tid = type.getTid();
		this.tname = type.getTname();
		this.enable= type.getEnable();
		this.createDate = type.getCreateDate();
		this.categories = type.getCategories();
		this.icon=type.getIcon()==null? null: new IconBean(type.getIcon());
	}

}
