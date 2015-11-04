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

		BasketManager.getBasketGoodsByUid(user.uid, function(baskets) {
			if(baskets.length==0) {
				$("#no-good").show();
				return;
			}
			for(var i in baskets) {
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
					amount: baskets[i].count*baskets[i].good.price
				});

				//加载购买件数的下拉菜单
				for(var j=1; j<baskets[i].good.number; j++) {
					var option=$("<option>").val(j).text(j);
					if(j==baskets[i].count)
						option.attr("selected", "selected");
					$("#"+baskets[i].bid+" .buy-good-count").append(option);
				}
				
				//变更购买件数
				$("#"+baskets[i].bid+" .buy-good-count").change(function() {
					var count=$(this).val();
					var bid=$(this).parent().parent().parent().parent().parent().attr("id");
					BasketManager.changeCount(bid, count, function(amount) {
						$("#"+bid+" .buy-good-amount").text(amount);
					});
				});

				//移除商品
				$("#"+baskets[i].bid+" .basket-remove").click(function() {
					var bid=$(this).parent().parent().parent().parent().attr("id");
					var gname=$("#"+bid+" .basket-gname").text();
					$.messager.confirm("提示", "确认从购物车中移除商品"+gname+"吗？", function() {
						BasketManager.removeGoodFromBasket(bid, function() {
							$("#"+bid).remove();
						});
					});
				});
			}
		});
	});
});