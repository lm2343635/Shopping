package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Photo;

public interface PhotoDao {
	Photo get(String pid);
	String save(Photo photo);
	void update(Photo photo);
	void delete(Photo photo);
}
