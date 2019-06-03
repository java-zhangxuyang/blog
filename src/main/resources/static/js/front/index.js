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
})