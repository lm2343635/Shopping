package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Type;

public interface TypeDao {
	Type get(String tid);
	String save(Type type);
	void update(Type type);
	void delete(Type type);
	
	/**
	 * 查找所有一级分类
	 * @param enable 可用性
	 * @return
	 */
	List<Type> findAll(boolean enable);
}
