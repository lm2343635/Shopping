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
		if(!good.enable||!good.category.enable||!good.category.type.enable) {
			AdminManager.checkSession(function(username) {
				if(username==null) {
					location.href="urlError.html";
					return;
				} 
			});
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

		$("#buy-good-taobao").attr("href", good.taobao);

		//加载商品图片
		PhotoManager.getPhotosByGid(gid, function(photos) {
			if(photos.length==0) {
				$("#good-photo-list").mengular(".good-photo-list-template", {
					src: "static/images/noImage.jpg"
				});
			}
			for(var i in photos) {
				var indicator=$("<li>").attr("data-target", "#good-photo-list")
					.attr("data-slide-to", i)
					.attr("style","background-image: url(upload/"+tid+"/"+photos[i].filename+")");
				if(i==0)
					indicator.addClass("active");
				$("#good-photo-list .carousel-indicators").append(indicator);
			}

			for(var i in photos) {
				$("#good-photo-list .carousel-inner").mengular(".good-photo-list-template", {
					pid: photos[i].pid,
					src: "upload/"+tid+"/"+photos[i].filename,
					rname: _good.gname
				});
				if(i==0) {
					$("#"+photos[i].pid).addClass("active");
				}

			}
			$("#good-photo-list .mengular-template").remove();
		});

		//加载商品评论
		CommentManager.getCommentsByGid(gid, function(comments) {
			$("#good-comment-list").mengularClear();
			if(comments.length>0)
				$("#no-good-comment").hide();
			for(var i in comments) {
				$("#good-comment-list").mengular(".good-comment-list-template", {
					cid: comments[i].cid,
					ono: comments[i].order.ono,
					commentDate: comments[i].commentDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					content: comments[i].content
				});

				 //加载评分星级
				 $("#"+comments[i].cid+" .good-comment-stars i").each(function(index) {
				 	if(index+1<=comments[i].stars)
				 		$(this).addClass("fa-star");
				 	else
				 		$(this).addClass("fa-star-o");
				 });
			}
		});
	});

	//变更购买数量
	$("#buy-good-count").change(function() {
		var number=$("#buy-good-count").val();
		if(!isInteger(number)||number<0) {
			$.messager.popup("请填写一个正整数！");
			$(this).val(0);
			return;
		}
		if(number>_good.number) {
			$.messager.popup("超过库存数量！");
			$(this).val(0);
			return;
		}
		if(number==0) {
			$.messager.popup("至少选择一件商品！");
			$("#buy-good-cart").attr("disabled", "disabled");
		} else {
			var amount=(number*_good.price).toFixed(2);
			fillText({
				"buy-good-number": number,
				"buy-good-amount": amount
			});
			$("#buy-good-cart").removeAttr("disabled", "disabled");
		}
	});

	$("#buy-good-count-minus").click(function() {
		var number=parseInt($("#buy-good-count").val());
		if(number==0) {
			return;
		}
		number-=1;
		$("#buy-good-count").val(number).change();
	});

	$("#buy-good-count-plus").click(function() {
		var number=parseInt($("#buy-good-count").val());
		if(number==_good.number) {
			return;
		}
		number+=1;
		$("#buy-good-count").val(number).change();
	});

	//加入购物车
	$("#buy-good-cart, #buy-good-account").click(function() {
		var number=$("#buy-good-count").val();
		var id=	$(this).attr("id");
		checkSession(function(user) {
			if(user==null) {
//				$.messager.popup("请先登录！");
				$("#user-login-modal").modal("show");
				return;
			}
			if(number>0) {
				BasketManager.addToBasket(user.uid, gid, number, function(bid) {
					if(bid) {
						switch (id) {
						case "buy-good-cart":
							location.href="basket.html";
							break;
						case "buy-good-account":
							location.href="account.html";
							break;
						default:
							break;
						}
					}
				});
			}
			if(id=="buy-good-account"&&number==0)
				location.href="account.html";
		});
	});
});