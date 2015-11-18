var modifyingTid;

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
	
	checkAdminSession(function(){
		
		loadTypes();
	});

	//添加一级分类
	$("#add-type-submit").click(function() {
		var tname=$("#add-type-tname").val();
		if(tname==""||tname==null)
			$.messager.popup("请填写一个名称！");
		else {
			TypeManager.addType(tname, function(tid) {
				if(tid!=null) {
					$("#add-type-modal").modal("hide");
					loadTypes();
				}
			});
		}

	});

	$("#add-type-modal").on("hide.bs.modal", function () {
		$("#add-type-tname").val("");
	});
	
	//修改一级分类
	$("#modify-type-submit").click(function() {
		var tname=$("#modify-type-tname").val();
		if(tname==""||tname==null)
			$.messager.popup("请填写一个名称！");
		else {
			TypeManager.modifyType(modifyingTid ,tname, function() {
				$("#modify-type-modal").modal("hide");
				loadTypes();
			});
		}
	});
});

/**
 * 加载一级分类
 */
function loadTypes() {
	TypeManager.getAll(false, function(types) {
		$("#type-list tbody").mengularClear();
		for(var i in types) {
			$("#type-list tbody").mengular(".type-list-template", {
				tid: types[i].tid,
				createDate: types[i].createDate.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
				tname: types[i].tname,
				categories: types[i].categories
			});

			//一级分类可用状态
			$("#"+types[i].tid+" .type-list-enable input").bootstrapSwitch({
				state: types[i].enable
			}).on('switchChange.bootstrapSwitch', function(event, state) {
				var tid=$(this).parent().parent().parent().parent().attr("id");
				TypeManager.enable(tid, state);
			});
			
			//管理一级分类
			$("#"+types[i].tid+" .type-list-manage").click(function() {
				modifyingTid=$(this).parent().attr("id");
				TypeManager.getType(modifyingTid, function(type) {
					$("#modify-type-tname").val(type.tname);
					if(type.icon!=null)
						$("#modify-type-icon").attr("src", "upload/"+modifyingTid+"/"+type.icon.filename);
					else
						$("#modify-type-icon").attr("src", "static/images/noIcon.jpg");
				});
				

				//上传照片
				$("#upload-icon").fileupload({
			    	autoUpload:true,
			    	url:"PhotoServlet?task=uploadTypeIcon&tid="+modifyingTid,
			    	dataType:"json",
			    	acceptFileTypes: /^image\/(gif|jpeg|png)$/,
			    	done:function(e,data){
			    		if(data.result.thumbnail==false) {
			    			$.messager.popup("图片尺寸过小，不允许上传！");
			    			return;
			    		}
			    		$("#modify-type-icon").attr("src", "upload/"+modifyingTid+"/"+data.result.filename);
			    	}
				});

				$("#modify-type-modal").modal("show");
			});
			//删除一级分类
			$("#"+types[i].tid+" .type-list-top").click(function() {
				var tid=$(this).parent().attr("id");
				TypeManager.updateCreateDate(tid, function() {
					loadTypes();
				})
			});
			
			//删除一级分类
			$("#"+types[i].tid+" .type-list-delete").click(function() {
				var tid=$(this).parent().attr("id");
				var tname=$("#"+tid+" .type-list-tname").text();
				$.messager.confirm("提示", "确认删除一级分类："+tname+"吗？", function() {
					TypeManager.deleteType(tid, function(success){
						if(success) 
							$("#"+tid).remove();
						else
							$.messager.popup("该一级分类下有二级分类，无法删除！");
					});
				});
			});
		}
	});
}