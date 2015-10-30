package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Type;

public interface TypeDao {
	Type get(String tid);
	String save(Type type);
	void update(Type type);
	void delete(Type type);
	
	List<Type> findAll();
}
