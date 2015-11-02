var types;

$(document).ready(function() {
	$("#head").load("head.html");
	$("#foot").load("foot.html");

	TypeManager.getAll(function(_types) {
		types=_types;
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
		if(!$("."+tid).is(":visible"))
			$("."+tid).show("normal");
		else
			$("."+tid).hide("normal");
	});

	CategoryManager.getCategoriesByTid(types[index].tid, function(categories) {
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
				$("#type-list li").removeClass("list-group-item-info");
				$(this).addClass("list-group-item-info");
				$("#good-list").mengularClear();
				$("#search-room-loading").show();
				$("#no-search-result").hide();
				GoodManager.getGoodsByCid($(this).attr("id"), function(goods) {
					$("#search-room-loading").hide();
					if(goods.length==0) {
						$("#no-search-result").show();
					} else {
						loadGoods(goods);
					}
				});
			});		
		}
		console.log(index+", "+types.length);
		if(index<types.length-1)
			loadOneType(index+1);
		if(index==types.length-1) {
			//加载第一个分类的商品
			var cid=$(".category-template").eq(1).attr("id");
			$("."+types[0].tid).show();
			$("#type-list li").removeClass("list-group-item-info");
			$("#"+cid).addClass("list-group-item-info");
			GoodManager.getGoodsByCid(cid, function(goods) {
				loadGoods(goods);
			});
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