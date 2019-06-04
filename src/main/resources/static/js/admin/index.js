/**
 * 
 */
$(function(){
	 $(".nav li").click(function() {
	        $(".active").removeClass('active');
	        $(this).addClass("active");
	    }); 
	 
	$("#loginOut").click(function(){
		$.ajax({
            type: "POST",
            url: "/admin/loginOut",
            dataType: "json",
            success: function(data){
                	layer.msg("注销成功");
                	window.location.href="/admin"; 
                 },
            error:function(){
	            	layer.msg("网络超时，请重试！");
	            }
        });
	})
})