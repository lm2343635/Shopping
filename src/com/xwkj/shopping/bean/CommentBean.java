package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Comment;

public class CommentBean {
	private String cid;
	private int stars;
	private String content;
	private Date commentDate;
	private boolean enable;
	private OrderBean order;
	private GoodBean good;
	
	public String getCid() {
		return cid;
	}

	public int getStars() {
		return stars;
	}

	public String getContent() {
		return content;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public boolean isEnable() {
		return enable;
	}

	public OrderBean getOrder() {
		return order;
	}

	public GoodBean getGood() {
		return good;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setOrder(OrderBean order) {
		this.order = order;
	}

	public void setGood(GoodBean good) {
		this.good = good;
	}

	public CommentBean(Comment comment) {
		super();
		this.cid = comment.getCid();
		this.stars = comment.getStars();
		this.content = comment.getContent();
		this.commentDate = comment.getCommentDate();
		this.enable = comment.getEnable();
		this.order = new OrderBean(comment.getOrder());
		this.good = new GoodBean(comment.getGood());
	}
	
}
