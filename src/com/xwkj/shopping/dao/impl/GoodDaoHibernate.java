package com.xwkj.shopping.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xwkj.common.hibernate3.support.PageHibernateDaoSupport;
import com.xwkj.shopping.dao.GoodDao;
import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Type;

public class GoodDaoHibernate extends PageHibernateDaoSupport implements GoodDao {

	@Override
	public Good get(String gid) {
		return getHibernateTemplate().get(Good.class, gid);
	}

	@Override
	public String save(Good good) {
		return (String)getHibernateTemplate().save(good);
	}

	@Override
	public void update(Good good) {
		getHibernateTemplate().update(good);
	}

	@Override
	public void delete(Good good) {
		getHibernateTemplate().delete(good);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Good> findByCategory(Category category) {
		return getHibernateTemplate().find("from Good where category=? order by createDate desc", category);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Good> findNewstWithLimit(int limit) {
		String hql="from Good order by createDate desc";
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Good>>() {
			@Override
			public List<Good> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Good> findByTypeWithLimit(Type type, int limit) {
		String hql="from Good where category.type=? order by createDate desc";
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Good>>() {
			@Override
			public List<Good> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setParameter(0, type);
				query.setFirstResult(0);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Good> findHotestWithLimit(int limit) {
		String hql="from Good order by sold desc";
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Good>>() {
			@Override
			public List<Good> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Good> findByGname(String gname) {
		String hql="from Good where gname like ? order by sold, createDate desc";
		return getHibernateTemplate().find(hql, "%"+gname+"%");
	}

}
