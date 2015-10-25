package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Category;

public interface CategoryDao {
	Category get(String cid);
	String save(Category category);
	void update(Category category);
	void delete(Category category);
}
