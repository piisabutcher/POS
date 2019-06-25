<template>
  <div id="statistics">
    <div id="condition">
      <el-form label-position="top">
        <el-radio-group v-model="type">
          <div class="section">
            <el-radio label="类别"><label>类别</label></el-radio>
            <el-dropdown split-button @command="catalogChange">
              所有
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="(item,index) in catalogList"
                                  :key="index"
                                  :command="item"
                >{{item.catalogName}}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div class="section">
            <el-radio label="周期"><label>周期</label></el-radio>
            <el-checkbox-group v-model="cycleTime" >
              <div class="date">
                <el-checkbox label="年"
                             name="type"
                             @change="cycleTimeChange()">
                </el-checkbox>
                <el-date-picker
                  v-model="time.year.value"
                  align="right"
                  type="year"
                  format="yyyy年"
                  placeholder="选择年"
                  :disabled="time.year.disabled">
                </el-date-picker>
              </div>
              <div class="date">
                <el-checkbox label="月"
                             name="type"
                             @change="cycleTimeChange()">
                </el-checkbox>
                <el-date-picker
                  v-model="time.month.value"
                  type="month"
                  placeholder="选择月"
                  :disabled='time.month.disabled'>
                </el-date-picker>
              </div>
              <div class="date">
                <el-checkbox label="周"
                             name="type"
                             @change="cycleTimeChange()">
                </el-checkbox>
                <el-date-picker
                  v-model="time.week.value"
                  type="week"
                  format="yyyy年第W周"
                  placeholder="选择周"
                  :disabled="time.week.disabled">
                </el-date-picker>
              </div>
              <div class="date">
                <el-checkbox label="日"
                             name="type"
                             @change="cycleTimeChange()">
                </el-checkbox>
                <el-date-picker
                  v-model="time.day.value"
                  type="date"
                  placeholder="选择日期"
                  :disabled="time.day.disabled">
                </el-date-picker>
              </div>
            </el-checkbox-group>

          </div>
          <div class="section">
            <el-form-item label="指标"></el-form-item>
            <el-radio-group class="rate" v-model="quota">
              <el-radio-button label="num">数量</el-radio-button>
              <el-radio-button label="rate">销售占额</el-radio-button>
              <el-radio-button label="total">总销售额</el-radio-button>
            </el-radio-group>
          </div>
          <div class="section">
            <el-radio-group class="chart"
                            v-model="chart">
              <el-radio label="curve">曲线图</el-radio>
              <el-radio label="strip">条形图</el-radio>
              <el-radio label="pie">饼状图</el-radio>
            </el-radio-group>
          </div>

        </el-radio-group>
      </el-form>
      <div class="bottom">
        <el-button round @click="createChart">生成报表</el-button>
      </div>
    </div><!--
  --><div id="charts">
    <Charts :options="options" :styles="styles" ref="chart"></Charts>
  </div>
  </div>

</template>

<script>
  import Charts from './Charts'
  import HighCharts from 'highcharts'
  export default {
      name: "Statistics",
      components:{Charts},
      created() {
        this.today = this.toFormat(this.today);
        this.time.year.value = this.today;
        this.$axios.get('/api/catalog/getAllCatalog').then(res => {
          this.catalogList = res.data.dataList;
          this.catalog = this.catalogList[0].catalogName;
        });
        this.createChart();
      },
      data() {
        return {
          today: Date.now(),
          type: '周期',//统计方式,按类别或按周期
          catalogList: [],
          catalog: "",
          cycleTime: ["年"],
          time: {
            year: {
              value: '',
              disabled: false
            },
            month: {
              value: '',
              disabled: true
            },
            week: {
              value: '',
              disabled: true
            },
            day: {
              value: '',
              disabled: true
            },
          },
          quota: "num",
          chart: "strip",
          options: {},
          styles: ''

        }
      },
      methods: {
        toFormat: function (input) {//转换日期格式·
          let d = new Date(input);
          let year = d.getFullYear();
          let month = d.getMonth() + 1;
          let day = d.getDate() < 10 ? '0' + d.getDate() : '' + d.getDate();
          return year + '-' + month + '-' + day;
        },
        createChart() {
          //读取表单数据
          let subCycle;//若按周期，提交的时间
          let flag = this.type === '周期';//标识统计指标，true表示按类别，false表示按周期
          switch (this.cycleTime[0]) {
            case "年": {
              subCycle = this.time.year.value;
            }
              break;
            case "月": {
              subCycle = this.time.month.value;
            }
              break;
            case "周": {
              subCycle = this.time.week.value;
            }
              break;
            case "日": {
              subCycle = this.time.day.value;
            }
              break;
          }
          subCycle = this.toFormat(subCycle);
          if (flag) {
            this.$axios.post('/api/report/statisticsByTimeZoneAll', {
              subCycle: subCycle,
              quota: this.quota,
              cycleTime: this.cycleTime[0]
            }).then(res => {
              switch (this.chart) {
                case "curve": {
                  this.initCurve(res.data, subCycle)
                }
                  break;
                case "strip": {
                  this.initStrip(res.data, subCycle)
                }
                  break;
                case "pie": {
                  this.initPie(res.data, subCycle)
                }
                  break;
              }
            }).catch(function (err) {
            });

          }
        },
        catalogChange(item) {
          this.catalog = item.catalogName;
        },
        cycleTimeChange: function () {//将复选框改为单选框
          let temp = this.cycleTime[this.cycleTime.length - 1];
          this.cycleTime.length = 1;
          this.cycleTime[0] = temp;
          for (let index in this.time) {
            this.time[index].disabled = true;
          }
          switch (this.cycleTime[0]) {
            case "年": {
              this.time.year.value = this.today;
              this.time.year.disabled = false;
            }
              break;
            case "月": {
              this.time.month.value = this.today;
              this.time.month.disabled = false;
            }
              break;
            case "周": {
              this.time.week.value = this.today;
              this.time.week.disabled = false;
            }
              break;
            case "日": {
              this.time.day.value = this.today;
              this.time.day.disabled = false;
            }
              break;
          }
        },
        initSeries(data, type) {
          switch (type) {
            case 1: {
              let mySeries = [];
              for(let prop in data){

              }

            }break;
            case 2: {
              let mySeries = [];
              for (let prop in data) {
                let seriesItem = {
                  name: prop,
                  data: data[prop]
                };
                mySeries.push(seriesItem);
              }
              return mySeries;
            }
            case 3: {

            }
          }
        },
        getTitle(subCycle){
          let type;
          if(this.quota === "num")type = "销售量";
          else if(this.quota === "rate")type = "销售占额";
          else if(this.quota === "total")type = "销售总额";
          let time;
          if(this.cycleTime[0] === "年"){
            time = subCycle.substring(0, 4) + "年";
          }else if(this.cycleTime[0] === "月"){
            time = subCycle.substring(0, 4) + "年" + subCycle.charAt(5) + "月";
          }else if(this.cycleTime[0] === "日"){
            time = subCycle;
          }else  if(this.cycleTime[0] === "周"){
            time = subCycle + "近一周";
          }
          return {
            type: type,
            time: time
          }
        },
        //曲线图
        initCurve(data, subCycle) {
          let mySeries = this.initSeries(data.series, 2);
          let type = this.getTitle(subCycle).type;
          let time = this.getTitle(subCycle).time;
          let myCurve = {
            title: {
              text: time  + '菜品'+ type +'折线图',
              categories: data.categories
            },
            xAxis: {
              title: {
                text: data.xAxis
              },
              categories: data.categories
            },
            yAxis: {
              title: {
                text: data.yAxis
              }
            },
            legend: {
              layout: 'vertical',
              align: 'right',
              verticalAlign: 'middle'
            },
            /*plotOptions: {
              series: {
                label: {
                  connectorAllowed: false
                },
                pointStart: 2010
              }
            },*/
            series: mySeries,
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
          this.options = myCurve;


        },
        //条形图
        initStrip(data, subCycle) {
          let s = this.initSeries(data.series, 2);
          let type = this.getTitle(subCycle).type;
          let time = this.getTitle(subCycle).time;
          let myOptions = {
            chart: {
              type: 'column'
            },
            title: {
              text: time  + '菜品'+ type +'百分比堆叠柱形图'
            },
            xAxis: {
              title: {
                text: data.xAxis
              },
              categories: data.categories
            },
            yAxis: {
              min: 0,
              title: {
                text: data.yAxis
              },
            },
            tooltip: {
              pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>' +
                '({point.percentage:.0f}%)<br/>',
              shared: true
            },
            plotOptions: {
              column: {
                stacking: 'percent'
              }
            },
            series: s
          };
          this.options = myOptions;
        },
        //饼状图
        initPie(data) {
          let s = this.initSeries(data.series, 1);
          let ctlPie = {
            chart: {
              plotBackgroundColor: null,
              plotBorderWidth: null,
              plotShadow: false,
              type: 'pie'
            },
            title: {
              text: '菜品销售占额饼状图'
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
          this.options = ctlPie;
        },
      }
  }
</script>

<style lang="scss">
  #statistics{
    background-color: #fdfdfd;
    width: 100%;
    height: calc(100% - 70px);
    #condition{
      display: inline-block;
      border-right: 1px solid #eee;
      width: 30%;
      height: 100%;
      background-color: #fafafa;
      position: relative;
      .el-form{
        width: calc(100 - 40px);
        padding: 10px;
        .el-radio-group{
          width: calc(100% - 40px);
        }
        .section{
          padding: 10px 20px;
          width: 100%;
          background-color: #fff;
          border: 1px solid #ebebeb;
          margin-bottom: 5px;
          .el-form-item{
            font-weight: bold;
            margin: 0;
          }
          .el-radio{
            label{
              display: inline-block;
              font-weight: bold;
              margin: 10px 0;
            }
          }
          .el-checkbox-group{
            margin-top: 10px;
            border: {
              top: 1px dashed #d2d2d2;
              bottom: 1px dashed #d2d2d2;
            };
          }
          .date{
            padding: 10px 0;
            border-bottom: 1px dashed #d2d2d2;
          }
          .rate{
            width: 100%;
            display: inline-block;
          }
          .chart{
            padding: 10px 0;
          }
        }
      }
    }
    .bottom{
      position: absolute;
      bottom: 0;
      width: calc(100% - 20px);
      text-align: right;
      background-color: #fff;
      padding: 10px;
      border-top: 1px solid #d2d2d2;
      .el-button{
        background-color: #af0d10;
        color: white;
      }
    }
    #charts{
      display: inline-block;
      width:calc(70% - 21px);
      height: calc(100% - 20px);
      background-color: #e2e9f1;;
      vertical-align: top;
      text-align: center;
      padding: 10px;
    }
  }
</style>
