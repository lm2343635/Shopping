package com.xwkj.shopping.service;

import java.util.List;

import com.xwkj.shopping.bean.GoodBean;

public interface GoodManager {
	
	/**
	 * 获取商品
	 * @param gid
	 * @return
	 */
	GoodBean getGood(String gid);
	
	/**
	 * 新增商品
	 * @param cid
	 * @param gname
	 * @param price
	 * @param number
	 * @param descriptor
	 * @return
	 */
	String addGood(String cid, String gname, double price, int number, String descriptor);
	
	/**
	 * 修改商品
	 * @param gid
	 * @param gname
	 * @param price
	 * @param number
	 * @param descriptor
	 */
	void modifyGood(String gid, String gname, double price, int number, String descriptor);
	
	/**
	 * 删除商品
	 * @param gid
	 * @return
	 */
	boolean removeGood(String gid);
	
	/**
	 * 获取二级分类下的商品
	 * @param cid
	 * @return
	 */
	List<GoodBean> getGoodsByCid(String cid);
	
	/**
	 * 得到最新的limit个一级分类的商品
	 * @param tid 一级分类id
	 * @param limit 限制个数
	 * @return
	 */
	List<GoodBean> getGoodsByTidWithLimit(String tid, int limit);
	
}
