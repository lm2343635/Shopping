package com.xwkj.shopping.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xwkj.common.util.DateTool;
import com.xwkj.shopping.bean.CommentBean;
import com.xwkj.shopping.domain.Comment;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.service.CommentManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

public class CommentManagerImpl extends ManagerTemplate implements CommentManager {

	@Override
	public boolean writeComment(String oid, String gid, String content, int stars) {
		Order order=orderDao.get(oid);
		//未收货的商品无法评论
		if(!order.getReceive())
			return false;
		Good good=goodDao.get(gid);
		Comment comment=commentDao.findByOrderAndGood(order, good);
		//未评论创建新的评论
		if(comment==null) {
			comment=new Comment();
			comment.setContent(content);
			comment.setStars(stars);
			comment.setCommentDate(new Date());
			comment.setEnable(false);
			comment.setOrder(order);
			comment.setGood(good);
			commentDao.save(comment);
		} 
		//已评论修改原评论，重置可用标志位
		else {
			comment.setContent(content);
			comment.setStars(stars);
			comment.setCommentDate(new Date());
			comment.setEnable(false);
			commentDao.update(comment);
		}
		return true;
	}

	@Override
	public CommentBean getComment(String cid) {
		Comment comment=commentDao.get(cid);
		if(comment==null)
			return null;
		return new CommentBean(comment);
	}

	@Override
	public CommentBean getCommentByOidAndGid(String oid, String gid) {
		Order order=orderDao.get(oid);
		Good good=goodDao.get(gid);
		Comment comment=commentDao.findByOrderAndGood(order, good);
		if(comment==null)
			return null;
		return new CommentBean(comment);
	}

	@Override
	public int getCommentsCount(String start, String end, String gname, int enable) {
		boolean _enable=false, showAll=false;
		if(enable==-1)
			showAll=true;
		else
			_enable= (enable==1)? true: false;
		Date startDate=null, endDate=null;
		if(!start.equals("")) 
			startDate=DateTool.transferDate(start+" 00:00:00", DateTool.DATE_HOUR_MINUTE_FORMAT);
		if(!end.equals("")) 
			endDate=DateTool.transferDate(end+" 23:59:59", DateTool.DATE_HOUR_MINUTE_FORMAT);
		return commentDao.getCommentsCount(startDate, endDate, gname, showAll, _enable);
	}

	@Override
	public List<CommentBean> searchComments(String start, String end, String gname, int enable, int page,
			int pageSize) {
		boolean _enable=false, showAll=false;
		if(enable==-1)
			showAll=true;
		else
			_enable= (enable==1)? true: false;
		List<CommentBean> comments=new ArrayList<>();
		int offset=(page-1)*pageSize;
		Date startDate=null, endDate=null;
		if(!start.equals("")) 
			startDate=DateTool.transferDate(start+" 00:00:00", DateTool.DATE_HOUR_MINUTE_FORMAT);
		if(!end.equals("")) 
			endDate=DateTool.transferDate(end+" 23:59:59", DateTool.DATE_HOUR_MINUTE_FORMAT);
		for(Comment comment: commentDao.findByPage(startDate, endDate, gname, showAll, _enable, offset, pageSize))
			comments.add(new CommentBean(comment));
		return comments;
	}

	@Override
	public void checkComment(String cid, boolean enable) {
		Comment comment=commentDao.get(cid);
		comment.setEnable(enable);
		commentDao.update(comment);
	}

	@Override
	public void removeComment(String cid) {
		Comment comment=commentDao.get(cid);
		commentDao.delete(comment);
	}

	@Override
	public List<CommentBean> getCommentsByGid(String gid) {
		List<CommentBean> comments=new ArrayList<>();
		Good good=goodDao.get(gid);
		for(Comment comment: commentDao.findByGood(good)) {
			comments.add(new CommentBean(comment));
		}
		return comments;
	}

}
