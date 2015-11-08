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
		String ono=request.getParameter("out_trade_no");
		String notify_id=request.getParameter("notify_id");
		System.out.println(ono+" has been paeded at "+new Date());
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ManagerTemplate managerTemplate=(ManagerTemplate)context.getBean("managerTemplate");
		Order order=managerTemplate.getOrderDao().findByOno(ono);
		AlipaySubmit alipaySubmit=(AlipaySubmit)context.getBean("AlipaySubmit");
		//支付完成更新支付信息、支付宝服务器可能会发出多次请求、如果已经接受一次请求之后将屏蔽支付宝的请求
		if(alipaySubmit.notifyVertify(notify_id)&&!order.getPayed()) {
			order.setPayed(true);
			order.setPayDate(new Date());
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
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
