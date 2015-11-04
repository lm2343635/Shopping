package com.xwkj.shopping.dao;

import com.xwkj.shopping.domain.Sendee;

public interface SendeeDao {
	Sendee get(String sid);
	String save(Sendee sendee);
	void update(Sendee sendee);
	void delete(Sendee sendee);
	
	/**
	 * 根据用户id查询收货人
	 * @param uid
	 * @return
	 */
	Sendee findByUid(String uid);
}
