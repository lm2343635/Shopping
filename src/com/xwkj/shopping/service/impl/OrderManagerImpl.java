package com.xwkj.shopping.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.WebContextFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xwkj.common.util.DateTool;
import com.xwkj.common.util.MathTool;
import com.xwkj.common.util.SMSService;
import com.xwkj.shopping.bean.OrderBean;
import com.xwkj.shopping.bean.UserBean;
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.domain.Sendee;
import com.xwkj.shopping.service.OrderManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

import net.sf.json.JSONObject;

public class OrderManagerImpl extends ManagerTemplate implements OrderManager {
	//快递包邮
	private String ExpressTure;
	//用户自提
	private String ExpressFalse;
	//支付超时时长
	private int PayTimeOut;
	//订房成功发送短信模板id
	private int OrderSuccessSMSTemplateID;
	//付款成功发送短信模板id
	private int PaySuccessSMSTemplateID;

	public String getExpressTure() {
		return ExpressTure;
	}

	public String getExpressFalse() {
		return ExpressFalse;
	}

	public int getPayTimeOut() {
		return PayTimeOut;
	}

	public int getOrderSuccessSMSTemplateID() {
		return OrderSuccessSMSTemplateID;
	}

	public void setExpressTure(String expressTure) {
		ExpressTure = expressTure;
	}

	public void setExpressFalse(String expressFalse) {
		ExpressFalse = expressFalse;
	}

	public void setPayTimeOut(int payTimeOut) {
		PayTimeOut = payTimeOut;
	}

	public void setOrderSuccessSMSTemplateID(int orderSuccessSMSTemplateID) {
		OrderSuccessSMSTemplateID = orderSuccessSMSTemplateID;
	}

	public int getPaySuccessSMSTemplateID() {
		return PaySuccessSMSTemplateID;
	}

	public void setPaySuccessSMSTemplateID(int paySuccessSMSTemplateID) {
		PaySuccessSMSTemplateID = paySuccessSMSTemplateID;
	}

	@Override
	public Map<String, Object> addOrder(UserBean user, boolean express, String name, String telephone, String address, String zip, String email) {
		Map<String, Object> data=new HashMap<>();
		Sendee sendee=sendeeDao.findByUid(user.getUid());
		List<Basket> baskets=basketDao.findUnorderdBySendee(sendee);
		//购物车中没有商品，不让创建订单
		if(baskets.size()==0) {
			data.put("empty", true);
			return data;
		}
		int count=0;
		double amount=0;
		for(Basket basket: baskets) {
			//某件商品的购买量大于供货量，不让创建订单
			if(basket.getCount()>basket.getGood().getNumber()) {
				data.put("empty", false);
				data.put("availability", false);
				return data;
			}
			count+=basket.getCount();
			amount+=basket.getCount()*basket.getGood().getPrice();
		}
		//占有存货
		for(Basket basket: baskets) {
			Good good=basket.getGood();
			good.setNumber(good.getNumber()-basket.getCount());
			goodDao.update(good);
		}
		Order order=new Order();
		order.setOno("S"+DateTool.formatDate(new Date(), "yyyyMMddHHmmss")+MathTool.getRandomStr(6));
		order.setCreateDate(new Date());
		order.setExpress(express);
		order.setCount(count);
		order.setAmount((new BigDecimal(amount)).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
		order.setPayed(false);
		order.setTimeout(false);
		order.setSend(false);
		order.setReceive(false);
		if(express) {
			order.setName(name);
			order.setTelephone(telephone);
			order.setAddress(address);
			order.setZip(zip);
			sendee.setSname(name);
			sendee.setTelephone(telephone);
			sendee.setZip(zip);
			sendee.setAddress(address);
			sendeeDao.update(sendee);
		}
		if(email!=null&&!email.equals("")) {
			order.setEmail(email);
			sendee.setEmail(email);
			sendeeDao.update(sendee);
		}
		order.setSendee(sendee);
		//生成订单
		String oid=orderDao.save(order);
		data.put("empty", false);
		data.put("availability", true);
		data.put("oid", oid);
		data.put("ono", order.getOno());
		for(Basket basket: baskets) {
			basket.setOrder(order);
			basketDao.update(basket);
		}
		SMSService sms=(SMSService)WebApplicationContextUtils.getWebApplicationContext(WebContextFactory.get().getServletContext()).getBean("SMSService");
		String value="#name#="+ user.getUname()
				+ "&#ono#="+ order.getOno()
				+ "&#goods#="+ baskets.size()
				+ "&#count#="+ order.getCount()
				+ "&#amount#="+ order.getAmount()
				+ "&#express#="+ (express? ExpressTure: ExpressFalse)
				+ "&#sendee#="+ (express? name+"，"+telephone+"，"+address: " ")
				+ "&#hour#="+ (PayTimeOut/60);
		JSONObject result=sms.send(user.getTelephone(), OrderSuccessSMSTemplateID, value);
		if(Integer.parseInt(result.get("error_code").toString())==0) {
			data.put("sms", true);
		} else {
			data.put("sms", false);
			System.out.println(result.get("reason"));
		}
		return data;
	}

	@Override
	public OrderBean getOrder(String oid) {
		Order order=orderDao.get(oid);
		if(order==null)
			return null;
		return new OrderBean(order);
	}

	@Override
	public OrderBean getOrderByOno(String ono) {
		Order order=orderDao.findByOno(ono);
		if(order==null)
			return null;
		return new OrderBean(order);
	}

	@Override
	public boolean removeOrder(String oid) {
		Order order=orderDao.get(oid);
		if(order.getPayed())
			return false;
		for(Basket basket: basketDao.findByOrder(order)) {
			//释放存货
			Good good=basket.getGood();
			good.setNumber(good.getNumber()+basket.getCount());
			goodDao.update(good);
			//删除购物车
			basketDao.delete(basket);
		}
		//删除订单
		orderDao.delete(order);
		return true;
	}

	@Override
	public List<OrderBean> getOrdersByUid(String uid) {
		List<OrderBean> orders=new ArrayList<>();
		Sendee sendee=sendeeDao.findByUid(uid);
		for(Order order: orderDao.findBySendee(sendee))
			orders.add(new OrderBean(order));
		return orders;
	}

	@Override
	public int getOrdersCount(boolean payed, boolean timeout, boolean send, boolean receive, String ono) {
		return orderDao.getOrdersCount(payed, timeout, send, receive, ono);
	}

	@Override
	public List<OrderBean> searchOrders(boolean payed, boolean timeout, boolean send, boolean receive, String ono, int page, int pageSize) {
		List<OrderBean> orders=new ArrayList<>();
		int offset=(page-1)*pageSize;
		for(Order order: orderDao.findOrders(payed, timeout, send, receive, ono, offset, pageSize)) {
			orders.add(new OrderBean(order));
		}
		return orders;
	}

	@Override
	public void savetLogistics(String oid, String logistics) {
		Order order=orderDao.get(oid);
		if(order.getExpress()==true) {
			order.setLogistics(logistics);
		}
		orderDao.update(order);
	}
	
}