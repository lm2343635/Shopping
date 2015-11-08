package com.xwkj.shopping.schedule;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.xwkj.common.util.DateTool;
import com.xwkj.shopping.dao.BasketDao;
import com.xwkj.shopping.dao.GoodDao;
import com.xwkj.shopping.dao.OrderDao;
import com.xwkj.shopping.domain.Basket;
import com.xwkj.shopping.domain.Good;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.service.OrderManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

public class CloseTimeoutOrdersJob extends QuartzJobBean {

	private ManagerTemplate managerTemplate;
	private OrderManager orderManager;

	public void setManagerTemplate(ManagerTemplate managerTemplate) {
		this.managerTemplate = managerTemplate;
	}

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		OrderDao orderDao=managerTemplate.getOrderDao();
		GoodDao goodDao=managerTemplate.getGoodDao();
		BasketDao basketDao=managerTemplate.getBasketDao();
		Date time=DateTool.nextMinute(new Date(), -orderManager.getPayTimeOut());
		for(Order order: orderDao.findWillTimeoutOrders(time)) {
			System.out.println(order.getOno()+" created at "+order.getCreateDate()+ " is timeout.");
			order.setTimeout(true);
			orderDao.update(order);
			//释放存货
			for(Basket basket: basketDao.findByOrder(order)) {
				Good good=basket.getGood();
				good.setNumber(good.getNumber()+basket.getCount());
				goodDao.update(good);
			}
		}
	}

}
