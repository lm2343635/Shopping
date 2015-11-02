var gname=decodeURIComponent(request("gname"));

$(document).ready(function() {
	$("#head").load("head.html");
	$("#foot").load("foot.html");

	if(gname!=""&&gname!=null) {
		searchGoods(gname);
		fillValue({
			"search-good": gname
		});
	} else  {
		//加载销量最好的12件商品
		GoodManager.getHotestGoodsWithLimit(12, function(goods) {
			loadGoods(goods);
		});
	}

	//搜索商品
	$("#search-good-submit").click(function() {
		gname=$("#search-good").val();
		if(gname!=""&&gname!=null) {
			searchGoods(gname);
		}
	});

});

function searchGoods(gname) {
	$("#good-list").mengularClear();
	$("#search-loading").show();
	$("#no-search-result").hide();
	GoodManager.searchGoods(gname, function(goods) {
		$("#search-loading").hide();
		if(goods.length==0) {
			$("#no-search-result").show();
		} else {
			loadGoods(goods);
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

