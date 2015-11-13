package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Type implements Serializable {

	private static final long serialVersionUID = -7137323648735103021L;

	private String tid;
	private String tname;
	private Boolean enable;
	private Date createDate;
	private Integer categories;
	private Icon icon;
	
	public String getTid() {
		return tid;
	}
	public String getTname() {
		return tname;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Integer getCategories() {
		return categories;
	}
	public Icon getIcon() {
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
	public void setCategories(Integer categories) {
		this.categories = categories;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
