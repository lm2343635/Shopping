var types;
var tid=request("tid");
var cid=request("cid");

$(document).ready(function() {
	$("#head").load("head.html");
	$("#foot").load("foot.html");

	TypeManager.getAll(true, function(_types) {
		types=_types;
		if(tid) {
			var enable=false;
			for(var i in types) {
				if(types[i].tid==tid) {
					enable=true;
					break;
				}
			}
			if(!enable) {
				location.href="urlError.html";
				return;
			}
		}
		loadOneType(0);
	});

	//搜索商品
	$("#search-good-submit").click(function() {
		var gname=$("#search-good").val();
		if(gname!=""&&gname!=null) {
			location.href="search.html?gname="+gname;
		}
	});
});

function loadOneType(index) {
	var ticon="static/images/noIcon.jpg";
	if(types[index].icon!=null)
		ticon="upload/"+types[index].tid+"/"+types[index].icon.filename;
	$("#type-list").mengular(".type-template", {
		tid: types[index].tid,
		tname: types[index].tname,
		ticon: ticon
	});
	//为一级分类绑定点击事件
	$("#"+types[index].tid).click(function() {
		var tid=$(this).attr("id");
		location.href="show.html?tid="+tid;
	});

	CategoryManager.getCategoriesByTid(types[index].tid, true, function(categories) {
		for(var j in categories) {
			var cicon="static/images/noIcon.jpg";
			if(categories[j].icon!=null)
				cicon="upload/"+categories[j].type.tid+"/"+categories[j].icon.filename;
			$("#type-list").mengular(".category-template", {
				cid: categories[j].cid,
				tid: categories[j].type.tid,
				cname: categories[j].cname,
				cicon: cicon
			});		
			//为二级分类绑定点击事件
			$("#"+categories[j].cid).click(function() {
				location.href="show.html?tid="+tid+"&cid="+$(this).attr("id");
			});		
		}
		if(index<types.length-1)
			loadOneType(index+1);
		if(index==types.length-1) {
			//加载第一个分类的商品
			tid=tid? tid: types[0].tid;
			cid=cid? cid: $("#"+tid).next().attr("id");
			$("."+tid).show();
			$("#type-list li").removeClass("list-group-item-info");
			$("#"+cid).addClass("list-group-item-info");
			if(cid!=undefined&&(cid!=null||cid!="")) {
				CategoryManager.getCategory(cid, function(category) {
					if(category==null) {
						location.href="urlError.html";
						return;
					}
					if(!category.enable||!category.type.enable) {
						location.href="urlError.html";
						return;
					}
					GoodManager.getGoodsByCid(cid, true, function(goods) {
						loadGoods(goods);
					});
				});
			}
		}
	});
}

function loadGoods(goods) {
	$("#good-list").mengularClear();
	for(var j in goods) {
		var tid=goods[j].category.type.tid;
		var src="static/images/noImage.jpg";
		if(goods[j].cover!=null)
			src="upload/"+tid+"/"+goods[j].cover.filename;
		$("#good-list").mengular(".good-list-template", {
			gid: goods[j].gid,
			gname: goods[j].gname,
			src: src,
			price: goods[j].price,
			sold: goods[j].sold,
			tname: goods[j].category.type.tname,
			cname: goods[j].category.cname
		});
	}
}