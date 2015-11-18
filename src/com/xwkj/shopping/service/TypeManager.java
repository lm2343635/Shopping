package com.xwkj.shopping.service;

import java.util.List;

import com.xwkj.shopping.bean.TypeBean;

public interface TypeManager {
	
	/**
	 * 获取一级分类
	 * @param tid
	 * @return
	 */
	TypeBean getType(String tid);
	
	/**
	 * 增加一级分类
	 * @param tname
	 * @return
	 */
	String addType(String tname);
	
	/**
	 * 修改一级分类
	 * @param tid
	 * @param tname
	 */
	void modifyType(String tid, String tname);
	
	/**
	 * 删除一级分类
	 * @param tid
	 * @return
	 */
	boolean deleteType(String tid);
	
	/**
	 * 获取所有一级分类
	 * @param enable 可用性
	 * @return
	 */
	List<TypeBean> getAll(boolean enable);

	/**
	 * 更新创建时间
	 * @param tid
	 */
	void updateCreateDate(String tid);
	
	/**
	 * 更改可用状态
	 * @param gid
	 * @param enable
	 */
	void enable(String cid, boolean enable);
}
