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
		var name = $("#ipname").val();
		if(name==null || name==""){
			layer.alert('请输入昵称！', {
			  skin: 'layui-layer-molv' //样式类名
			  ,closeBtn: 0
			}, function(){
				layer.close();
			});
			return;
		}
		$.ajax({
			type: "POST",
			url: "/front/addmessage",
			data: {massage:$("#content").val(),name:name,ip:$("#ip").val()},
			dataType: "json",
			success: function(data){
				if(data == 0){
					layer.msg("留言出错了~您再试试？");
				}else if(data == 1){
					layer.alert('留言成功，感谢您的留言和支持！', {
					  skin: 'layui-layer-molv' //样式类名
					  ,closeBtn: 0
					}, function(){
						window.location.href="/front/message"; 
					});
				}
				
			},
			error:function(){
				layer.msg("网络超时，请重试！");
			}
		});
	})
	
})

