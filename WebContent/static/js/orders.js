var ORDER_TYPE_NAME=["待发货", "已发货", "已完成", "待退款", "待付款", "未付款"];
var TIME_NAME=["付款时间", "发货时间", "收货时间", "退款申请时间", "订单创建时间", "订单创建时间"];
var pageSize=15;
var payed, timeout, send, receive;
var type=0;
var sendingOid, sendingExpress;

$(document).ready(function() {

	$.messager.model = {
		ok:{ 
			text: "确定", 
			classed: "btn-danger" 
		},
		cancel: { 
			text: "取消", 
			classed: "btn-default" 
		}
	};
	
	checkAdminSession(function(){
		searchOrders("", 1);
	});
	
	//加载每页显示条数的下拉菜单
	for(var i=1;i<=100;i++) {
		var option=$("<option>").val(i).text(i);
		if(pageSize==i)
			option.attr("selected","selected");
		$("#page-size").append(option);
	}

	//更改每页显示的条数
	$("#page-size").change(function() {
		pageSize=$(this).val();
		var ono=$("#search-ono").val();
		searchOrders(ono, 1);
	});
	
	//更换订单种类
	$("#order-type button").click(function() {
		$("#order-type button").removeClass("active");
		$(this).addClass("active");
		type=$(this).attr("data-index");
		fillText({
			"order-type-name": ORDER_TYPE_NAME[type],
			"time-name": TIME_NAME[type]
		});
		searchOrders("", 1);
	});

	//订单发货
	$("#order-send-submit").click(function() {
		var company=$("#order-send-logistics-company").val();
		var no=$("#order-send-logistics-no").val();
		var validate=true;
		if(sendingExpress) {
			if(company==null||company=="") {
				validate=false;
				$("#order-send-logistics-company").parent().addClass("has-error");
			} else {
				$("#order-send-logistics-company").parent().removeClass("has-error");
			}
			if(no==null||no=="") {
				validate=false;
				$("#order-send-logistics-no").parent().addClass("has-error");
			} else {
				$("#order-send-logistics-no").parent().removeClass("has-error");
			}
		} else {
			company="用户自提";
			no="";
		}
		if(validate) {
			$(this).text("正在发货").attr("disabled", "disabled");
			$.post("AlipayServlet", {
				task: "send",
				oid: sendingOid,
				logistics_name: company,
				invoice_no: no
			}, function(data) {
				if(data!=null) {
					$("#"+sendingOid).remove();
					$("#order-send-submit").text("确认发货").removeAttr("disabled");
					$("#order-send-modal").modal("hide");
					OrderManager.savetLogistics(sendingOid, company+no);
				}
			});
		}
	});

	$("#order-send-modal").on("hidden.bs.modal", function() {
		$("#order-send-logistics").val("");
	});
});


function searchOrders(ono, page) {
	if (type==0) {
		payed=true;
		timeout=send=receive=false;
	} else if(type==1) {
		payed=send=true;
		timeout=receive=false;
	} else if(type==2) {
		payed=send=receive=true;
		timeout=false;
	} else if(type==3) {
		payed=timeout=true;
		send=receive=false;
	} else if(type==4) {
		payed=send=receive=timeout=false;
	} else if(type==5) {
		timeout=true;
		payed=send=receive=false;
	}

	//加载页码
	OrderManager.getOrdersCount(payed, timeout, send, receive, ono, function(count) {
		$("#page-count").text(count);
		$("#page-nav ul").empty();
		for(var i=1; i<Math.ceil(count/pageSize+1);i++) {
			var li='<li><a href="javascript:void(0)">'+i+'</a></li>';
			if(page==i)
				li='<li class="active"><a href="javascript:void(0)">'+i+'</a></li>';
			$("#page-nav ul").append(li);
		}
		$("#page-nav ul li").each(function(index) {
			$(this).click(function() {
				searchOrders(1, index+1); 
			});
		});
	});
	
	//加载订单
	OrderManager.searchOrders(payed, timeout, send, receive, ono, page, pageSize, function(orders) {
		$("#order-list tbody").mengularClear();
		for(var i in orders) {
			var date;
			var task="无操作";
			if (type==0) { //待发货
				date=orders[i].payDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT);
				task="发货";
			} else if(type==1) { //已发货
				date=orders[i].sendDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT);
			} else if(type==2) { //已完成
				date=orders[i].receiveDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT);
			} else if(type==3) { //待退款
				date=orders[i].returnDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT);
				task="退款";
			} else if(type==4) { //待付款
				date=orders[i].createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT);
			} else if(type==5) { //未付款
				date=orders[i].createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT);
			}
			$("#order-list tbody").mengular(".order-payed-template", {
				oid: orders[i].oid,
				ono: orders[i].ono,
				date: date,
				amount: orders[i].amount,
				name: orders[i].name==null? "无": orders[i].name,
				express: orders[i].express? "快递包邮": "用户自提",
				task: task
			});

			//查看订单详情
			$("#"+orders[i].oid+" .order-list-info").click(function() {
				var oid=$(this).parent().attr("id");
				//获取订单信息
				OrderManager.getOrder(oid, function(order) {
					fillText({
						"order-info-ono": order.ono,
						"order-info-createDate": order.createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
						"order-info-amount": order.amount,
						"order-info-count": order.count,
						"order-info-payed": order.payed? "已支付": "未支付",
						"order-info-payDate": order.payDate==null? "无": order.payDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
						"order-info-timeout": order.timeout? "已超时，订单已关闭": "未超时",
						"order-info-express": order.express? "快递包邮": "用户自提",
						"order-info-name": order.name==null? "无": order.name,
						"order-info-telephone": order.telephone==null? "无": order.telephone,
						"order-info-address": order.address==null? "无": order.address,
						"order-info-zip": order.zip==null? "无": order.zip,
						"order-info-email": order.email==null? "无": order.email,
						"order-info-trade": order.trade==null? "无": order.trade,
						"order-info-send": order.send? "已发货": "未发货",
						"order-info-sendDate": order.sendDate==null? "无": order.sendDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
						"order-info-logistics": order.logistics==null? "无": order.logistics,
						"order-info-receive": order.receive? "已收货，订单交易成功": "未收货",
						"order-info-receiveDate": order.receiveDate==null? "无": order.receiveDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT)
					});
				});

				//获取订单商品
				setOrderGoods(oid, "#order-info-goods", ".order-info-goods-template");

				$("#order-info-modal").modal("show");
			});

			//操作订单
			$("#"+orders[i].oid+" .order-list-task button").click(function() {
				var oid=$(this).parent().parent().attr("id");
				//发货操作
				if(type==0) {
					//获取订单信息
					OrderManager.getOrder(oid, function(order) {
						fillText({
							"order-send-ono": order.ono,
							"order-send-createDate": order.createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
							"order-send-amount": order.amount,
							"order-send-count": order.count,
							"order-send-express": order.express? "快递包邮": "用户自提",
							"order-send-name": order.name==null? "无": order.name,
							"order-send-telephone": order.telephone==null? "无": order.telephone,
							"order-send-address": order.address==null? "无": order.address,
							"order-send-zip": order.zip==null? "无": order.zip,
							"order-send-email": order.email==null? "无": order.email
						});

						sendingOid=order.oid;
						sendingExpress=order.express;
						if(sendingExpress) {
							$("#order-send-logistics").show();
						} else {
							$("#order-send-logistics").hide();
						}
					});

					//获取订单商品
					setOrderGoods(oid, "#order-send-goods", ".order-send-goods-template");

					$("#order-send-modal").modal("show");
				} 
				//退款操作
				else if(type==3) {
					$("#order-return-modal").modal("show");
				}
				//无操作
				else {
					$.messager.popup("无操作");
				}
			});

			//删除订单
			$("#"+orders[i].oid+" .order-list-delete").click(function() {
				var oid=$(this).parent().attr("id");
				var ono=$("#"+oid+" .order-list-ono").text();
				if(type!=5) {
					$.messager.popup("只能删除未付款的订单");
					return;
				}
				$.messager.confirm("提示", "确认删除订单："+ono+"吗？", function() {

				});
			});

		}
		
	});
}

/**
 * 加载订单商品
 * @param oid 订单id
 * @param selector div选择器
 * @param template mengluar模板
 */
function setOrderGoods(oid, selector, template) {
	BasketManager.getBasketGoodsByOid(oid, function(baskets) {
		$(selector).mengularClear();
		for(var i in baskets) {
			var src="static/images/noImage.jpg";
			if(baskets[i].good.cover!=null) {
				src="upload/"+baskets[i].good.category.type.tid+"/"+baskets[i].good.cover.filename;
			}
			$(selector).mengular(template, {
				bid: baskets[i].bid,
				gid: baskets[i].good.gid,
				src: src,
				gname: baskets[i].good.gname,
				count: baskets[i].count
			});
		}
	});
}