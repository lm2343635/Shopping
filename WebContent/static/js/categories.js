var tid=request("tid");
var modifyingCid;

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
		loadCategories();

		TypeManager.getType(tid, function(type) {
			fillText({
				"type-name": type.tname
			});
		});
	});

	//创建二级分类
	$("#add-category-submit").click(function() {
		var cname=$("#add-category-cname").val();
		if(cname==""||cname==null)
			$.messager.popup("请填写一个名称！");
		else {
			CategoryManager.addCategory(tid, cname, function(cid) {
				if(cid) {
					$("#add-category-modal").modal("hide");
					loadCategories();
				}
			});
		}
	});

	$("#add-category-modal").on("hide.bs.modal", function () {
		$("#add-category-cname").val("");
	});

	//修改二级分类
	$("#modify-category-submit").click(function() {
		var cname=$("#modify-category-cname").val();
		if(cname==""||cname==null)
			$.messager.popup("请填写一个名称！");
		else {
			CategoryManager.modifyCategory(modifyingCid ,cname, function() {
				$("#modify-category-modal").modal("hide");
				loadCategories();
			});
		}
	});
});

function loadCategories() {
	CategoryManager.getCategoriesByTid(tid, function(categories) {
		$("#category-list tbody").mengularClear();
		for(var i in categories) {
			$("#category-list tbody").mengular(".type-list-template", {
				cid: categories[i].cid,
				cname: categories[i].cname,
				createDate: categories[i].createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
				goods: categories[i].goods
			});

			//管理二级分类
			$("#"+categories[i].cid+" .category-list-manage").click(function() {
				modifyingCid=$(this).parent().attr("id");
				CategoryManager.getCategory(modifyingCid, function(category) {
					$("#modify-category-cname").val(category.cname);
					if(category.icon!=null)
						$("#modify-category-icon").attr("src", "upload/"+tid+"/"+category.icon.filename);
					else
						$("#modify-category-icon").attr("src", "static/images/noIcon.jpg");
				});
				

				//上传照片
				$("#upload-icon").fileupload({
			    	autoUpload:true,
			    	url:"PhotoServlet?task=uploadCategoryIcon&cid="+modifyingCid,
			    	dataType:"json",
			    	acceptFileTypes: /^image\/(gif|jpeg|png)$/,
			    	done:function(e,data){
			    		$("#modify-category-icon").attr("src", "upload/"+tid+"/"+data.result.filename);
			    	}
				});

				$("#modify-category-modal").modal("show");
			});

			//删除二级分类
			$("#"+categories[i].cid+" .category-list-delete").click(function() {
				var cid=$(this).parent().attr("id");
				var cname=$("#"+cid+" .category-list-cname").text();
				$.messager.confirm("提示", "确认删除二级分类："+cname+"吗？", function() {
					CategoryManager.removeCategory(cid, function(success){
						if(success) 
							$("#"+cid).remove();
						else
							$.messager.popup("该二级分类下有商品，无法删除！");
					});
				});
			});
		}
	});
}