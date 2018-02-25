var TT = TAOTAO = {
	checkLogin : function(){
		//取出cookie中的token，名称为
		var _ticket = $.cookie("YOUTU_TOKEN");
		if(!_ticket){
			return ;
		}
		//使用jQuery包装好的jsonp调用
		$.ajax({
			url : "http://localhost:8084/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.nickname;
					//退出时调用安全退出
					var html = username + "，欢迎来到优兔！<a href=\"http://localhost:8084/user/logout/"+ _ticket +"\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});