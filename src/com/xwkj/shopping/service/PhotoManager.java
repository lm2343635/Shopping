package com.xwkj.shopping.service;

import java.util.List;

import com.xwkj.shopping.bean.PhotoBean;

public interface PhotoManager {
	
	/**
	 * 删除照片
	 * @param pid
	 */
	void removePhoto(String pid);
	
	/**
	 * 设置为商品封面
	 * @param pid
	 */
	void setAsGoodCover(String pid);

	/**
	 * 获取商品的照片
	 * @param gid
	 * @return
	 */
	List<PhotoBean> getPhotosByGid(String gid);
}
