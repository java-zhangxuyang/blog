/**
 * 
 */
$(function(){
	
	var myDate = new Date();
	var year = myDate.getFullYear(); //获取完整的年份(4位,1970-????)
 	$("#yearing").html(year);
	
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
                    	layer.alert('<center>个人简历，请勿盗用！</center>', {
							skin: 'layui-layer-molv' //样式类名
								,closeBtn: 0
						}, function(){
							window.location.href="/front/toResume"; 
						});
                    }
                    
                 },
            error:function(){
	            	layer.msg("网络超时，请重试！");
	            }
        });
	})
	$("#massage").click(function(){
		var name = $("#ipname").val();
		var content = $("#content").val();
		if(name==null || name==""){
			layer.alert('请输入昵称！', {
				  skin: 'layui-layer-molv' //样式类名
				  ,closeBtn: 0
				}, function(){
				  layer.closeAll();
				});
		}else{
			if(content=="<p><br></p>"){
				layer.alert('请输入留言内容！', {
					  skin: 'layui-layer-molv' //样式类名
					  ,closeBtn: 0
					}, function(){
					  layer.closeAll();
					});
			}else{
				$.ajax({
					type: "POST",
					url: "/front/addmessage",
					data: {massage:content,name:name,ip:$("#ip").val()},
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
			}
		}
	})
})

