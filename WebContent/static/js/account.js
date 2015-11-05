
var accountCount=0, accountAmount=0;

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
			location.href="basket.html";
			return;
		}

		BasketManager.getBasketGoodsByUid(user.uid, function(baskets) {
			for(var i in baskets) {
				accountCount+=baskets[i].count;
				accountAmount+=baskets[i].count*baskets[i].good.price;
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
	})

	//变更配送方式
	$("#sendee-express-true, #sendee-express-false").click(function() {
		$("#sendee-express-true, #sendee-express-false").removeClass("active");
		$(this).addClass("active");
	});

	//确认支付，创建订单
	$("#create-order").click(function() {
		var express=$("#sendee-express-true").hasClass("active")? true: false;
		var sname=$("#sendee-sname").val();
		var telephone=$("#sendee-telephone").val();
		var address=$("#sendee-address").val();
		var email=$("#sendee-email").val();
		var validate=true;
		if(express) {
			if(sname==""||sname==null) {
				validate=false;
				$("#sendee-sname").addClass("input-error");
			} else {
				$("#sendee-sname").removeClass("input-error");
			}
			if(telephone==""||telephone==null||!isNum(telephone)) {
				validate=false;
				$("#sendee-telephone").addClass("input-error");
			} else {
				$("#sendee-telephone").removeClass("input-error");
			}
			if(address==""||address==null) {
				validate=false;
				$("#sendee-address").addClass("input-error");
			} else {
				$("#sendee-address").removeClass("input-error");
			}
		}
		if(!validate) {
			$.messager.popup("请正确填写收货人信息！");
		} else {
			
		}
	});
});