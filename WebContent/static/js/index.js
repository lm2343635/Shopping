$(document).ready(function($) {
	$("#head").load("head.html");
	$("#foot").load("foot.html");

	GoodManager.getNewestGoodsWithLimit(4, function(goods) {
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
	});

	GoodManager.getHotestGoodsWithLimit(3, function(goods) {
		for(var j in goods) {
			var tid=goods[j].category.type.tid;
			var src="static/images/noImage.jpg";
			if(goods[j].cover!=null)
				src="upload/"+tid+"/"+goods[j].cover.filename;
			$("#hotest-good-list").mengular(".hotest-good-list-template", {
				gid: goods[j].gid,
				gname: goods[j].gname,
				src: src,
				price: goods[j].price,
				sold: goods[j].sold,
				tname: goods[j].category.type.tname,
				cname: goods[j].category.cname
			});
		}
	});

	/**
	TypeManager.getAll(function(types) {
		var style="news";
		for(var i in types) {
			style= style=="news"? "events": "news";

			var icon="static/images/noIcon.jpg";
			if(types[i].icon!=null)
				icon="upload/"+types[i].tid+"/"+types[i].icon.filename;
			$("#type-list").mengular(".type-list-template", {
				style: style,
				tid: types[i].tid,
				tname: types[i].tname,
				icon: icon
			});

			GoodManager.getGoodsByTidWithLimit(types[i].tid, 6, function(goods) {
				for(var j in goods) {
					var tid=goods[j].category.type.tid;
					var src="static/images/noImage.jpg";
					if(goods[j].cover!=null)
						src="upload/"+tid+"/"+goods[j].cover.filename;
					$("#"+tid+" .good-list").mengular(".good-list-template", {
						gid: goods[j].gid,
						gname: goods[j].gname,
						src: src,
						price: goods[j].price,
						sold: goods[j].sold,
						tname: goods[j].category.type.tname,
						cname: goods[j].category.cname
					});
				}
			});


		}
	});
	**/
});