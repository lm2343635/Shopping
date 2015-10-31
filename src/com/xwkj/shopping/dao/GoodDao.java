package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Good;

public interface GoodDao {
	Good get(String gid);
	String save(Good good);
	void update(Good good);
	void delete(Good good);
	
	/**
	 * 根据二级分类查找商品
	 * @param category
	 * @return
	 */
	List<Good> findByCategory(Category category);
}
