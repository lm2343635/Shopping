package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Photo;
import com.xwkj.shopping.domain.Type;

public interface PhotoDao {
	Photo get(String pid);
	String save(Photo photo);
	void update(Photo photo);
	void delete(Photo photo);
	
	/**
	 * 根据商品查询照片
	 * @param good
	 * @return
	 */
	List<Photo> findByGood(Good good);
	
	/**
	 * 根据一级分类查询照片
	 * @param type
	 * @return
	 */
	List<Photo> findByType(Type type);
}
