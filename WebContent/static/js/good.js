var gid=request("gid");
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
		fillText({
			"good-gname": good.gname,
			"good-tname": good.category.type.tname,
			"good-cname": good.category.cname,
			"good-descriptor": good.descriptor,
			"good-price": good.price
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
	});
});