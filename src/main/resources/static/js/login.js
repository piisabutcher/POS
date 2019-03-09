$(document).ready(function(){
    console.log("success");
})

$("#login>form>input[type=button]").click(function(event) {
	console.log("尝试登录……");
	$.ajax({
		url: '/user/getAllUser',
		type: 'post',
		dataType: 'json',
		data: {
			userId: $("#userId").val(),
			password: $("password").val()
		},
		success:function(res){
			location.pathname = "pages/index.html";
		},
		error:function(){
		    alert("出错啦！");


        }
	});
	
	
});