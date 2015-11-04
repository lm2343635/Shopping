var gid=request("gid");
var _good;
var tid;

$(document).ready(function() {
	$("#head").load("head.html");
	$("#foot").load("foot.html");
	
	GoodManager.getGood(gid, function(good) {
		if(good==null) {
			location.href="urlError.html";
			return;
		}
		tid=good.category.type.tid;
		_good=good;

		fillText({
			"good-gname": good.gname,
			"good-tname": good.category.type.tname,
			"good-cname": good.category.cname,
			"good-descriptor": good.descriptor,
			"good-price": good.price,
			"good-number": good.number
		})

		PhotoManager.getPhotosByGid(gid, function(photos) {
			for(var i in photos) {
				$("#good-photo-list").mengular(".good-photo-list-template", {
					pid: photos[i].pid,
					src: "upload/"+tid+"/"+photos[i].filename
				});
			}
			if(photos.length==0) {
				$("#good-photo-list").mengular(".good-photo-list-template", {
					src: "static/images/noImage.jpg"
				});
			}
		});

		for(var i=1; i<=good.number; i++) {
			$("<option>").val(i).text("购买"+i+"件").appendTo("#buy-good-count");
		}
	});

	//变更购买数量
	$("#buy-good-count").change(function() {
		var number=$("#buy-good-count").val();
		if(number==0) {
			$.messager.popup("至少选择一件商品！");
			$("#buy-good-cart").attr("disabled", "disabled");
		} else {
			var amount=number*_good.price;
			fillText({
				"buy-good-number": number,
				"buy-good-amount": amount
			});
			$("#buy-good-cart").removeAttr("disabled", "disabled");
		}
			
		
	});

	//加入购物车
	$("#buy-good-cart").click(function() {
		var number=$("#buy-good-count").val();
		if(number>0) {
			checkSession(function(user) {
				if(user==null) {
					$.messager.popup("请先登录！");
					return;
				}
					
				BasketManager.addToBasket(user.uid, gid, number, function(bid) {
					if(bid) {
						location.href="basket.html";
					}
				})
			})
		}
	});
});