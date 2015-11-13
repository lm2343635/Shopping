package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Good;

public class GoodBean {
	private String gid;
	private String gname;
	private boolean enable;
	private double price;
	private int number;
	private String descriptor;
	private int sold;
	private Date createDate;
	private CategoryBean category;
	private PhotoBean cover;
	
	public String getGid() {
		return gid;
	}
	public String getGname() {
		return gname;
	}
	public double getPrice() {
		return price;
	}
	public int getNumber() {
		return number;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public int getSold() {
		return sold;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public CategoryBean getCategory() {
		return category;
	}
	public PhotoBean getCover() {
		return cover;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setCategory(CategoryBean category) {
		this.category = category;
	}
	public void setCover(PhotoBean cover) {
		this.cover = cover;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public GoodBean(Good good) {
		super();
		this.gid = good.getGid();
		this.gname = good.getGname();
		this.enable= good.getEnable();
		this.price = good.getPrice();
		this.number = good.getNumber();
		this.descriptor = good.getDescriptor();
		this.sold = good.getSold();
		this.createDate = good.getCreateDate();
		this.category = new CategoryBean(good.getCategory());
		this.cover = good.getCover()==null? null: new PhotoBean(good.getCover());
	}
	
}
