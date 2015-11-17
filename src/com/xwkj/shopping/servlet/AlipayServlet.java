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
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.service.OrderManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

@WebServlet("/AlipayServlet")
public class AlipayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String task;
	
    public AlipayServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		task=request.getParameter("task");
		switch (task) {
		case "pay":
			pay(request,response);
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ono=request.getParameter("ono");
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ManagerTemplate manager=(ManagerTemplate)context.getBean("managerTemplate");
		OrderManager orderManager=(OrderManager)context.getBean("orderManager");
		Order order=manager.getOrderDao().findByOno(ono);
		Basket basket=manager.getBasketDao().findByOrder(order).get(0);
		AlipaySubmit alipaySubmit=(AlipaySubmit)context.getBean("AlipaySubmit");
		//订单描述
		String body="共"+order.getCount()+"件商品，合计"+order.getAmount()+"元";
		//超时时长
		int minutes=orderManager.getPayTimeOut()-DateTool.minutesBetween(order.getCreateDate(), new Date());
		String name=basket.getGood().getGname()+"等"+order.getCount()+"件商品";
		String sbHtml=alipaySubmit.buildSecuredTransactionsRequest(minutes, order.getOno(), name, order.getAmount(), body, order.getName(), 
				order.getAddress(), order.getZip(), order.getTelephone(), order.getTelephone());
		System.out.println(sbHtml);
		//跳转页面
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");  
		response.getWriter().print(sbHtml);
	}

}
