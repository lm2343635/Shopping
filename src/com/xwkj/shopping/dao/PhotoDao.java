package com.xwkj.shopping.dao;

import java.util.List;

import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Photo;

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
}
