/**
 * 初始默认显示员工管理页面
 */
//showUser(0);
/**
 * 初始化css样式
 */
$('#left>ul>li:first').addClass('li-click')
    .children('div').children().addClass('i-click')
/**
 * 样式控制
 */
$('#left>ul>li').mouseover(function () {
    if (this.className != "li-click") {
        $(this).addClass('li-hover')
            .children('div').children().addClass('i-hover');
    }
    $(this).children('ul').stop(true).animate({
        width: "show",
    }, 200);
}).mouseleave(function () {
    if ($(this).hasClass("li-hover")) {
        $(this).removeClass('li-hover')
            .children('div').children().removeClass('i-hover');
    }
    $(this).children('ul').stop(true).animate({
        width: "hide",
    }, 100);
});
//切换列表
$('#left>ul>li').click(function () {
    $(this).addClass('li-click').siblings().removeClass('li-click');
    $(this).children('div').children().addClass('i-click')
    $(this).siblings().children('div').children().removeClass('i-click');
    var index = $(this).index();
    $('#content>div:eq(' + index + ')').show().removeClass("slideOutDown").addClass("slideInUp")
        .siblings().hide().removeClass("slideInUp");
    switch (index) {
        case 0:
            showUser(0);
            break;
        case 1:
            showCatelog();
            break;
    }
});
/**
 * 分页按钮
 */
$('#first').click(function () {
    showUser(0);
    $('#now').text(1);
});
$('#last').click(function () {
    showUser(1);
    $('#now').text(2);
});
$('#prev').click(function () {
    var page = $('#now').text();
    if (page != 1) {
        page = page - 1;
        showUser(page - 1);
        $('#now').text(page);
    }
});
var userPage;//记住用户列表页数，全局
$("#next").click(function () {
    var page = $('#now').text();
    if (page != userPage) {
        page = (page - 0) + 1;
        showUser(page - 1);
        $('#now').text(page);
    }
})

/**
 * 控制分页
 */
function pageController(page, size, itface) {
    var data;
    $.ajax({
        url: itface,
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: false,
        data: {
            page: page,
            size: size
        },
        success: function (res) {
            if (res.code === 200) {
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
function showUser(page) {
    $('#content-worker table tr:first-child').siblings().remove();
    var res = pageController(page, 14, "/user/getUserList");
    var data = res.dataList.content;//数据
    userPage = res.dataList.totalPages;//总页数
    $('#content-worker').show();
    $(data).each(function (i, u) {
        var gender = (u.gender === 1) ? '男' : '女';
        var power = (u.power === 0) ? '员工' : '管理员';
        $('#content-worker table').append(
            '<tr>' +
            '<td>' + u.userId + '</td>' +
            '<td>' + u.password + '</td>' +
            '<td>' + u.userName + '</td>' +
            '<td>' + gender + '</td>' +
            '<td>' + u.tel + '</td>' +
            '<td>' + power + '</td>' +
            '<td>' +
            '<button class="edit" id="e' + u.userId + '" onclick=""><i class="fa fa-edit"></i></button>' +
            '<button class="delete" id="d' + u.userId + '" onclick="vDelete(this)"><i class="fa fa-remove"></i></button>' +
            '</td>' +
            '</tr>');
    });
}

function vDelete(e) {
    var userId = e.parentNode.parentNode.firstChild.innerHTML;
    deleteUser(userId);
    location.reload();
};

/**
 * 修改用户信息
 */
function editUser(userId) {
    $.ajax({
        url: '/user/editUser',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({
            userId: userId
        }),
        success: function (res) {

        },
        error: function () {

        }

    });
}

/**
 * 删除用户
 */
function deleteUser() {
    $.ajax({
        url: '/user/deleteUser',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({}),
        success: function (res) {
            console.log(res.code);
            console.log("删除请求执行成功！");
        },
        error: function () {
            console.log("出错啦！");

        }
    });
}

/**
 * 点击菜品查询菜单
 */
function changeCtlView(e) {
    $(e).addClass('ctlHover').siblings().removeClass('ctlHover');
    showOneCatalog($(e).text());
}

/**
 查询所有菜品并列出
 **/
function showCatelog() {
    $('#catalogList>ul').empty();
    var data = getAllCatalog().dataList;
    var l = data.length;
    $(data).each(function (i, ctl) {
        $('#catalogList>ul').append(
            '<li id=ctl' + i + ' onclick="changeCtlView(this)">' + ctl.catalogName + '</li>');
        //模态框内菜品可提前填充，不必多次读取同一数据
        if (i == 0) {
            $('#detail-catalog>td:last>select').append('<option selected="selected">' + ctl.catalogName + '</option>');
        } else {
            $('#detail-catalog>td:last>select').append('<option>' + ctl.catalogName + '</option>');
        }

    });
    changeCtlView($('#catalogList>ul>li').first());
    //$('#content-food>ul').append('<li id="allCatalog">查看全部</li>');
}

/**
 * 接口：查询所有菜品
 */
function getAllCatalog() {
    var data;
    $.ajax({
        url: '/catalog/getAllCatalog',
        type: 'get',
        contentType: 'application/json',
        async: false,
        success: function (res) {
            data = res;
            return;
        },
    });
    return data;
}

/**
 * 接口：根据catalog查询Food
 */
function getFoodByCatalog(catalogName) {
    var data;
    $.ajax({
        url: '/food/getFoodByCatalog',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: false,
        data: {
            catalogName: catalogName
        },
        success: function (res) {
            //渲染
            data = res;
            return;
        },
        error: function () {
        }
    });
    return data;
}

/**
 * 显示某一类菜单
 */
function showOneCatalog(catalog) {
    $("#catalog-food>ul").empty();
    var data = getFoodByCatalog(catalog);
    $(data.dataList).each(function (i, f) {
        $("#catalog-food>ul").append(
            '<li>' +
            '<div>' +
            '<img src="../img/list/' + f.image + '">' +
            '<div class="animated">' +
            '<span onclick="showModel(this)"><i class="fa fa-cog"></i></span>' +
            '<span>' +
            '<input type="hidden" value="' + f.catalog.catalogName + '">' +
            '<input type="hidden" value="' + f.foodId + '">' +
            '<h2>' + f.foodName + '</h2>' +
            '<h3>售价：￥' + f.price + '</h3>' +
            '<h4>供应时段' + f.supPeriod + '</h4>' +
            '</span>' +
            '</div>' +
            '</div>' +
            '</li>');
    });
}

/**
 打开food设置模态框
 */
function showModel(e) {
    modelControl(true);
    var $eData = $(e).siblings();
    var imgSrc = $(e).parent().siblings('img').attr("src");
    $('#detail-img').attr("src", imgSrc);
    $('#detail-name>td').text($eData.children('h2').text());
    $('#detail-price>td:last>input').val($eData.children('h3').text());
    $('#detail-catalog>td:last>select').val($eData.children('input:last').val());
    $('#detail-id>td:last').text($eData.children('input:last').val());
}

$('.closeBar').click(function () {
    modelControl(false);
});

function modelControl(show) {
    if (show) {
        $('#food-modal').show()
            .addClass("fadeInRight").removeClass("fadeOutRight");
    } else {
        $('#food-modal').removeClass("fadeInRight")
            .addClass("fadeOutRight");
    }
}


/**
 * 新增菜品
 */
$('#ca3').click(function () {
    //addCatalog()
});

function addCatalog() {
    $.ajax({
        url: '/catalog/saveCatalog',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            ctlName: '烧烤'
        },
        success: function (res) {
            console.log(res);
        },
        error: function () {
        }

    });
}

$('#ca4').click(function () {
    //deleteCatalog();
});

/**
 * 删除菜品
 */
function deleteCatalog() {
    $.ajax({
        url: '/catalog/deleteCatalog',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({
            catalogId: "H"
        }),
        success: function (res) {
            console.log(res);
        },
        error: function () {

        }

    });
}

$('#ca5').click(function () {
    //getCatalogByCatalogName();
});

/**
 * 根据菜品名查询菜品
 */
function getCatalogByCatalogName() {
    $.ajax({
        url: '/catalog/getCatalogByName',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            ctlName: "意粉"
        },
        success: function (res) {
            console.log(res);
        },
        error: function () {
        }
    });
}

$('#ca2').click(function () {
    //editCatalog();
});

/**
 * 修改菜品信息
 */
function editCatalog() {
    $.ajax({
        url: '/catalog/updateCatalog',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({}),
        success: function (res) {

        },
        error: function () {

        }

    });

}

/**
 * 新增菜单
 */
$('#fa3').click(function () {
    //addFood();
});

function addFood() {
    $.ajax({
        url: '/food/saveFood',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({
            foodId: '123321',
            foodName: 'edit',
            image: '289983',
            supPeriod: "14:00",
            price: 54,
            catalog: {
                catalogId: 'B'
            }
        }),
        success: function (res) {
            console.log(res);
        },
        error: function () {

        }

    });
}

/**
 * 删除菜单
 */
$('#fa4').click(function () {
    deleteFood();
});

function deleteFood() {
    $.ajax({
        url: '/food/deleteByFoodId',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            foodId: '1414141'
        },
        success: function (res) {
            console.log(res);

        },
        error: function () {

        }

    });
}

/**
 * 修改菜单
 */
$('#fa2').click(function () {
    editFood();
});

function editFood() {
    $.ajax({
        url: '/food/updateFood',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({
            foodId: 'F00000',
            foodName: '黑椒鸡扒饭'//不传参默认为空;
        }),
        success: function (res) {
            console.log(res);

        },
        error: function () {

        }

    });
}

/**
 * 查询所有菜单
 */
$('#fa1').click(function () {
    getAllFood();
});

function getAllFood() {
    $.ajax({
        url: '/food/getAllFood',
        type: 'get',
        contentType: 'application/json',
        async: true,
        success: function (res) {
            console.log(res);
        },
        error: function () {
        }
    });

}

$('#fa5').click(function () {
    getFoodByName();
});

/**
 * 根据菜单ID查询菜单
 */
function getFoodById() {
    $.ajax({
        url: '/food/getFoodById',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            foodId: "123456789"
        },
        success: function (res) {
            console.log(res);
        },
        error: function () {
        }
    });
}



