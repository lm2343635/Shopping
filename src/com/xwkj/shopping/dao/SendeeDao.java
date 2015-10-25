package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Sendee;

public interface SendeeDao {
	Sendee get(String sid);
	String save(Sendee sendee);
	void update(Sendee sendee);
	void delete(Sendee sendee);
}
