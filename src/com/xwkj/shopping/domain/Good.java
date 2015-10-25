package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Good implements Serializable {

	private static final long serialVersionUID = -3514204735446716317L;
	
	private String gid;
	private String gname;
	private Double price;
	private Integer number;
	private Integer sold;
	private Date createDate;
	private Category category;
	private Photo cover;
	private Set<Photo> photos;
	
	public String getGid() {
		return gid;
	}
	public String getGname() {
		return gname;
	}
	public Double getPrice() {
		return price;
	}
	public Integer getNumber() {
		return number;
	}
	public Integer getSold() {
		return sold;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Category getCategory() {
		return category;
	}
	public Photo getCover() {
		return cover;
	}
	public Set<Photo> getPhotos() {
		return photos;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setSold(Integer sold) {
		this.sold = sold;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setCover(Photo cover) {
		this.cover = cover;
	}
	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

}
