package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Icon;

public interface IconDao {
	Icon get(String iid);
	String save(Icon icon);
	void update(Icon icon);
	void delete(Icon icon);
}
