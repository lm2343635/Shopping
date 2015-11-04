package com.xwkj.shopping.service.impl;

import javax.servlet.http.HttpSession;

import com.xwkj.common.util.HttpRequestUtil;
import com.xwkj.shopping.bean.UserBean;
import com.xwkj.shopping.domain.Sendee;
import com.xwkj.shopping.service.SendeeManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class SendeeManagerImpl extends ManagerTemplate implements SendeeManager {
	
	private String BookingDomain;

	public String getBookingDomain() {
		return BookingDomain;
	}

	public void setBookingDomain(String bookingDomain) {
		BookingDomain = bookingDomain;
	}

	@Override
	public boolean login(String telephone, String password, HttpSession session) {
		String url="http://"+BookingDomain+"/UserServlet?task=login&telephone="+telephone+"&password="+password;
		JSONObject data=JSONObject.fromObject(HttpRequestUtil.httpRequest(url));
		if(!data.getBoolean("exsit")) 
			return false;
		if(!data.getBoolean("success")) 
			return false;
		JSONObject jsonObject=data.getJSONObject("user");
		UserBean userBean=(UserBean)JSONObject.toBean(jsonObject, new UserBean(), new JsonConfig());
		session.setAttribute(USER_FLAG, userBean);
		//根据用户信息创建收货人
		Sendee sendee=sendeeDao.findByUid(userBean.getUid());
		if(sendee==null) {
			sendee=new Sendee();
			sendee.setUid(userBean.getUid());
			sendeeDao.save(sendee);
		}
		return true;
	}

	@Override
	public UserBean checkSession(HttpSession session) {
		if(session.getAttribute(SendeeManager.USER_FLAG)==null)
			return null;
		return (UserBean)session.getAttribute(SendeeManager.USER_FLAG);
	}

	@Override
	public void logout(HttpSession session) {
		session.removeAttribute(USER_FLAG);
	}

}
