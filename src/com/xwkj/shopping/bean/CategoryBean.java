package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Category;

public class CategoryBean {
	private String cid;
	private String cname;
	private Date createDate;
	private int goods;
	private TypeBean type;
	private IconBean icon;
	
	public String getCid() {
		return cid;
	}
	public String getCname() {
		return cname;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public int getGoods() {
		return goods;
	}
	public TypeBean getType() {
		return type;
	}
	public IconBean getIcon() {
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
	public void setGoods(int goods) {
		this.goods = goods;
	}
	public void setType(TypeBean type) {
		this.type = type;
	}
	public void setIcon(IconBean icon) {
		this.icon = icon;
	}
	
	public CategoryBean(Category category) {
		super();
		this.cid = category.getCid();
		this.cname = category.getCname();
		this.createDate = category.getCreateDate();
		this.goods = category.getGoods();
		this.type = new TypeBean(category.getType());
		this.icon = category.getIcon()==null? null: new IconBean(category.getIcon());
	}
}
