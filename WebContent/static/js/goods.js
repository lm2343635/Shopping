var cid=request("cid");
var tid;
var modifyingGid;

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

	checkAdminSession(function() {
		CategoryManager.getCategory(cid, function(category) {
			fillText({
				"type-name": category.type.tname,
				"category-name": category.cname
			});
			tid=category.type.tid;
			$("#back-to-category").attr("href", "categories.html?tid="+category.type.tid);
		});

		loadGoods();
	});

	//添加商品
	$("#add-good-submit").click(function() {
		var gname=$("#add-good-gname").val();
		var price=$("#add-good-price").val();
		var number=$("#add-good-number").val();
		var descriptor=$("#add-good-descriptor").val();
		var validate=true;
		if(gname==""||gname==null) {
			validate=false;
			$("#add-good-gname").parent().addClass("has-error");
		} else {
			$("#add-good-gname").parent().removeClass("has-error");
		}
		if(price==""||price==null||!isNum(price)) {
			validate=false;
			$("#add-good-price").parent().addClass("has-error");
		} else {
			$("#add-good-price").parent().removeClass("has-error");
		}
		if(number==""||number==null||!isInteger(number)) {
			validate=false;
			$("#add-good-number").parent().addClass("has-error");
		} else {
			$("#add-good-number").parent().removeClass("has-error");
		}
		if(descriptor==""||descriptor==null) {
			validate=false;
			$("#add-good-descriptor").parent().addClass("has-error");
		} else {
			$("#add-good-descriptor").parent().removeClass("has-error");
		}
		if(validate) {
			GoodManager.addGood(cid, gname, price, number, descriptor, function(gid) {
				if(gid!=null) {
					$("#add-good-modal").modal("hide");
					loadGoods();
				}
			});
		}
	});

	$("#add-good-modal").on("hidden.bs.modal", function() {
		$("#add-good-form input, #add-good-form textarea").val("")
			.parent().removeClass("has-error");
	});

	//修改商品
	$("#modify-good-submit").click(function() {
		var gname=$("#modify-good-gname").val();
		var price=$("#modify-good-price").val();
		var number=$("#modify-good-number").val();
		var descriptor=$("#modify-good-descriptor").val();
		var validate=true;
		if(gname==""||gname==null) {
			validate=false;
			$("#modify-good-gname").parent().addClass("has-error");
		} else {
			$("#modify-good-gname").parent().removeClass("has-error");
		}
		if(price==""||price==null||!isNum(price)) {
			validate=false;
			$("#modify-good-price").parent().addClass("has-error");
		} else {
			$("#modify-good-price").parent().removeClass("has-error");
		}
		if(number==""||number==null||!isInteger(number)) {
			validate=false;
			$("#modify-good-number").parent().addClass("has-error");
		} else {
			$("#modify-good-number").parent().removeClass("has-error");
		}
		if(descriptor==""||descriptor==null) {
			validate=false;
			$("#modify-good-descriptor").parent().addClass("has-error");
		} else {
			$("#modify-good-descriptor").parent().removeClass("has-error");
		}
		if(validate) {
			GoodManager.modifyGood(modifyingGid, gname, price, number, descriptor, function() {
				$("#modify-good-modal").modal("hide");
				loadGoods();
			});
		}
	});

	//添加商品数量
	$("#modify-good-number-add-submit").click(function() {
		var add=$("#modify-good-number-add").val();
		if(add==""||add==null||!isInteger(add)) {
			$.messager.popup("请正确填写要增加的商品数量！");
			return;
		}
		GoodManager.addGoodNumber(modifyingGid, add, function(number) {
			$("#modify-good-number").val(number);
			$.messager.popup("已添加"+add+"个");
			loadGoods();
		})
	});
});

function loadGoods() {
	GoodManager.getGoodsByCid(cid, function(goods) {
		$("#good-list tbody").mengularClear();
		for(var i in goods) {
			$("#good-list tbody").mengular(".good-list-template", {
				gid: goods[i].gid,
				createDate: goods[i].createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
				gname: goods[i].gname,
				price: goods[i].price,
				number: goods[i].number,
				sold: goods[i].sold,
			});

			//管理商品
			$("#"+goods[i].gid+" .good-list-manage").click(function() {
				modifyingGid=$(this).parent().attr("id");
				GoodManager.getGood(modifyingGid, function(good) {
					fillValue({
						"modify-good-gname": good.gname,
						"modify-good-price": good.price,
						"modify-good-number": good.number,
						"modify-good-descriptor": good.descriptor,
					});

					//加载照片
					$("#good-photo-list").mengularClear();
					PhotoManager.getPhotosByGid(modifyingGid, function(photos) {
						for(var i in photos) {
							$("#good-photo-list").mengular(".good-photo-template", {
								pid: photos[i].pid,
				    			src: "upload/"+tid+"/"+photos[i].filename
				    		});						
							//设定封面图片
							if(good.cover!=null) {
								if(good.cover.pid==photos[i].pid) {
									$("#"+photos[i].pid+" .good-photo-cover")
				    					.removeClass("button-action")
				    					.addClass("button-primary")
				    					.text("封面图片");
								}
							}
							//绑定删除图片按钮点击事件
			    			$("#"+photos[i].pid+" .good-photo-delete").click(function() {
			    				var pid=$(this).parent().attr("id");
			    				PhotoManager.removePhoto(pid, function() {
			    					$("#"+pid).remove();
			    				});
			    			});
			    			//绑定设定图片封面按钮点击事件
			    			$("#"+photos[i].pid+" .good-photo-cover").click(function() {
			    				var pid=$(this).parent().attr("id");
			    				PhotoManager.setAsGoodCover(pid, function() {
			    					$(".good-photo-cover").removeClass("button-primary")
				    					.removeClass("button-action")
				    					.addClass("button-action")
				    					.text("设为封面");		
			    					$("#"+pid+" .good-photo-cover")	
			    						.removeClass("button-action")
				    					.addClass("button-primary")
				    					.text("封面图片");
			    				});
			    			});
						}
					});

					$("#modify-good-modal").modal("show");
				});

				//上传照片
				$("#upload-photo").fileupload({
			    	autoUpload:true,
			    	url:"PhotoServlet?task=uploadGoodPhoto&gid="+modifyingGid,
			    	dataType:"json",
			    	acceptFileTypes: /^image\/(gif|jpeg|png)$/,
			    	done:function(e,data){
			     		$("#good-photo-list").mengular(".good-photo-template", {
			     			pid: data.result.pid,
			     			src: "upload/"+tid+"/"+data.result.filename
			     		});
						//绑定删除图片按钮点击事件
		    			$("#"+data.result.pid+" .good-photo-delete").click(function() {
		    				var pid=$(this).parent().attr("id");
		    				PhotoManager.removePhoto(pid, function() {
		    					$("#"+pid).remove();
		    				});
		    			});
		    			//绑定设定图片封面按钮点击事件
		    			$("#"+data.result.pid+" .good-photo-cover").click(function() {
		    				var pid=$(this).parent().attr("id");
		    				PhotoManager.setAsGoodCover(pid, function() {
		    					$(".good-photo-cover").removeClass("button-primary")
			    					.removeClass("button-action")
			    					.addClass("button-action")
			    					.text("设为封面");		
		    					$("#"+pid+" .good-photo-cover")	
		    						.removeClass("button-action")
			    					.addClass("button-primary")
			    					.text("封面图片");
		    				});
		    			});
			     		setTimeout(function(){
						 	$("#upload-photo-progress").hide(1500);
						 },2000);
			    	},
			    	progressall:function(e,data){
						$("#upload-photo-progress").show();
					    var progress=parseInt(data.loaded/data.total*100, 10);
					    $("#upload-photo-progress .progress-bar").css("width",progress+"%");
					    $("#upload-photo-progress .progress-bar").text(progress+"%");
			    	}
				});
				
			});
			
			//置顶商品
			$("#"+goods[i].gid+" .good-list-top").click(function() { 
				var gid=$(this).parent().attr("id");
				GoodManager.updateCreateDate(gid, function() {
					loadGoods();
				})
			});

			//删除商品
			$("#"+goods[i].gid+" .good-list-delete").click(function() {
				var gid=$(this).parent().attr("id");
				var gname=$("#"+gid+" .good-list-cname").text();
				$.messager.confirm("提示", "确认删除商品："+gname+"吗？", function() {
					GoodManager.removeGood(gid, function(success) {
						if(success)
							$("#"+gid).remove();
						else
							$.messager.popup("该商品已售出，无法删除！");
					});
				});
			});
		}
	});
}