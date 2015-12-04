package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = -555705669867938214L;

	private String cid;
	private Integer stars;
	private String content;
	private Date commentDate;
	private Boolean enable;
	private Order order;
	private Good good;
	
	public String getCid() {
		return cid;
	}
	public Integer getStars() {
		return stars;
	}
	public String getContent() {
		return content;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public Boolean getEnable() {
		return enable;
	}
	public Order getOrder() {
		return order;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Good getGood() {
		return good;
	}
	public void setGood(Good good) {
		this.good = good;
	}
	
}
