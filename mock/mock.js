import Mock from 'mockjs'
import {ctlStrip, ctlPie, ctlArea,ctlCurve} from './chartData.js'
import {catalogList} from "./catalogData.js";

Mock.mock("/catalog/getAllCatalog",{
  code:200,
  msg:"OK",
  data:null,
  dataList: catalogList,
  success:true
});
Mock.mock("/catalog/getStatistics","post",function (req) {
  let myTitle = '';
  let options = ctlCurve;
  req = JSON.parse(req.body);
  if(req.flag){
    switch (req.Chart) {
      case 'curve':{ options = ctlCurve; }break;
      case 'strip':{ options = ctlStrip; }break;
      case 'pie':{ options = ctlPie }break;
      case 'area':{ options = ctlArea }break;
    }
  }
  return options;
});

let data = {
  subCycle :'2019-01-01',
  cycleTime: '年',
  quota: 'num'
};




















Mock.mock("/getOptions",{
  code:200,
  msg:"OK",
  data:null,
  dataList: {
    chart: {
      type:'column'//指定图表的类型，默认是折线图（line）
    },
    credits: {
      enabled:false
    },//去掉地址
    title: {
      text: '测试' //指定图表标题
    },
    colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00',
      '#24CBE5' ],
    xAxis: {
      categories: ['1号', '2号', '3号','3号','3号'] //指定x轴分组
    },
    yAxis: {
      title: {
        text: '最近七天', //指定y轴的标题
      },
    },
    plotOptions: {
      column: {
        colorByPoint:true
      },
    },
    series: [{ //指定数据列
      name: '小明',
      data: [{
        y:1000,
        color:"red"}, 5000, 4000,5000,2000] //数据
    }]
  },
  success:true

});
