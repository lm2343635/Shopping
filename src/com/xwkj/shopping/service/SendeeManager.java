package com.xwkj.shopping.service;

import javax.servlet.http.HttpSession;

import com.xwkj.shopping.bean.UserBean;

public interface SendeeManager {
	public static final String USER_FLAG="e4e681eb50bd2fd60150bd30d82b0000";

	/**
	 * 用户登录
	 * @param telephone
	 * @param password
	 * @param session
	 * @return
	 */
	boolean login(String telephone, String password, HttpSession session);
	
	/**
	 * 检查用户session
	 * @param session
	 * @return
	 */
	UserBean checkSession(HttpSession session);
	
	/**
	 * 退出登录
	 * @param session
	 */
	void logout(HttpSession session);
}
