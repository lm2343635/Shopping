package com.xwkj.shopping.service;

import java.util.List;

import com.xwkj.shopping.bean.CategoryBean;

public interface CategoryManager {
	
	/**
	 * 获取二级分类
	 * @param cid
	 * @return
	 */
	CategoryBean getCategory(String cid);
	
	/**
	 * 增加二级分类
	 * @param tid
	 * @param cname
	 * @return
	 */
	String addCategory(String tid, String cname);
	
	/**
	 * 修改二级分类
	 * @param cid
	 * @param cname
	 */
	void modifyCategory(String cid, String cname);
	
	/**
	 * 删除二级分类
	 * @param cid
	 * @return
	 */
	boolean removeCategory(String cid);
	
	/**
	 * 获取指定一级分类下的所有二级分类
	 * @param tid
	 * @return
	 */
	List<CategoryBean> getCategoriesByTid(String tid, boolean enable);
	
	/**
	 * 更新创建时间
	 * @param tid
	 */
	void updateCreateDate(String cid);
	
	/**
	 * 更改可用状态
	 * @param gid
	 * @param enable
	 */
	void enable(String cid, boolean enable);
}
