/**
 * 初始默认显示员工管理页面
 */
//showUser(0,11,"/user/getUserList");
/**
 * 初始化css样式
 */
$('#left>ul>li:first').addClass('li-click')
.children('div').children().addClass('i-click')
/**
 * 样式控制
 */
$('#left>ul>li').mouseover(function(){
    if(this.className != "li-click"){
        $(this).addClass('li-hover')
            .children('div').children().addClass('i-hover');
    }
    $(this).children('ul').stop(true).animate({
        width:"show",
    }, 200);
}).mouseleave(function(){
    if($(this).hasClass("li-hover")){
        $(this).removeClass('li-hover')
            .children('div').children().removeClass('i-hover');
    }
    $(this).children('ul').stop(true).animate({
        width:"hide",      
    }, 100);
});
//切换列表
$('#left>ul>li').click(function() {
    $(this).addClass('li-click').siblings().removeClass('li-click');
	$(this).children('div').children().addClass('i-click')
	$(this).siblings().children('div').children().removeClass('i-click');
	var index = $(this).index();
	$('#content>div:eq('+index+')').animate({
		height:'show',
		opacity:'show',
		width:'show'
	}, 250).siblings().animate({
		height:'hide',
		opacity:'hide',
		width:'hide'
	}, 250);
	switch (index) {
        case 0: showUser(0,11,"/user/getUserList");break;
        case 1: showCatelog();break;
    }
});

/**
 * 分页按钮
 */
$('#first').click(function() {
	showUser(0);
    $('#now').text(1);
});
$('#last').click(function(){
    showUser(1);
    $('#now').text(2);
});
$('#prev').click(function(){
    var page = $('#now').text();
    if(page!=1){
        page = page-1;
        showUser(page-1);
        $('#now').text(page);
    }
});
var userPage;//记住用户列表页数，全局
$("#next").click(function(){
    var page = $('#now').text();
    if(page!=userPage){
        page = (page-0)+1;
        showUser(page-1);
        $('#now').text(page);
    }
})
/**
 * 控制分页
 */
function pageController(page,size,itface){
    var data;
    $.ajax({
        url:itface,
        type: 'post',
        contentType:'application/x-www-form-urlencoded',
        async: false,//指定同步，回调函数无法传参问题
        data:{
            page:page,
            size:size
        },
        success:function(res){
            if(res.code === 200){
                data = res;
                return;
            }
        }
    });
    return data;
}
/**
 * 用户列表获取某页
 */
function showUser(page){
    $('#content-worker table tr:first-child').siblings().remove();
    var res = pageController(page,11,"/user/getUserList");
    var data = res.dataList.content;//数据
    userPage = res.dataList.totalPages;//总页数
    $('#content-worker').show();
    $(data).each(function (i, u){
        var gender = (u.gender===1)?'男':'女';
        var power = (u.power===0)?'员工':'管理员';
        $('#content-worker table').append(
            '<tr>'+
            '<td>'+u.userId+'</td>'+
            '<td>'+u.password+'</td>'+
            '<td>'+u.userName+'</td>'+
            '<td>'+gender+'</td>'+
            '<td>'+u.tel+'</td>'+
            '<td>'+power+'</td>'+
            '<td>'+
            '<button class="edit" id="e'+u.userId+'">编辑</button>'+
            '<button class="delete" id="d'+u.userId+'">删除</button>'+
            '</td>'+
            '</tr>');
    });
}
/**
 * 修改用户信息
 */
function editUser(){
    $.ajax({
        url: '/user/editUser',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({

        }),
        success:function(res){

        },
        error:function(){

        }

    });
}
/**
 * 删除用户
 */
function deleteUser(){
    $.ajax({
        url: '/user/deleterUser',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({

        }),
        success:function(res){

        },
        error:function(){

        }
    });
}
function showCatelog(){
    $('#content-catalog>ul').empty();
	var data = getAllCatalog().dataList;
	var l = data.length;
	$(data).each(function(i, ctl) {
        $('#content-food>ul').append(
            '<li id=ctl'+i+'>'+ctl.catalogName+
            '<button class="ctlEdit">编辑</button>'+
            '<button class="ctlDelete">删除</button>'+
            '</li>');
	});
    $('#content-food>ul').append('<li id="allCatalog">查看全部</li>')
}
$('#allCatalog').click(function(){
    $('#content-catalog').empty();



})
/**
 * 查询所有菜品
 */
function getAllCatalog(){
    var data;
    $.ajax({
        url: '/catalog/getAllCatalog',
        type: 'get',
        contentType:'application/json',
        async: false,
        success:function(res){
            data = res;
            return;
        },
    });
    return data;
}
/**
 * 新增菜品
 */
$('#ca3').click(function(){
    //addCatalog()
});
function addCatalog(){
    $.ajax({
        url: '/catalog/saveCatalog',
        type: 'post',
        contentType:'application/x-www-form-urlencoded',
        async: true,
        data:{
            ctlName:'烧烤'
        },
        success:function(res){
            console.log(res);
        },
        error:function(){
        }

    });
}
$('#ca4').click(function(){
    //deleteCatalog();
});
/**
 * 删除菜品
 */
function deleteCatalog(){
    $.ajax({
        url: '/catalog/deleteCatalog',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({
            catalogId:"H"
        }),
        success:function(res){
            console.log(res);
        },
        error:function(){

        }

    });
}
$('#ca5').click(function(){
    //getCatalogByCatalogName();
});
/**
 * 根据菜品名查询菜品
 */
function getCatalogByCatalogName(){
    $.ajax({
        url: '/catalog/getCatalogByName',
        type: 'post',
        contentType:'application/x-www-form-urlencoded',
        async: true,
        data: {
            ctlName:"意粉"
        },
        success:function(res){
            console.log(res);
        },
        error:function(){
        }
    });
}
$('#ca2').click(function(){
    //editCatalog();
});
/**
 * 修改菜品信息
 */
function editCatalog() {
    $.ajax({
        url: '/catalog/updateCatalog',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({


        }),
        success:function(res){

        },
        error:function(){

        }

    });

}
/**
 * 新增菜单
 */
$('#fa3').click(function(){
    //addFood();
});
function addFood(){
    $.ajax({
        url: '/food/saveFood',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({
            foodId:'123321',
            foodName:'edit',
            image:'289983',
            supPeriod:"14:00",
            price:54,
            catalog:{
                catalogId:'B'
            }
        }),
        success:function(res){
            console.log(res);
        },
        error:function(){

        }

    });
}
/**
 * 删除菜单
 */
$('#fa4').click(function(){
    deleteFood();
});
function deleteFood() {
    $.ajax({
        url: '/food/deleteByFoodId',
        type: 'post',
        contentType:'application/x-www-form-urlencoded',
        async: true,
        data: {
            foodId:'1414141'
        },
        success:function(res){
            console.log(res);

        },
        error:function(){

        }

    });
}
/**
 * 修改菜单
 */
$('#fa2').click(function(){
    //editFood();
});
function editFood(){
    $.ajax({
        url: '/food/updateFood',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({
            foodId:'777777',
            foodName:'奶茶'//不传参默认为空;
        }),
        success:function(res){
            console.log(res);

        },
        error:function(){

        }

    });
}
/**
 * 查询所有菜单
 */
$('#fa1').click(function(){
    getAllFood();
});
function getAllFood() {
    $.ajax({
        url: '/food/getAllFood',
        type: 'get',
        contentType:'application/json',
        async: true,
        success:function(res){
            console.log(res);
        },
        error:function(){
        }
    });

}
$('#fa5').click(function(){
    getFoodByName();
});
/**
 * 根据菜单ID查询菜单
 */
function getFoodById() {
    $.ajax({
        url: '/food/getFoodById',
        type: 'post',
        contentType:'application/x-www-form-urlencoded',
        async: true,
        data: {
            foodId:"123456789"
        },
        success:function(res){
            console.log(res);
        },
        error:function(){
        }
    });
}
/**
 * 根据菜单名查询菜单
 */
function getFoodByName(){
    $.ajax({
        url: '/food/getFoodByName',
        type: 'post',
        contentType:'application/x-www-form-urlencoded',
        async: true,
        data: {
            foodName:"意大利面"
        },
        success:function(res){
            console.log(res);
        },
        error:function(){
        }
    });
}


