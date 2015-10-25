package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Good;

public interface GoodDao {
	Good get(String gid);
	String save(Good good);
	void update(Good good);
	void delete(Good good);
}
