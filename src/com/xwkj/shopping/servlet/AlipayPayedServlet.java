package com.xwkj.shopping.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alipay.service.AlipaySubmit;
import com.xwkj.common.util.DateTool;
import com.xwkj.common.util.HttpRequestUtil;
import com.xwkj.common.util.SMSService;
import com.xwkj.shopping.dao.OrderDao;
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.service.OrderManager;
import com.xwkj.shopping.service.SendeeManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

@WebServlet("/AlipayPayedServlet")
public class AlipayPayedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AlipayPayedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ManagerTemplate managerTemplate=(ManagerTemplate)context.getBean("managerTemplate");
		OrderDao orderDao=managerTemplate.getOrderDao();
		AlipaySubmit alipaySubmit=(AlipaySubmit)context.getBean("AlipaySubmit");

		String ono=request.getParameter("out_trade_no");
		String notify_id=request.getParameter("notify_id");
		String trade_no=request.getParameter("trade_no");//支付宝交易号
		String trade_status=request.getParameter("trade_status");//交易状态
		
		System.out.println("Received an message for "+ono+" with trade status "+trade_status+" at "+new Date());
		
		//支付完成更新支付信息，验证信息是否是由支付宝所发出的请求
		if(alipaySubmit.notifyVertify(notify_id)) {
			Order order=orderDao.findByOno(ono);
			switch (trade_status) {
			//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
			case "WAIT_BUYER_PAY":
				//如果支付宝交易号为空，就写入支付宝交易号
				if(order.getTrade()==null) {
					order.setTrade(trade_no);
					orderDao.update(order);
				}
				break;
			//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
			case "WAIT_SELLER_SEND_GOODS":
				System.out.println(ono+" has been payed at "+new Date());			
				//订单变更为已支付状态
				order.setPayed(true);
				order.setPayDate(new Date());
				if(order.getTrade()==null) 
					order.setTrade(trade_no);
				orderDao.update(order);
				//支付成功后、销售数量加
				for(Basket basket: managerTemplate.getBasketDao().findByOrder(order)) {
					Good good=basket.getGood();
					good.setSold(good.getSold()+basket.getCount());
					managerTemplate.getGoodDao().update(good);
				}
				//发送短息通知用户支付成功
				SMSService sms=(SMSService)context.getBean("SMSService");
				String value="#ono#="+ order.getOno()
						+ "&#payDate#="+ DateTool.formatDate(new Date(), DateTool.DATE_HOUR_MINUTE_FORMAT_CN)
						+ "&#amount#="+ order.getAmount();
				SendeeManager sendeeManager=(SendeeManager)context.getBean("sendeeManager");
				OrderManager orderManager=(OrderManager)context.getBean("orderManager");
				String url="http://"+sendeeManager.getBookingDomain()+"/UserServlet?task=getTelephone&uid="+order.getSendee().getUid();
				String telephone=HttpRequestUtil.httpRequest(url);
				sms.send(telephone, orderManager.getPaySuccessSMSTemplateID(), value);
				break;
			//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
			case "WAIT_BUYER_CONFIRM_GOODS":
				if(!order.getSend()) {
					order.setSend(true);
					order.setSendDate(new Date());
					orderDao.update(order);
					System.out.println(ono+" has been sent at "+order.getSendDate());			
				}
				break;
			//该判断表示买家已经确认收货，这笔交易完成
			case "TRADE_FINISHED":
				if(!order.getReceive()) {
					order.setReceive(true);
					order.setReceiveDate(new Date());
					orderDao.update(order);
					System.out.println(ono+" has been received at "+order.getReceiveDate());			
				}	
				break;
			default:
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
