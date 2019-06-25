let catalogList = [//菜品
  {"catalogId":"A","catalogName":"意粉","catalogImg":""},
  {"catalogId":"B","catalogName":"饮品","catalogImg":""},
  {"catalogId":"C","catalogName":"甜品","catalogImg":""},
  {"catalogId":"D","catalogName":"盖饭","catalogImg":""},
  {"catalogId":"E","catalogName":"焗饭","catalogImg":""},
  {"catalogId":"F","catalogName":"扒饭","catalogImg":""},
  {"catalogId":"G","catalogName":"沙拉","catalogImg":""}
];
let table = [{
  name: '意粉',
  data: [500,453,422,515,521]
}, {
  name: '饮品',
  data: [78,123,224,334,456]
}, {
  name: '甜品',
  data: [48,89,140,125,135]
}, {
  name: '盖饭',
  data: [254,234,234,242,238]
}, {
  name: '焗饭',
  data: [414,485,454,380,395]
},{
  name: '扒饭',
  data: [354,390,360,435,498]
},{
  name: '沙拉',
  data: [457,411,351,365,380]
}];
let ctlCurve = {
  type:'bar',
  title:{
    text : "1~5月销售利润折线图"
  },
  yAxis: {
    title:{
      text: "月营业额（万元）"
    },
    labels:{
      step:1
    }
  },
  xAxis:{
    title:{
      text: "月份"
    },
    categories:['一月', '二月', '三月', '四月', '五月']
  },
  legend:{
    layout: 'vertical',
    align: 'right',
    verticalAlign: 'middle'
  },
  plotOptions: {
    series:{
      label:{
        connectorAllowed: false,
      }
    }
  },
  series: [{
    name: '月净利润',
    data: [20.75, 25.46, 23.70, 25.10, 24.20]
  }],
  responsive: {
    rules: [{
      condition: {
        maxWidth: 500
      },
      chartOptions: {
        legend: {
          layout: 'horizontal',
          align: 'center',
          verticalAlign: 'bottom'
        }
      }
    }]
  }
};
let ctlStrip = {
  /*chart: {
    type: 'column'
  },
  title: {
    text: '1~5月不同菜品类别的销售总额柱形图'
  },
  xAxis: {
    categories: [
      '一月','二月','三月','四月','五月'
    ],
    crosshair: true
  },
  yAxis: {
    min: 0,
    title: {
      text: '月销售总额（份）'
    }
  },
  tooltip: {
    // head + 每个 point + footer 拼接成完整的 table
    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
      '<td style="padding:0"><b>{point.y}</b></td></tr>',
    footerFormat: '</table>',
    shared: true,
    useHTML: true
  },
  plotOptions: {
    column: {
      borderWidth: 0
    }
  },
  series: table,*/
  chart: {
    type: 'column'
  },
  title: {
    text: '2017年5月菜品销售量百分比堆叠柱形图'
  },
  xAxis: {
    categories: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],
  },
  yAxis: {
    min: 0,
    title: {
      text: '菜品消费总量'
    }
  },
  tooltip: {
    pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>' +
      '({point.percentage:.0f}%)<br/>',
    //:.0f 表示保留 0 位小数，详见教程：https://www.hcharts.cn/docs/basic-labels-string-formatting
    shared: true
  },
  plotOptions: {
    column: {
      stacking: 'percent'
    }
  },
  series: [{
    name: '意粉',
    data: [5,3,4,7,2,3,4,4,7,6,4,8,6,4,2,6,5,3,6,8,9,5,6,4,4,9,4,5,6,8,7]
  }, {
    name: '饮品',
    data: [1,9,5,3,8,7,5,7,5,6,2,3,3,9,9,5,7,6,2,1,2,4,8,7,7,2,1,6,9,7,6]
  }, {
    name: '甜品',
    data: [6,2,6,3,7,8,7,1,9,1,7,2,3,8,8,4,3,1,6,4,6,9,1,2,9,3,4,2,2,4,8]
  },{
    name: '盖饭',
    data: [8,5,8,2,5,4,2,9,4,2,5,4,7,8,7,1,8,5,7,2,3,8,6,5,8,8,2,8,1,7,9]
  },{
    name: '焗饭',
    data: [6,1,6,3,9,9,6,5,4,6,6,5,2,9,3,9,3,9,4,9,8,8,7,2,3,5,3,3,8,2,8]
  },{
    name: '沙拉',
    data: [5,4,8,2,2,2,4,2,7,1,4,3,5,3,9,9,5,3,2,2,4,3,5,2,6,3,4,5,1,4,9]
  },{
    name: '扒饭',
    data: [5,5,1,4,8,4,7,1,9,8,4,1,7,2,7,5,4,6,2,6,8,3,8,5,5,3,9,9,2,2,7]
  }]
};
let ctlPie = {
  chart: {
    plotBackgroundColor: null,
    plotBorderWidth: null,
    plotShadow: false,
    type: 'pie'
  },
  title: {
    text: '2018年8月菜品销售占额'
  },
  tooltip: {
    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
  },
  plotOptions: {
    pie: {
      allowPointSelect: true,
      cursor: 'pointer',
      dataLabels: {
        enabled: true,
        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
        style: {
          color: 'black'
        }
      }
    }
  },
  series: [{
    name: 'Brands',
    colorByPoint: true,
    data: [{
      name: '意粉',
      y: 11.41,
      sliced: true,
      selected: true
    }, {
      name: '饮品',
      y: 22.84
    }, {
      name: '甜品',
      y: 14.85
    }, {
      name: '盖饭',
      y: 10.67
    }, {
      name: '扒饭',
      y: 13.78
    }, {
      name: '沙拉',
      y: 10.04
    }, {
      name: '焗饭',
      y: 12.6
    }, {
      name: '其他',
      y: 3.81
    }]
  }]









};
let ctlArea = {
  chart: {
    type: 'area'
  },
  title: {
    text: '2019年5月10日各时段不同菜品销售量面积图'
  },
  xAxis: {
    categories: ['8:00-10:00', '10:00~12:00', '12:00~14:00', '14:00~16:00'
      , '16:00~18:00', '18:00~20:00', '20:00~22:00','22:00~24:00'],
    tickmarkPlacement: 'on',
    title: {
      enabled: false
    }
  },
  yAxis: {
    title: {
      text: '销售量（份）'
    },
  },
  tooltip: {
    split: true,
  },
  plotOptions: {
    area: {
      stacking: 'normal',
      lineColor: '#666666',
      lineWidth: 1,
      marker: {
        lineWidth: 1,
        lineColor: '#666666'
      }
    }
  },
  series: [{
    name: '意粉',
    data: [0,0,0,1,0,2,1,2]
  }, {
    name: '饮品',
    data: [0,0,1,2,2,3,2,0]
  }, {
    name: '甜品',
    data: [1,0,2,5,4,8,3,1]
  }, {
    name: '盖饭',
    data: [0,2,1,4,6,3,1,0]
  }, {
    name: '焗饭',
    data: [0,3,1,6,0,3,0,1]
  },{
    name: '扒饭',
    data: [2,1,5,5,2,0,1,0]
  },{
    name: '沙拉',
    data: [2,1,5,2,2,1,1,0]
  }]

};






export {
  ctlCurve,
  ctlStrip,
  ctlPie,
  ctlArea,
  catalogList
}

