var _user;
var goods, accountCount=0, accountAmount=0;

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
		_user=user;

		BasketManager.getBasketGoodsByUid(user.uid, function(baskets) {
			if(baskets.length==0) {
				location.href="basket.html";
				return;
			}
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
			goods=baskets.length;
			fillText({
				"account-goods": goods,
				"account-count": accountCount,
				"account-amount": accountAmount
			});
		});

		SendeeManager.getSendeeByUid(user.uid, function(sendee) {
			fillValue({
				"sendee-sname": sendee.sname,
				"sendee-telephone": sendee.telephone,
				"sendee-address": sendee.address,
				"sendee-email": sendee.email
			});
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
			var message="订单信息<br>"+
				"共"+goods+"种商品， "+accountCount+"件， 合计￥"+accountAmount+"元<br><br>"+
				"配送信息<br>"+
				"配送方式："+ (express? "快递包邮": "用户自取")+"<br>";
			if(express) {
				message+="收货人姓名："+sname+"<br>"+
					"收货人电话："+telephone+"<br>"+
					"收货人地址："+address+"<br>";
			}
			message+="电子邮箱："+email+"<br>";
			$.messager.confirm("确认订单信息", message, function() {
				OrderManager.addOrder(_user, express, sname, telephone, address, email, function(data) {
					if(data.empty) {
						$.messager.popup("购物车中没有任何商品，无法创建订单！");
						return;
					}
					if(!data.availability) {
						$.messager.popup("购物车中的商品库存不足，请返回购物车确认！");
						return;
					}
					if(data.oid!=null) {
						location.href="pay.html?ono="+data.ono;
					}
				});			
			});

		}
	});
});