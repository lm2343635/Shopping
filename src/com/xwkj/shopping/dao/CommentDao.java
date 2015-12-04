package com.xwkj.shopping.dao;

import java.util.Date;
import java.util.List;

import com.xwkj.shopping.domain.Comment;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;

public interface CommentDao {
	Comment get(String cid);
	String save(Comment comment);
	void update(Comment comment);
	void delete(Comment comment);
	
	/**
	 * 通过订单查找评论
	 * @param order
	 * @return
	 */
	List<Comment> findByOrder(Order order);
	
	/**
	 *通过商品查找已审核的评论
	 * @param good
	 * @return
	 */
	List<Comment> findByGood(Good good);
	
	/**
	 * 通过商品和订单查找评论
	 * @param order
	 * @param good
	 * @return 商品和订单确定唯一的评论
	 */
	Comment findByOrderAndGood(Order order, Good good);
	
	/**
	 * 获取评论数量
	 * @param start
	 * @param end
	 * @param gname 商品名称
	 * @param showAll
	 * @param enable
	 * @return
	 */
	int getCommentsCount(Date start, Date end, String gname, boolean showAll, boolean enable);
	
	/**
	 * 分页查询评论
	 * @param start
	 * @param end
	 * @param gname 商品名称
	 * @param showAll
	 * @param enable
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<Comment> findByPage(Date start, Date end, String gname, boolean showAll, boolean enable, int offset, int pageSize);
}
