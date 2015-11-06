var accountCount=0, accountAmount=0;
var _user;

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
	
	//加载购物车中的商品
	checkSession(function(user) {
		if(user==null) {
			$("#no-good").show();
			return;
		}
		_user=user;
		BasketManager.getBasketGoodsByUid(user.uid, function(baskets) {
			if(baskets.length==0) {
				$("#no-good").show();
				return;
			} 
			$("#account-div").show();
			for(var i in baskets) {
				//如果购买件数大于库存
				if(baskets[i].count>baskets[i].good.number) {
					baskets[i].count=baskets[i].good.number;
					BasketManager.changeCount(baskets[i].bid, baskets[i].count);
				}
				accountCount+=baskets[i].count;
				accountAmount+=baskets[i].count*baskets[i].good.price;
				var src="static/images/noImage.jpg";
				if(baskets[i].good.cover!=null) {
					src="upload/"+baskets[i].good.category.type.tid+"/"+baskets[i].good.cover.filename;
				}
				$("#basket-list").mengular(".basket-list-template", {
					bid: baskets[i].bid,
					gid: baskets[i].good.gid,
					src: src,
					gname: baskets[i].good.gname,
					number: baskets[i].good.number,
					cname: baskets[i].good.category.cname,
					tname: baskets[i].good.category.type.tname,
					amount: baskets[i].count*baskets[i].good.price,
					price: baskets[i].good.price
				});

				//加载购买件数的下拉菜单
				for(var j=1; j<=baskets[i].good.number; j++) {
					var option=$("<option>").val(j).text(j);
					if(j==baskets[i].count)
						option.attr("selected", "selected");
					$("#"+baskets[i].bid+" .buy-good-count").append(option);
				}

				//变更购买件数
				$("#"+baskets[i].bid+" .buy-good-count").change(function() {
					var count=$(this).val();
					var bid=$(this).parent().parent().parent().parent().parent().attr("id");
					BasketManager.changeCount(bid, count, function(data) {
						$("#"+bid+" .buy-good-amount").text(data.amount);
						accountCount+=data.dcount;
						accountAmount+=data.damount;
						fillText({
							"account-count": accountCount,
							"account-amount": accountAmount
						});
					});
				});

				//移除商品
				$("#"+baskets[i].bid+" .basket-remove").click(function() {
					var bid=$(this).parent().parent().parent().parent().attr("id");
					var gname=$("#"+bid+" .basket-gname").text();
					var count=parseInt($("#"+bid+" .buy-good-count").val());
					var amount=parseFloat($("#"+bid+" .buy-good-amount").text());
					$.messager.confirm("提示", "确认从购物车中移除商品"+gname+"吗？", function() {
						BasketManager.removeGoodFromBasket(bid, function() {
							$("#"+bid).remove();
							accountCount-=count;
							accountAmount-=amount;
							fillText({
								"account-count": accountCount,
								"account-amount": accountAmount
							});
							if(accountCount==0) {
								$("#account-div").hide();
								$("#no-good").show();
							}
						});
					});
				});
			}
			fillText({
				"account-goods": baskets.length,
				"account-count": accountCount,
				"account-amount": accountAmount
			});
		});
	});

	//清空购物车
	$("#clear-basket").click(function() {
		$.messager.confirm("警告", "确定要清除购物车中的所有商品吗？", function() {
			BasketManager.clearBasket(_user.uid, function() {
				$("#basket-list").remove();
				$("#account-div").hide();
				$("#no-good").show();
			});
		});
	});
});