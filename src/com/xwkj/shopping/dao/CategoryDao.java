package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Type;

public interface CategoryDao {
	Category get(String cid);
	String save(Category category);
	void update(Category category);
	void delete(Category category);
	
	/**
	 * 查询一级分类下的所有二级分类
	 * @param type
	 * @param enable 可用性
	 * @return
	 */
	List<Category> findByType(Type type, boolean enable);
}
