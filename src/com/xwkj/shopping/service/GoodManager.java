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
	 * 增加商品数量
	 * @param gid 要增加的商品id
	 * @param dnumber 增加数量
	 * @return
	 */
	int addGoodNumber(String gid, int dnumber);
	
	/**
	 * 删除商品
	 * @param gid
	 * @return
	 */
	boolean removeGood(String gid);
	
	/**
	 * 获取二级分类下的商品
	 * @param cid
	 * @param enable
	 * @return
	 */
	List<GoodBean> getGoodsByCid(String cid, boolean enable);
	
	/**
	 * 得到最新的limit个一级分类的商品
	 * @param tid 一级分类id
	 * @param limit 限制个数
	 * @return
	 */
	List<GoodBean> getGoodsByTidWithLimit(String tid, int limit);
	
	/**
	 * 得到销量最好的limit个商品
	 * @param limit
	 * @return
	 */
	List<GoodBean> getHotestGoodsWithLimit(int limit);
	
	/**
	 * 得到最新的limit个商品
	 * @param limit
	 * @return
	 */
	List<GoodBean> getNewestGoodsWithLimit(int limit);
	
	/**
	 * 根据商品名称搜索商品
	 * @param gname
	 * @return
	 */
	List<GoodBean> searchGoods(String gname);
	
	/**
	 * 更新创建时间
	 * @param tid
	 */
	void updateCreateDate(String gid);
	
	/**
	 * 更改可用状态
	 * @param gid
	 * @param enable
	 */
	void enable(String gid, boolean enable);
}
