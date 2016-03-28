package com.xwkj.shopping.servlet;

import java.io.IOException;
import java.text.DecimalFormat;
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
		SendeeManager sendeeManager=(SendeeManager)context.getBean("sendeeManager");
		OrderManager orderManager=(OrderManager)context.getBean("orderManager");
		SMSService sms=(SMSService)context.getBean("SMSService");
		
		AlipaySubmit alipaySubmit=(AlipaySubmit)context.getBean("AlipaySubmit");
		String ono=request.getParameter("out_trade_no");
		String notify_id=request.getParameter("notify_id");
		String trade_no=request.getParameter("trade_no");//支付宝交易号
		String trade_status=request.getParameter("trade_status");//交易状态
		String refund_status=request.getParameter("refund_status");//退款状态
		
		System.out.println("Received an message for "+ono+" with trade status "+trade_status+" at "+new Date());
		
		//支付完成更新支付信息，验证信息是否是由支付宝所发出的请求
		if(alipaySubmit.notifyVertify(notify_id)) {
			Order order=orderDao.findByOno(ono);
			String url="http://"+sendeeManager.getBookingDomain()+"/UserServlet?task=getTelephone&uid="+order.getSendee().getUid();
			String telephone=HttpRequestUtil.httpRequest(url);
			//交易状态
			switch (trade_status) {
			//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
			case "WAIT_BUYER_PAY":
				//如果支付宝交易号为空，就写入支付宝交易号
				if(order.getTrade()==null) {
					order.setTrade(trade_no);
					orderDao.update(order);
					response.getWriter().println("success");
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
				//发送短息通知用户和管理员支付成功
				String value="#ono#="+ order.getOno()
						+ "&#payDate#="+ DateTool.formatDate(new Date(), DateTool.DATE_HOUR_MINUTE_FORMAT_CN)
						+ "&#amount#="+ (new DecimalFormat("#.00").format(order.getAmount()));
				sms.send(telephone, orderManager.getPaySuccessSMSTemplateID(), value);
				sms.send(orderManager.getAdminTelephone(), orderManager.getPaySuccessSMSTemplateID(), value);
				response.getWriter().println("success");
				break;
			//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
			case "WAIT_BUYER_CONFIRM_GOODS":
				if(!order.getSend()) {
					order.setSend(true);
					order.setSendDate(new Date());
					orderDao.update(order);
					System.out.println(ono+" has been sent at "+order.getSendDate());			
					response.getWriter().println("success");
				}
				break;
			//该判断表示买家已经确认收货，这笔交易完成
			case "TRADE_FINISHED":
				if(!order.getReceive()) {
					order.setReceive(true);
					order.setReceiveDate(new Date());
					orderDao.update(order);
					System.out.println(ono+" has been received at "+order.getReceiveDate());			
					response.getWriter().println("success");
				}	
				break;
			//交易中途关闭（已结束，未成功完成）
			case "TRADE_CLOSED":
				
				break;
			default:
				break;
			}
			//退款状态
			switch (refund_status) {
			//退款协议等待卖家确认中
			case "WAIT_SELLER_AGREE":
				order.setTimeout(true);
				order.setReturnDate(new Date());
				orderDao.update(order);
				System.out.println();			
				response.getWriter().println("success");
				break;
			//卖家不同意协议，等待买家修改
			case "SELLER_REFUSE_BUYER":
				order.setTimeout(false);
				orderDao.update(order);
				//发送短息通知用户退款失败
				sms.send(telephone, orderManager.getRefundFailedSMSTemplateID(), "#ono#="+ order.getOno());
				System.out.println(ono+"'s refund was rejected at "+(new Date()));
				response.getWriter().println("success");
				break;
			//退款协议达成，等待买家退货
			case "WAIT_BUYER_RETURN_GOODS":
				//此处通知买家发货给卖家
				//您的订单#ono#退款已被受理，请将商品邮寄至指定地址（点击我的订单->退款查询登录支付宝页面查看指定退货地址），收到退货商品后，将会再次与您联系。详情咨询九资游客服：0713-5077888。
				break;
			//等待卖家收货
			case "WAIT_SELLER_CONFIRM_GOODS":
				//通知买家发货成功
				break;
			//退款成功
			case "REFUND_SUCCESS":
				order.setPayed(false);
				order.setSend(false);
				orderDao.update(order);
				System.out.println(ono+" has refunded at "+order.getReceiveDate());			
				//发送短息通知用户退款成功
				String value="#ono#="+ order.getOno()
					+"&#date#="+ DateTool.formatDate(new Date(), DateTool.DATE_HOUR_MINUTE_FORMAT_CN);
				sms.send(telephone, orderManager.getRefundSuccessSMSTemplateID(), value);
				System.out.println(ono+"'s refund was rejected at "+(new Date()));
				response.getWriter().println("success");
				break;
			//退款关闭
			case "REFUND_CLOSED":
				
				break;
			default:
				break;
			}
		} else {
			response.getWriter().println("fail");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
