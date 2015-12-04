package com.xwkj.shopping.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.CommentDao;
import com.xwkj.shopping.domain.Comment;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;

public class CommentDaoHibernate extends PageHibernateDaoSupport implements CommentDao {

	@Override
	public Comment get(String cid) {
		return getHibernateTemplate().get(Comment.class, cid);
	}

	@Override
	public String save(Comment comment) {
		return (String)getHibernateTemplate().save(comment);
	}

	@Override
	public void update(Comment comment) {
		getHibernateTemplate().update(comment);
	}

	@Override
	public void delete(Comment comment) {
		getHibernateTemplate().delete(comment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByOrder(Order order) {
		String hql="from Comment where order=? order by commentDate desc";
		return getHibernateTemplate().find(hql, order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByGood(Good good) {
		String hql="from Comment where enable=true and good=? order by commentDate desc";
		return getHibernateTemplate().find(hql, good);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comment findByOrderAndGood(Order order, Good good) {
		String hql="from Comment where order=? and good=?";
		List<Comment> comments=getHibernateTemplate().find(hql, order, good);
		if(comments.size()==0)
			return null;
		return comments.get(0);
	}

	@Override
	public int getCommentsCount(Date start, Date end, String gname, boolean showAll, boolean enable) {
		String hql="select count(*) from Comment where content!=null ";
		List<Object> objects=new ArrayList<>();
		if(start!=null) {
			hql+=" and commentDate>=?";
			objects.add(start);
		}
		if(end!=null) {
			hql+=" and commentDate<=?";
			objects.add(end);
		}
		if(gname!=null&&!gname.equals("")) {
			hql+=" and good.gname like ?";
			objects.add("%"+gname+"%");
		}
		if(!showAll) {
			hql+=" and enable=?";
			objects.add(enable);
		}
		final String _hql=hql;
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query=session.createQuery(_hql);
				for(int i=0; i< objects.size(); i++)
					query.setParameter(i, objects.get(i));
				return (long)query.uniqueResult();
			}
		}).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByPage(Date start, Date end, String gname, boolean showAll, boolean enable, int offset, int pageSize) {
		String hql="from Comment where content!=null ";
		List<Object> objects=new ArrayList<>();
		if(start!=null) {
			hql+=" and commentDate>=?";
			objects.add(start);
		}
		if(end!=null) {
			hql+=" and commentDate<=?";
			objects.add(end);
		}
		if(gname!=null&&!gname.equals("")) {
			hql+=" and good.gname like ?";
			objects.add("%"+gname+"%");
		}
		if(!showAll) {
			hql+=" and enable=?";
			objects.add(enable);
		}
		hql+=" order by commentDate desc";
		Object [] objs=new Object[objects.size()];
		for(int i=0; i<objects.size(); i++)
			objs[i]=objects.get(i);
		return findByPage(hql, objs, offset, pageSize);
	}

}
