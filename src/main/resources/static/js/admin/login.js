/**
 * 
 */
$(function(){
	$("#loginButton").click(function(){
		console.info("111")
		$.ajax({
            type: "POST",
            url: "/login",
            data: {userName:$("#userName").val(), password:$("#password").val()},
            dataType: "json",
            success: function(data){
                    if(data.code == -1){
                    	layer.msg(data.msg);
                    }else if(data.code == 1){
                    	layer.msg("登陆成功");
                    	window.location.href="/admin/index"; 
                    }
                    
                 },
            error:function(){
	            	layer.msg("网络超时，请重试！");
	            }
        });
	})
})