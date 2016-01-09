var stars=5;
var writingCommentOid;
var writingCommentGid;

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
				if(orders[i].payed&&!orders[i].timeout&&!orders[i].send&&!orders[i].receive) { //待发货
					template=".order-payed-template";
				} else if (!orders[i].payed&&!orders[i].timeout&&!orders[i].send&&!orders[i].receive) { //待付款
					template=".order-wait-template";
				} else if(orders[i].payed&&!orders[i].timeout&&orders[i].send&&!orders[i].receive) { //已发货
					template=".order-sending-template";
				} else if(orders[i].payed&&orders[i].timeout&&!orders[i].receive) {//待退款
					template=".order-return-template";
				} else if(orders[i].payed&&!orders[i].timeout&&orders[i].send&&orders[i].receive) { //已完成
					template=".order-finish-template";
				} else if(!orders[i].payed&&orders[i].timeout&&!orders[i].send&&!orders[i].receive) {//未付款
					template=".order-timeout-template";
				}
				$("#order-list").mengular(template, {
					oid: orders[i].oid,
					ono: orders[i].ono,
					count: orders[i].count,
					amount: (orders[i].amount).toFixed(2),
					createDate: orders[i].createDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					payDate: orders[i].payDate==null? null: orders[i].payDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					sendDate: orders[i].sendDate==null? null: orders[i].sendDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					receiveDate: orders[i].receiveDate==null? null: orders[i].receiveDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
					returnDate: orders[i].returnDate==null? null: orders[i].returnDate.format(DATE_HOUR_MINUTE_FORMAT_CN),
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

				//评论订单
				$("#"+orders[i].oid+" .order-comment").click(function() {
					writingCommentOid=$(this).parent().attr("id");
					BasketManager.getBasketGoodsByOid(writingCommentOid, function(baskets) {
						$("#order-goods").mengularClear();
						for(var i in baskets) {
							var src="static/images/noImage.jpg";
							if(baskets[i].good.cover!=null) {
								src="upload/"+baskets[i].good.category.type.tid+"/"+baskets[i].good.cover.filename;
							}
							$("#order-goods").mengular(".order-goods-template", {
								bid: baskets[i].bid,
								gid: baskets[i].good.gid,
								src: src,
								gname: baskets[i].good.gname,
								count: baskets[i].count
							});

							$("#"+baskets[i].good.gid).click(function() {
								writingCommentGid=$(this).attr("id");
								GoodManager.getGood(writingCommentGid, function(good) {
									$("#good-comment-gname").text(good.gname);
								});
								
								CommentManager.getCommentByOidAndGid(writingCommentOid, writingCommentGid, function(comment) {
									if(comment!=null) {
										stars=comment.stars;
										for(var i=0; i<5; i++) {
											if(i<=stars-1) 
												$("#good-comment-stars i").eq(i).removeClass("fa-star-o").addClass("fa-star");
											else 
												$("#good-comment-stars i").eq(i).removeClass("fa-star").addClass("fa-star-o");
										}
										$("#good-comment-content").val(comment.content);
									}
								});

								$("#order-goods").fadeOut();
								$("#good-comment-stars, #good-comment-content, #good-comment-submit").fadeIn();
							});
						}

						$("#order-comment-modal").modal("show");
					});
				});
			}
		});
	});

	//选择星级
	$("#good-comment-stars i").each(function(index) {
		$(this).mouseover(function() {
			for(var i=0; i<5; i++) {
				if(i<=index) 
					$("#good-comment-stars i").eq(i).removeClass("fa-star-o").addClass("fa-star");
				else 
					$("#good-comment-stars i").eq(i).removeClass("fa-star").addClass("fa-star-o");
			}
		});
		$(this).mouseout(function() {
			for(var i=0; i<5; i++) {
				if(i<=stars-1) 
					$("#good-comment-stars i").eq(i).removeClass("fa-star-o").addClass("fa-star");
				else 
					$("#good-comment-stars i").eq(i).removeClass("fa-star").addClass("fa-star-o");
			}
		});
		$(this).click(function() {
			for(var i=0; i<5; i++) {
				if(i<=index) 
					$("#good-comment-stars i").eq(i).removeClass("fa-star-o").addClass("fa-star");
				else 
					$("#good-comment-stars i").eq(i).removeClass("fa-star").addClass("fa-star-o");
			}
			stars=index+1;
		});
	});

	//提交评论
	$("#good-comment-submit").click(function() {
		var content=$("#good-comment-content").val();
		if(content==null||content=="") {
			$.messager.popup("请填写评论");
			return;
		} 
		CommentManager.writeComment(writingCommentOid, writingCommentGid, content, stars, function(success) {
			if(success) {
				
				$("#order-comment-modal").modal("hide");
				$.messager.popup("评论成功，等待审核！");
			}
		});
	});

	$("#order-comment-modal").on("hidden.bs.modal", function() {
		$("#order-goods").fadeIn();
		$("#good-comment-stars, #good-comment-content, #good-comment-submit").fadeOut();
		$("#good-comment-gname").text("");
		stars=5;
		for(var i=0; i<5; i++) {
			if(i<=stars-1) 
				$("#good-comment-stars i").eq(i).removeClass("fa-star-o").addClass("fa-star");
			else 
				$("#good-comment-stars i").eq(i).removeClass("fa-star").addClass("fa-star-o");
		}
		$("#good-comment-content").val("");
	});
});
