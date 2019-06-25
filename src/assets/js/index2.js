//actShowAllFood();
$('.fa-minus-circle').click(function () {
    $(this).parent().parent().remove();
});
$('#saleItem td input').click(function () {
    if ($(this).val() == 0) {
        $(this).parent().parent().remove();
    }
});
$('.emptyOrder').click(function () {
    $('#saleItem td').parent().remove();
});
$('#c-downOrder').click(function () {
    if ($('#orderId').text() == "-------------" || ('$saleItem tr').length === 1) {
        alert('请点击生成新订单');
        return;
    }
    $('#downList').removeClass('zoomOut')
    $('#downList').show().addClass('bounceInDown');
    $('#orderTitle>label:last-child').text($('#orderId').text());
    getTableTypeNum(2);
});
$('#downListClose').click(function () {
    $('#downList').removeClass('bounceInDown');
    $('#downList').addClass('zoomOut');
    $('#downList').hide();
});
/*动作：查看订单*/
$('#c-waitOrder').click(function () {
    $('#orderList').removeClass('zoomOut')
    $('#orderList').show().addClass('bounceInDown');
});
/*动作：关闭查看订单*/
$('#orderListClose').click(function () {
    $('#orderList').removeClass('bounceInDown')
    $('#orderList').addClass('zoomOut');
    $('#orderList').hide();
})
$('#c-payOrder').click(function () {
    $('#downList').addClass('zoomOut');
    makeNewOrder();
});
/**
 * 动作：清空列表
 */
$('#c-cleanTable').click(function () {
    actCleanTable();
});
/**
 * 动作：删除订单
 */
$('#deleteOrder').click(function () {
    dropOrder();

});
/**
 * 动作:显示不同类型桌子的可用号
 */
$('#tableType>li').click(function () {
    var o = $(this).children('div:first-child');
    var num = parseInt(o.text());
    $('#select>li:first').text(o.text());
    getTableTypeNum(num);
});

/**
 * 动作：新订单
 */
$('#c-newOrder').click(function () {
    var orderId = utilGetTimeStamp();
    $('#orderId').text(orderId);
})

/**
 * 动作：点菜数量显示同步1
 */
function actSynchronizeNum1(li) {
    if ($('#orderId').text() == "-------------") {
        alert('请点击生成新订单');
        return;
    }
    if ($(li).children('span').length > 0) {
        var num = parseInt($(li).children('span').text() + '');
        $(li).children('span').text(num + 1);
    } else {
        $(li).append('<span>1</span>');
    }
    var $hasClass = $(li).attr('class');
    if ($('.' + $hasClass).length > 1) {
        var linkInput = $('#saleItem').find('tr.' + $hasClass + ' td input');
        var n = linkInput.val() - 0 + 1;
        linkInput.val(n);
        linkInput.parent().next().text(n * ($(li).find('div label').text() - 0));
    } else {
        $('#saleItem').append('' +
            '<tr class="' + $(li).attr('class') + '">' +
            '<td><i class="fa fa-minus-circle"></i></td>' +
            '<td>' +
            '<div>' + $(li).find('div:first-child').text() + '</div>' +
            '<div>（普通口味）</div>' +
            '</td>' +
            '<td>' +
            '<input type="number" value="1" min="0" onchange="actSynchronizeNum2(this)">' +
            '</td>' +
            '<td>' + $(li).find('div label').text() + '</td>' +
            '</tr>');
    }
    actCalcTotal();
}

/**
 * 动作：点菜数量显示同步2 表格
 */
function actSynchronizeNum2(inputNum) {
    var n = $(inputNum).val() - 0;
    var $hasClass = $(inputNum).parent().parent().attr('class');
    var linkSpan = $('#foodList>ul').find('li.' + $hasClass + ' span');
    var price = linkSpan.prev().children('label').text() - 0;
    if (n === 0) {
        $(inputNum).parent().parent().remove();
        linkSpan.remove();
    } else {
        linkSpan.text(n);
        $(inputNum).parent().next().text(n * price);
    }
    actCalcTotal();
}

/**
 * 动作：总计
 */
function actCalcTotal() {
    var tableList = $('#saleItem tr');
    var sum = 0;
    var n = 0;
    tableList.each(function (i, tr) {
        sum = $(tr).find('td:last-child').text() - 0 + sum;
        if (i != 0) {
            n = $(tr).find('td>input').val() - 0 + n;
        }
    });
    $('#calcTotal').text(sum);
    $('#saleNum').text(n);
}

/**
 * 动作:清空列表
 */
function actCleanTable() {
    console.log("删除");
    var tableList = $('#saleItem tr');
    $(tableList).each(function (i, tr) {
        if (i != 0) {
            $(tr).remove();
        }
    });
    $('#foodList ul li span').remove();

}

/**
 * 动作：挂单、删除订单
 */
function dropOrder() {
    $('#orderId').text('-------------');
    actCleanTable();
}

/**
 * 动作：选择桌子
 */
function selectTable(li) {
    if ($('#select>li').index(li) == 0) {
        return;
    }
    $(li).addClass('select')
        .siblings().removeClass('select');
    $('#selectedTb>label:last-child').text($(li).text());
}


/**
 * 工具：获取当前时间戳
 */
function utilGetTimeStamp() {
    var timestamp = (new Date()).getTime();
    return timestamp;
}


/**
 * 接口-----------------------------------------------
 */
function itfGetAllFood() {
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

/**
 * 接口：获取不同桌子的可用数
 */
function getTableTypeNum(num) {
    $.ajax({
        url: '/sale/getDiningTableByNum',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        async: true,
        data: {
            dtt_num: num
        },
        success: function (res) {
            var tbList = res.dataList;
            $('#select>li').each(function (i, li) {
                if (i != 0) {
                    $(li).remove();
                }
            });
            $(tbList).each(function (i, t) {
                $('#select').append('' +
                    '<li onclick="selectTable(this)">' +
                    '<label class="tableNum">' + t.dt_Id + '</label>' +
                    '<label>号桌</label>' +
                    '</li>');

            });
            $('#select>li:nth-child\(2\)').addClass('select');
            $('#selectedTb>label:last-child').text($('#select>li:nth-child\(2\)').text());
        },
        error: function () {
        }
    });
}

/**
 * 接口：生成新订单
 */
function makeNewOrder() {
    var sltItemList = [];
    $('#saleItem>tr').each(function (i, $tr) {
        console.log("进入了！");
        if (i === 0) ;
        else {
            console.log("进入根据id获取食物ajax");
            $.ajax({
                url: '/food/getFoodById',
                type: 'post',
                contentType: 'application/x-www-form-urlencoded',
                async: true,
                data: {
                    foodId: $($tr).attr('id')
                },
                success: function (res) {
                    console.log("根据id获取食物增添列表项成功");
                    var sltItem = {
                        sli_id: '',
                        sale: {
                            saleId: $('#orderId').text()
                        },
                        food: res.data,
                        sli_num: $($tr).find('td input').val(),
                        sli_spicy: $($tr).find('td div:last-child').text(),
                        is_export: 0
                    }
                },
                error: function () {
                    alert("根据id获取食物增添列表项出错");

                }
            });
        }
    });
    /*
    $.ajax({
        url: '/sale/makeNewSale',
        type: 'post',
        contentType:'application/json',
        async: true,
        data: JSON.stringify({
            sale:{
                sale_id: $('#orderId').text(),
                dinners_num: $('#dinersNum').val(),
                is_complete: 0,
                sale_time: new Date(),
                total_amt: $('#calcTotal').text() - 0,
                diningTable:null
            },
            saleLineItemList:sltItemList
        }),
        success:function(res){
            console.log(res);
        },
        error:function(){

        }


    });*/
}


//订单头显示当前时间
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