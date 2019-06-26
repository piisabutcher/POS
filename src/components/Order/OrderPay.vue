<template>
    <div class="order-pay">
      <nav>
        <label>订单</label>
        <el-dropdown trigger="click" @command="orderChange">
      <span class="el-dropdown-link">
        {{this.mySaleId}}
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              v-for="item in saleList"
              :key="item.saleId"
              :command="item"
            >{{item.saleId}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span class="close"
              @click="closeOrderPay">
          <i class="fa fa-close"></i></span>
      </nav>
      <div class="content">
        <div class="oth">
          <div class="orderCount">
            <label>订单汇总</label>
            <span>￥{{mySale.total_amt}}</span>
            <div id="cheap">
              <label>可用优惠</label>

            </div>
          </div>
          <div></div>
        </div>
        <div class="calc">
          <div class="main">
            <ul>
              <li>
                <span>应收</span>
                <div>￥{{shouldPay}}</div>
              </li><li>
                <span>实收</span>
                <div>￥{{realPay}}</div>
              </li><li>
                <span>找零</span>
                <div>￥{{returnMoney}}</div>
              </li>
            </ul>
            <table>
              <tr v-for="(group,row) in calcKey">
                <td v-for="(item,column) in group">
                  <button v-if="column === 4" @click="pressCalc(column,item,false)"
                  >￥{{item}}</button>
                  <button v-else @click="pressCalc(column,item,false)"
                  >{{item}}</button>
                </td>
              </tr>
              <tr>
                <td v-for="(fb,index) in calcKeyFoot"
                    :colspan="index === 3?2:1">
                  <button :class="{'sub': index === 3}"
                          @click="pressCalc(index,fb,true)">{{fb}}</button>
                </td>
              </tr>
            </table>
          </div>
          <el-button @click="exitOrder">结束交易</el-button>
        </div>
      </div>









    </div>
</template>

<script>
    export default {
      name: "OrderPay",
      props:{
        currentSaleId:{
          type: String,
        },
        currentSale:{
          type: Object
        },
      },
      data(){
        return{
          saleList:[],
          mySale:{},
          mySaleId:'',
          calcKey:[
            ["现金支付","1","2","3","100"],
            ["支付宝","4","5","6","50"],
            ["微信支付","7","8","9","20"],
            ["银行卡","0","00",".","10"]
          ],
          calcKeyFoot:["积分支付","删除","清空","结算"],
          orderPrice:0,//订单实际价格
          shouldPay:0,//应付
          realPay:0,//实付
          returnMoney:0,//找零

          }
      },
      watch:{
        currentSale:function (value) {
          this.mySale = value;
          this.getUncompletedSale();

        },
        currentSaleId: function (value) {
          this.mySaleId = value;
        }
      },
      created() {
        this.initShow();
      },
      methods:{
        initShow(){
          this.getUncompletedSale();
          this.mySaleId = this.currentSaleId;
          this.mySale = this.currentSale;
          if(this.mySaleId === ''){
            this.mySale = this.saleList[0];
            this.$emit("showLinkSale",this.mySale);
          }
          this.countShouldPay();
        },
        closeOrderPay(){
          this.$router.back();
        },
        pressCalc(column, value, isFooter){
          if(isFooter){
            switch (column) {
              case 0:{}break;
              case 1:{//删除
                this.realPay =
                  this.realPay.substring(0 ,this.realPay.length - 1);
                if(this.realPay === "")
                  this.realPay = 0;
              }break;
              case 2:{//清空
                this.realPay = 0;
              }break;
              case 3:{//结算
                this.doPay();
              }break;
            }
          }else {//键入
            if(column > 0){
              if(column === 4){
                this.realPay = this.realPay===0?
                  value:(this.realPay - 0 + parseInt(value));
              }else{
                this.realPay = this.realPay===0?
                  value : this.realPay+value;
              }
            }
          }
        },
        getUncompletedSale(){
          this.$axios.get("/api/sale/getUncompletedSale")
            .then(res=>{
              this.saleList = res.data;
              if(this.mySaleId === ""){
                this.mySaleId = this.saleList[0].saleId;
                this.mySale = this.saleList[0];
              }
            });
        },
        orderChange(item){
          this.mySaleId = item.saleId;
          this.mySale = item;
          this.$emit("orderChange",item);
        },
        //计算应付款
        countShouldPay(){
          this.shouldPay = this.mySale.total_amt;
          //可增加优惠计算，提交到后台，由后台处理逻辑然后返回
        },
        //提交支付金额
        doPay(){
          this.$axios.post('/api/sale/paySale',{
            saleId: this.mySaleId,
            realPay : this.realPay
          }).then(res=>{
            console.log(res);
            if(res.data === ""){
              this.$alert("金额不足，无法结账！");
              this.realPay  = 0;
            }
            this.returnMoney = res.data.pay_change;
          })
        },
        exitOrder(){
          this.getUncompletedSale();
          this.$emit("showLinkSale", this.mySale);
          this.closeOrderPay();
        }
      }
    }
</script>

<style scoped lang="scss">
  .order-pay{
    width: 70%;
    height: 100%;
    display: inline-block;
    background-color: #f0f0f0;
    nav {
      border-bottom: 1px solid #dfdfdf;
      padding: 10px 20px;
      height: 5%;
      background-color: #fff;
      label {
        margin-right: 20px;
      }
      .el-dropdown {
        border: 1px solid #e2e2e2;
        padding: 5px;
        border-radius: 5px;
      }
      .close{
        float: right;
        display: inline-block;
        font-size: 23px;
        cursor: pointer;
        i {
          color: #4e555b;
        }
      }
    }
    .content{
      display: flex;
      height: calc(95% - 20px);
      align-items: stretch;
      width: 100%;
      .oth{
        order: 1;
        display: flex;
        flex-direction: column;
        width: 48%;
        border-right: 1px solid #e0e0e0;
        div:first-child{
          order:1;
          height: 90%;
          padding: 20px;
          background: #fff;
        }
        div:last-child{
          order: 2;
          background-color: #fdfdfd;
          height: 10%;
          border-top: 1px solid #e0e0e0;
        }
      }
      .calc{
        background-color: #fdfdfd;
        order:2;
        height: calc(100% - 40px);
        width: 52%;
        padding: 20px;
        .main{
          padding: 15px;
          border-radius: 5px;
          background: linear-gradient(#3a4a5a,#77675a,#30697c);
          ul{
            background-color: #fff;
            border-radius: 5px;
            padding: 10px 0;
            border: 1px solid #e0e0e0;
            li{
              width: calc((100% - 3px) / 3 );
              display: inline-block;
              text-align: center;
              border-right: 1px solid #d8d8d8;
              padding: 10px 0;
              &:last-child{
                border: 0;
                div{
                  color: #fc502c;
                }
              }
              span{
                display: inline-block;
              }
              div{
                font-size: 20px;
              }
            }
          }
        }
        .el-button{
          width: 100%;
          background-color: #af0d10;
          color: white;
          margin-top: 40px;
          height: 50px;
        }
      }
    }
  }
  table{
    margin-top: 15px;
    width: 100%;
    tr{

      td{
        padding: 5px;
        width: 15%;
        button{
          height: 60px;
          width: 100%;
          font-size: 20px;
          border: 0;
          border-radius: 5px;
          background-color: #fff;
          cursor: pointer;
        }
        &:first-child{
          width: 30%;
          button{
            font-size: 16px;
            background-color: #50c1e9;
            color: white;
          }

        }
        &:last-child{
          width: 25%;
          button{
            background-color: #fb6e52;
            color: white;
          }
          .sub{
            background-color: #a1d46f;
          }


        }

      }
    }
  }
  .orderCount{
    label{
      font-size: 20px;
    }
    span{
      margin-left: 100px;
      font-size: 20px;
      color: #af0d10;
    }
    #cheap{
      margin-top: 20px;
      //border: 1px dashed #e0e0e0;
      background: #fff;
      height: 100%;
    }
  }
</style>
