
$(document).ready(function() {
	$("span.menu").click(function(){
		$(" ul.navig").slideToggle("slow" , function(){});
	});
	
	//检查用户session
	checkSession(function(user) {
		if(user!=null) {
			$("#nav-login, #nav-register").css("display","none");
			$("#nav-user a").text(user.uname+"的订单");
			$("#nav-user, #nav-logout").css("display","inline");
		}
	});
	
	SendeeManager.getBookingDomain(function(BookingDomain) {
		$("#user-register-submit, #forget-password").attr("href", "http://"+BookingDomain);
	});

	//用户登录
	$("#nav-login").click(function() {
		$("#user-login-modal").modal("show");
	});

	$("#user-login-modal").on("hidden.bs.modal", function() {
		$("#user-login-modal .input-group input").val("");
	});

	//用户退出登录
	$("#nav-logout").click(function() {
		SendeeManager.logout(function() {
			$.messager.popup("退出登录");
			$("#nav-login, #nav-register").css("display","inline");
			$("#nav-user, #nav-logout").css("display","none");
		});
	});

	$("#nav-register").click(function() {
		$("#user-register-modal").modal("show");
	});

	//用户登录
	$("#user-login-submit").click(function() {
		var telephone=$("#user-login-telephone").val();
		var password=$("#user-login-password").val();
		var validate=true;
		if(telephone==""||telephone==null) {
			$("#user-login-telephone").parent().addClass("has-error");
			validate=false;
		} else {
			$("#user-login-telephone").parent().removeClass("has-error");
		}
		if(password==""||password==null) {
			$("#user-login-password").parent().addClass("has-error");
			validate=false;
		} else {
			$("#user-login-password").parent().removeClass("has-error");
		}
		if(validate) {
			SendeeManager.login(telephone, password, function(success) {
				if(success) {
					$("#user-login-modal").modal("hide");
					$.messager.popup("登录成功！");
					checkSession(function(user) {
						$("#nav-login, #nav-register").css("display","none");
						$("#nav-user a").text(user.uname+"的订单");
						$("#nav-user, #nav-logout").css("display","inline");
					})
				} else {
					$("#user-login-telephone").parent().addClass("has-error");
					$("#user-login-password").parent().addClass("has-error");
				}
			});
		}
	});

	//支持键盘return键登录
	$("#user-login-modal").keydown(function() {
        if (event.keyCode==13) 
        	$("#user-login-submit").click();
    });
});