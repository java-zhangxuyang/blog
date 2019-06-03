/**
 * 
 */
$(function(){
	$("#login").click(function(){
		$.ajax({
            type: "POST",
            url: "/admin/login",
            data: {userName:$("#userName").val(), password:$("#password").val()},
            dataType: "json",
            success: function(data){
                    if(data.code == -1){
                    	layer.msg(data.msg);
                    }else if(data.code == 1){
                    	layer.msg("登陆成功");
                    	window.location.href="/admin/index; 
                    }
                    
                 }
        });
		
	})
})