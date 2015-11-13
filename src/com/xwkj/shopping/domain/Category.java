package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {

	private static final long serialVersionUID = 7906540632450428382L;

	private String cid;
	private String cname;
	private Boolean enable;
	private Date createDate;
	private Integer goods;
	private Type type;
	private Icon icon;
	
	public String getCid() {
		return cid;
	}
	public String getCname() {
		return cname;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Integer getGoods() {
		return goods;
	}
	public Type getType() {
		return type;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setGoods(Integer goods) {
		this.goods = goods;
	}
	public void setType(Type type) {
		this.type = type;
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
