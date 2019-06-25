/**
 * 1.页面加载显示category，逐项显示在最左边的导航栏，点击某项菜品，即可根据category查询对应的food
 * 2.根据Catalog查询Food
 */
//方法：页面加载触发;显示所有菜品
funShowAllCatalog();

function funShowAllCatalog() {
    $.ajax({
        url: '/catalog/getAllCatalog',
        type: 'get',
        contentType: 'application/json',
        async: true,
        success: function (res) {
            $(res.dataList).each(function (i, c) {
                $('#left-bar>ul').append(
                    '<li id="' + c.catalogId +
                    '" onclick="funShowCatalogById(this)">' + c.catalogName + '</li>');
            })
        },
        error: function () {
            alert("服务器发生错误");
        }
    });
}

//方法：点击某项catalog触发;点击某个catalog显示该类别的食物
function funShowCatalogById(catalodLi) {
    $.ajax({
        url: '/food/getFoodByCatalog',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            catalogName: $(catalodLi).text()
        },
        success: function (res) {
            $('#foodList>div>table').empty();
            $(res.dataList).each(function (i, f) {
                $('#foodList>div>table').append(
                    '<tr class="' + f.foodId + '">' +
                    '<td>' + f.foodId + '</td>' +
                    '<td>' +
                    '<label>' + f.foodName + '</label>' +
                    '</td>' +
                    '<td>' +
                    '<input type="number" min="1" value="1"/>' +
                    '</td>' +
                    '<td>' +
                    '<span onclick="addSaleItem(this)"><i class="fa fa-arrow-right"></i></span>' +
                    '</td>' +
                    '</tr>');
            })
        },
        error: function () {

        }

    });
}


/**
 * 1.页面加载默认显示所有待选择的菜品，逐项显示在界面的左边，每行包括菜名，数量，和操作按钮
 * 2.增加新订单Sale
 * 3.往当前订单Sale中插入新纪录SaleLineItem
 * 4.从当前订单Sale中删除记录SaleLineItem
 * 5.删除订单Sale
 */
//方法：页面加载触发;显示所有食物
funShowAllFood();

function funShowAllFood() {
    $.ajax({
        url: '/food/getAllFood',
        type: 'get',
        contentType: 'application/json',
        async: true,
        success: function (res) {
            $(res.dataList).each(function (i, f) {
                $('#foodList>div>table').append(
                    '<tr class="' + f.foodId + '">' +
                    '<td>' + f.foodId + '</td>' +
                    '<td>' +
                    '<label>' + f.foodName + '</label>' +
                    '</td>' +
                    '<td>' +
                    '<input type="number" min="1" value="1"/>' +
                    '</td>' +
                    '<td>' +
                    '<span onclick="addSaleItem(this)"><i class="fa fa-arrow-right"></i></span>' +
                    '</td>' +
                    '</tr>');
            })
        },
        error: function () {
            alert("bad request……");
        }
    });
}

//方法：左侧选择了某一项食物并点击末尾箭头触发，增加新订单项
function addSaleItem(span) {
    if ($('#orderId').text() == "-------------") {
        //若当前没有正在操作的订单
        alert("请先点击生成新订单@-@")
    } else {
        $.ajax({
            url: '/sale/addNewSaleItem',
            type: 'post',
            contentType: 'application/json',
            async: true,
            data: JSON.stringify({
                sliId: "",
                is_export: 0,
                sli_num: Number($(span).parent().prev().children('input').val()),
                sli_spicy: 0,
                subtotal: 0,
                food: {
                    foodId: $(span).parent().parent().attr('class'),
                    foodName: $(span).parent().prev().prev().children('label').text(),
                },
                sale: {
                    saleId: $('#orderId').text()
                }
            }),
            success: function (res) {
                var data = res.data;
                var food = data.food;
                var sale = data.sale;
                $('#saleItem').append(
                    '<tr id="' + data.sli_id + '">' +
                    '<td><i class="fa fa-minus-circle" onclick="deleteSaleItem(this)"></i></td>' +
                    '<td>' +
                    '<div>' + food.foodName + '</div>' +
                    '<div>（普通口味）</div>' +
                    '</td>' +
                    '<td>' + food.price + '</td>' +
                    '<td>' + data.sli_num + '</td>' +
                    '<td>' + data.sub_total + '</td>' +
                    '</tr>');
                calcuTotal();
            },
            error: function () {
            }
        });

    }
}

//方法：删除订单项，点击某一订单项左侧的-按钮，将此项从数据库中删除。
function deleteSaleItem(fa) {
    var saleLineItemId = $(fa).parent().parent('tr').attr('id');
    $.ajax({
        url: '/sale/deleteSaleLineItem',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            saleLineItemId: saleLineItemId
        },
        success: function (res) {
            $(fa).parent().parent('tr').remove();
            calcuTotal();
            console.log(res);
        }
    });


}

//动作：点击生成新订单
$('#c-newOrder').click(function () {
    if ($('#orderId').text() == "-------------") {//检查当前是否有正在操作的订单
        console.log("当前没有操作订单");
    } else {
        let deleteOrderId = $('#orderId').text();
        if (confirm("此操作将会放弃当前正在进行的订单，是否继续？")) {//有bug
            $.ajax({
                url: '/sale/deleteSale',
                type: 'post',
                contentType: 'application/x-www-form-urlencoded',
                async: true,
                data: {
                    saleId: deleteOrderId
                },
                success: function (res) {
                    alert("删除成功！");

                },
                error: function () {

                }
            });
        } else {
            return;
        }
    }
    var orderId = utilGetTimeStamp();
    $('#orderId').text(orderId);
    $.ajax({
        url: '/sale/createNewSale',
        type: 'post',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({
            saleId: orderId,
            dinnersNum: 0,
            isComplete: 0,
            saleTime: null,
            totalAmt: 0,
            diningTable: null
        }),

        success: function (res) {
            console.log(res);
        },
        error: function () {
            console.log("服务器发生未知错误")

        }
    })
});

//方法：成功增加了一个新订单项触发,实时计算订单总数目和总金额
function calcuTotal() {
    $.ajax({
        url: '/sale/calcSaleTotal',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            saleId: $("#orderId").text()
        },
        success: function (res) {
            $('#saleNum').text(res.data.totalNum);
            $('#calcTotal').text(res.data.totalPrice);
        }
    });
}

//方法：删除订单
function deleteSale() {

}


/**
 * 用户选择好了订单后，进行选座，点击“选座”按钮，后边显示选择窗口，本店共有12张不同类型的桌子供客人选择
 * 窗口加载时，从后台读取大厅内所有桌子的信息，包括桌号、桌型和当前状态，不同状态的桌子显示为不同颜色
 * 进行当前订单客人的作为选择，点击某张空桌子后，选中的桌子颜色变为绿色，下方显示当前选中的桌子
 * 点击确定按钮后确认将该订单的客人分配到这张桌子，更新数据库
 */
//动作：点击了“选座”按钮触发
$('#c-selectSeat').click(function () {
    if ($('#orderId').text() == "-------------") {
        alert("当前没有操作的订单哦~")
        return;
    }
    $('#seatWindow').show();
    $('#orderTitle>label:last-child').text($('#orderId').text());
    $('#people table').empty();
    showSeatWindow();
})

//方法：显示或更新座位列表，点击“选座”或“确定”按钮时调用
function showSeatWindow() {
    $.ajax({
        url: '/diningTable/getAllDiningTable',
        type: 'get',
        contentType: 'application/json',
        async: true,
        success: function (res) {
            let data = res.dataList;
            $(data).each(function (i, table) {
                if ((i + 1) % 4 === 1) {
                    $('#people table').append(
                        '<tr>' +
                        '<td id="' + table.dt_Id + '" ' +
                        'class="' + (table.is_people === 0 ? "" : "usedSeat") + '" ' +
                        'onclick="selectTable(this)">' +
                        '<label>' + table.dt_Id + '号桌</label>' +
                        '<div>(' + table.diningTableType.dtt_num + '人桌)</div>' +
                        '<label>' + (table.is_people === 0 ? "空" : "已用") + '</label>' +
                        '</td>' +
                        '</tr>');
                } else {
                    $('#people table tr:last-child').append(
                        '<td id="' + table.dt_Id + '" ' +
                        'class="' + (table.is_people === 0 ? "" : "usedSeat") + '" ' +
                        'onclick="selectTable(this)">' +
                        '<label>' + table.dt_Id + '号桌</label>' +
                        '<div>(' + table.diningTableType.dtt_num + '人桌)</div>' +
                        '<label>' + (table.is_people === 0 ? "空" : "已用") + '</label>' +
                        '</td>'
                    )
                }
            })
        }
    });
}

//方法：选择某个空座位，点击某个空座位时触发
function selectTable(table) {
    $(table).parent().parent().find('td').removeClass("selectedSeat")
    $(table).addClass("selectedSeat");
    $('.choose').text($(table).attr("id") + "号桌");
}

//动作：更改订单的选座，点击了"确定”按钮时触发，判断当前订单是否已经分配了餐台
$('#updateSaleTable').click(function () {
    console.log($('#orderTitle').attr('class'));
    if ($('#orderTitle').attr('class')) {
        if (confirm('当前订单已选' + $('#orderTitle').attr('class') + "号桌,是否确认更换座位？")) {
            updateSaleTable();
            $('#' + $('#orderTitle').attr('class')).removeClass("usedSeat");
        } else {
            $("#" + parseInt($('.choose').text())).removeClass("selectSeat");//去掉绿色，有bug
            $('.choose').text($('#orderTitle').attr('class') + "号桌");
        }
    } else {
        updateSaleTable();
    }
});

//方法：更改订单的餐台
function updateSaleTable() {
    $.ajax({
        url: '/sale/updateSaleTable',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            saleId: $('#orderTitle>label:last-child').text(),
            dt_id: parseInt($(".choose").text()),
            dinner_num: $('#dinersNum').val()
        },
        success: function (res) {
            console.log(res);
            var table = res.data.diningTableList;
            $(table).each(function (i, table) {
                //table.dt_Id;
            })
            //$('#orderTitle').attr('class',table);
            $('#people table').empty();
            showSeatWindow();
        }
    });
}


/**
 * 结账场景
 * 1.点击结账按钮出现结账窗口,同步订单号和应收金额
 * 2.点击键盘上的数字同步到输入框中
 * 3.点击确定进行支付，将数据提交到后台并进行计算，返回值显示到界面
 * 4.点击完成交易关闭窗口
 */
$('#c-payOrder').click(function () {
    $('#payWindow').show();
    var saleId = $('#orderTitle>label:last').text();
    $('#paySaleId').text(saleId);
    $.ajax({
        url: '/sale/getSaleBySaleId',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            saleId: saleId
        },
        success: function (res) {
            var shouldPay = res.data.total_amt;
            $('#shouldPay').text(shouldPay);
        }
    });
});
$('.num').click(function () {
    console.log("点击啦@");
    var num = $('#inputPay').val() + $(this).text();
    $('#inputPay').val(num);
});
$('#calSubPay').click(function () {
    var realPay = $('#inputPay').val();
    $('#realPay').text(realPay);
    paySale($('#paySaleId').text(), realPay);
});

function paySale(saleId, realPay) {
    $.ajax({
        url: '/sale/paySale',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            saleId: saleId,
            pay_num: realPay
        },
        success: function (res) {
            console.log(res);
            $('#returnPay').text(res.data.pay_change);
        }
    });

}


/**
 * 工具：获取当前时间戳作为订单号
 */
function utilGetTimeStamp() {
    var timestamp = (new Date()).getTime();
    return timestamp;
}

/**
 * 工具：事实显示当前时间
 */
startTime();

function startTime() {
    var today = new Date();//定义日期对象
    var yyyy = today.getFullYear();//通过日期对象的getFullYear()方法返回年
    var MM = today.getMonth() + 1;//通过日期对象的getMonth()方法返回年
    var dd = today.getDate();//通过日期对象的getDate()方法返回年
    var hh = today.getHours();//通过日期对象的getHours方法返回小时
    var mm = today.getMinutes();//通过日期对象的getMinutes方法返回分钟
    var ss = today.getSeconds();//通过日期对象的getSeconds方法返回秒
    // 如果分钟或小时的值小于10，则在其值前加0，比如如果时间是下午3点20分9秒的话，则显示15：20：09
    MM = checkTime(MM);
    dd = checkTime(dd);
    mm = checkTime(mm);
    ss = checkTime(ss);

    $('#time').html(yyyy + "/" + MM + "/" + dd + " " + hh + ":" + mm + ":" + ss);
    setTimeout('startTime()', 1000);//每一秒中重新加载startTime()方法
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}