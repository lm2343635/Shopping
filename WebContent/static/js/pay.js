var ono=request("ono");

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

	OrderManager.getOrderByOno(ono, function(order) {
		if(order==null) {
			location.href="urlError.html";
			return;
		}
		if(order.payed&&!order.timeout) {
			$("#has-payed").show();
			$("#pay-date").text(order.payDate.format(DATE_HOUR_MINUTE_FORMAT_CN));
		} else if(!order.payed&&!order.timeout) {
			$("#not-pay").show();
		} else if(!order.payed&&order.timeout) {
			$("#pay-timeout").show();
		}
		fillText({
			"pay-ono": order.ono,
			"pay-count": order.count,
			"pay-amount": order.amount,
		});
		$("#alipay-submit").attr("href","AlipayServlet?task=pay&ono="+ono);
		BasketManager.getBasketGoodsByOid(order.oid, function(baskets) {
			for(var i in baskets) {
				var src="static/images/noImage.jpg";
				if(baskets[i].good.cover!=null) {
					src="upload/"+baskets[i].good.category.type.tid+"/"+baskets[i].good.cover.filename;
				}
				$("#basket-list").mengular(".basket-list-template", {
					bid: baskets[i].bid,
					src: src,
					gid: baskets[i].good.gid,
					gname: baskets[i].good.gname,
					amount: baskets[i].count*baskets[i].good.price,
					price: baskets[i].good.price,
					count: baskets[i].count
				});
			}
		});
	});

});