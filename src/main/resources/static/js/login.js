//登录注册样式切换
$("#content>ul>li").click(function () {
	$(this).addClass("tabOn")
	.removeClass("tabOff")
	.siblings().removeClass("tabSwitch").
	addClass('tabOff');
	if($(this).index()===0){
		showOrHide($("#register"));
		$('#content').animate({
			top:"30%"
		}, 300);
	}
	else{
		showOrHide($("#login"));
		$('#content').animate({
			top:"15%"
		}, 300);
	}
})
function showOrHide(o){
	$(o).animate({
			height:"hide",
			width:"hide",
			opacity:"hide"
		}, 400)
	.siblings().animate({
			height:"show",
			width:"show",
			opacity:"show"
		}, 400);
}
//登录
$("#login>form>input[type=button]").click(function() {
	console.log("login……");
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
			console.log(res);
			if(res.code === 200){
				window.location.pathname = "/pages/index.html";
			}
			else if(res.code === 401){
				alert("密码错误！");
			}
		},
		error:function(){
		    alert("服务器出错");
        }
	});
});
//注册表单验证
$('#registerForm input').keyup(function() {
	var pwdPattern = /^[0-9a-zA-Z]+$/;
	var telPattern = /^[1][3,4,5,7,8][0-9]{9}$/;
	if($(this).is('#rUsername')){
		if(this.value.length<2 || this.value.length>16){
			$('span').remove('.u1');
			$(this).after('<span class="u1">WARN：请输入长度为2~16的用户名</span>');
		}else{
			$('span').remove('.u1');
		}
	}
	if($(this).is('#rPassword')){
		if(this.value.length<6 || this.value.length>16){
			$('span').remove('.p1');
			$(this).after('<span class="p1">WARN：密码长度要求为6~16</span>');
		}else{
			$('span').remove('.p1');
		}
        if(!pwdPattern.test(this.value)){
        	$('span').remove('.p2');
        	$(this).after('<span class="p2">WARN：密码中含有非法字符</span>');
        }else{
        	$('span').remove('.p2');
        }
	}
	if($(this).is('#rrPassword')){
		if(this.value !== $('#rPassword').val()){
			$('span').remove('.p3');
			$(this).after('<span class="p3">WARN：两次密码输入不一致</span>');
		}else{
			$('span').remove('.p3');
		}
	}
	if($(this).is('#rTel')){
		if(!telPattern.test(this.value)){
        	$('span').remove('.t1');
        	$(this).after('<span class="t1">WARN：请输入正确格式的手机号码</span>');
        }else{
        	$('span').remove('.t1');
        }
	}
});
$('#registerForm input[type=button]').click(function() {
	var flag = false;
	$(this)[0].disabled = false;
	$(this).siblings('input').each(function(index, el) {
		if(el.value == ""){
			flag = true;
		}
	});
	if(flag){
		alert("表单未填写完整！");
	}
	else if($(this).parent().children('span').length !== 0){
		$(this)[0].disabled = true;
		alert("表单存在尚未处理的错误！");
	}
	else{
		console.log("注册register……");
		var gender = $('input:radio[name="sex"]:checked')=='男'?1:2;
		$.ajax({
			url: '/user/register',
			type: 'post',
			contentType:'application/json',
			async: true,
			data: JSON.stringify({
				"userName": $("#rUsername").val(),
				"password": $("#rPassword").val(),
				"gender": gender,
				"tel": $('#rTel').val(),
				"power": 0
			}),
			success:function(res){
				console.log(res);
				if(res.code === 200){
					window.location.pathname = "/pages/index.html";
				}
			},
			error:function(){
				alert("服务器出错");
			}

		});
	}

});