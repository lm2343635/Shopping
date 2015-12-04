package com.xwkj.shopping.service;

import java.util.List;

import com.xwkj.shopping.bean.CommentBean;

public interface CommentManager {
	
	/**
	 * 撰写评论
	 * @param oid 订单id
	 * @param gid 商品id
	 * @param content 评论内容
	 * @param stars 星级
	 * @return
	 */
	boolean writeComment(String oid, String gid, String content, int stars);
	
	/**
	 * 获取订单
	 * @param cid
	 * @return
	 */
	CommentBean getComment(String cid);
	
	/**
	 * 通过订单和商品获取唯一评论
	 * @param oid
	 * @param gid
	 * @return
	 */
	CommentBean getCommentByOidAndGid(String oid, String gid);
	
	/**
	 * 获取评论数量
	 * @param start
	 * @param end
	 * @param gname 商品名称
	 * @param enable
	 * @return
	 */
	int getCommentsCount(String start, String end, String gname, int enable);
	
	/**
	 * 搜索评论
	 * @param start
	 * @param end
	 * @param gname 商品名称
	 * @param enable
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<CommentBean> searchComments(String start, String end, String gname, int enable, int page, int pageSize);
	
	/**
	 * 审核评论
	 * @param cid
	 * @param enable
	 */
	void checkComment(String cid, boolean enable);
	
	/**
	 * 删除评论
	 * @param cid
	 */
	void removeComment(String cid);
	
	/**
	 * 获取商品的所有已审核评论
	 * @param gid
	 * @return
	 */
	List<CommentBean> getCommentsByGid(String gid);
}
