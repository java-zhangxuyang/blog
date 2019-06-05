/**
 * 
 */
$(function(){
	$("ul li").click(function(){
	    $(this).addClass("active").siblings().removeClass("active");
	})
	
	$("#searchBotton").click(function(){
		var likeName = $("#likeName").val();
		console.info(likeName);
		if(likeName == ""){
			layer.msg('请输入关键词！');
			return;                                
		}                                          
		window.location.href="/index?likeName="+likeName; 
	})
	$("#resumeCheck").click(function(){
		$.ajax({
            type: "POST",
            url: "/front/resumeCheck",
            data: {pass:$("#pass").val()},
            dataType: "json",
            success: function(data){
                    if(data == 0){
                    	layer.msg("密码错误");
                    }else if(data == 1){
                    	window.location.href="/front/toResume"; 
                    }
                    
                 },
            error:function(){
	            	layer.msg("网络超时，请重试！");
	            }
        });
	})
	$("#massage").click(function(){
		$.ajax({
			type: "POST",
			url: "/front/addmessage",
			data: {pass:$("#pass").val()},
			dataType: "json",
			success: function(data){
				if(data == 0){
					layer.msg("密码错误");
				}else if(data == 1){
					window.location.href="/front/toResume"; 
				}
				
			},
			error:function(){
				layer.msg("网络超时，请重试！");
			}
		});
	})
})