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
	
	$("#head").load("head.html");
	$("#foot").load("foot.html");

	
	checkSession(function(user) {
		if(user==null) {
			$("#no-order").show();
			return;
		}

		OrderManager.getOrdersByUid(user.uid, function(orders) {
			if(orders.length==0) {
				$("#no-order").show();
				return;
			}
			for(var i in orders) {
				var template;
				if(orders[i].payed&&!orders[i].timeout&&!orders[i].send) {
					template=".order-payed-template";
				} else if (!orders[i].payed&&!orders[i].timeout&&!orders[i].send) {
					template=".order-wait-template";
				} else if(!orders[i].payed&&orders[i].timeout&&!orders[i].send) {
					template=".order-timeout-template";
				} else if(orders[i].payed&&!orders[i].timeout&&orders[i].send) {
					template=".order-sending-template";
				}
				$("#order-list").mengular(template, {
					oid: orders[i].oid,
					ono: orders[i].ono,
					count: orders[i].count,
					amount: orders[i].amount,
					createDate: orders[i].createDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					payDate: orders[i].payDate==null? null: orders[i].payDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					sendDate: orders[i].sendDate==null? null: orders[i].sendDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					logistics: orders[i].logistics
				});

				//删除订单
				$("#"+orders[i].oid+" .order-delete").click(function() {
					var oid=$(this).parent().attr("id");
					var ono=$("#"+oid+" .order-ono").val();
					$.messager.confirm("提示", "确认删除订单："+ono+"吗？", function() {
						OrderManager.removeOrder(oid, function(success) {
							if(success)  {
								$("#"+oid).remove();
							}
						});
					});
				});
			}
		});
	})
});