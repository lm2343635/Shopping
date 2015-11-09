package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Category;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Type;

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
	
	/**
	 * 查询最新的limit个一级分类的商品
	 * @param type
	 * @param limit
	 * @return
	 */
	List<Good> findByTypeWithLimit(Type type, int limit);
	
	/**
	 * 查询销量最好的limit个商品
	 * @param limit
	 * @return
	 */
	List<Good> findHotestWithLimit(int limit);
	
	/**
	 * 根据商品名称查询
	 * @param gname
	 * @return
	 */
	List<Good> findByGname(String gname);
	
	/**
	 * 查询最新的limit个商品
	 * @param limit
	 * @return
	 */
	List<Good> findNewstWithLimit(int limit);
}
