$(function () {
	$("#content>ul>li").click(function () {
		console.log(this);
		$(this).addClass("tabSwitch")
			.siblings().removeClass("tabSwitch");


	})
});
$("#login>form>input[type=button]").click(function() {
	console.log("尝试登录……");
	$.ajax({
		url: '/user/login',
		type: 'post',
		contentType:'application/json',
		async: true,
		data: JSON.stringify({
			"userId": $("#userId").val(),
			"password": $("#password").val()
		}),
		success:function(res){
			location.pathname = "pages/index.html";
		},
		error:function(){
		    alert("出错啦！");
        }
	});
	
	
});