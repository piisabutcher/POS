<template>
  <div id="content">
    <div id="order">
      <div id="container">
        <!--订单项头部-->
        <div class="head">
          <div>
            <span>NO.<label>
              {{this.currentSaleId}}</label></span>
            <span>{{this.currentTime}}</span>
          </div>
          <div>
            <label>客单</label>
            <span>
              <i class="fa fa-trash"
                 @click="deleteSale"></i>
            </span>
          </div>

        </div>
        <!--订单项-->
        <div class="main">
          <table>
            <tr>
              <th>项</th><th>品名</th><th>单价</th><th>数量</th><th>小计</th>
            </tr>
            <tr v-for="item in currentSale.saleLineItemList">
              <td><i class="fa fa-minus-circle"
                     @click="removeSaleLineItem(item)"></i></td>
              <td>
                {{item.food.foodName}}
                <div>{{item.pickTaste | tagsToFormat}}</div>
              </td>
              <td>{{item.food.price}}</td>
              <td>{{item.sli_num}}</td>
              <td>{{item.sub_total}}</td>
            </tr>
            <!--删-->
          </table>
        </div>
        <!--订单项尾部-->
        <div class="footer">
          <span>
            <label>合计</label><label class="light">
            {{this.currentSale.total_num?this.currentSale.total_num:0}}
          </label><label>项</label>
          </span>
          <span>
            <label>￥</label><label class="light">
            {{this.currentSale.total_amt?this.currentSale.total_amt:0}}
          </label>
          </span>
        </div>

      </div>
      <div class="sub">
        <button @click="addNewSale"><i class="fa fa-upload"></i>新单</button>
        <button @click="downOrder"><i class="fa fa-download"></i>挂单</button>
        <button ><i class="fa fa-close"></i>清空</button>
        <button @click="pickTable">
          <i class="fa fa-hand-pointer-o"></i>
          选桌</button>
        <button @click="payOrder"
        ><i class="fa fa-money"></i>结账</button>
      </div>
    </div>
    <transition name="slide-fade">
      <router-view id="orderPicker"
                   :currentSaleId="currentSaleId"
                   :currentSale="currentSale"
                   @addSlt="addSaleLineItem"
                   @showLinkSale="showLinkSale"
                   @orderChange="showLinkSale"
                    @listToTable="listToTable"
                  @listToPay="listToPay"></router-view>
    </transition>

  </div>

</template>

<script>
    export default {
      name: "Content",
      data(){
        return{
          currentTime: "",//实时显示当前时间
          currentSaleId:"",
          currentSale:{},

        }
      },
      created() {
        this.showTime();
      },
      methods:{
        addNewSale(){
          this.$axios.get("/api/sale/createNewSale")
            .then(res=>{
              this.currentSale = res.data.data;
              this.currentSaleId = this.currentSale.saleId;
            })
        },
        addSaleLineItem(sale){
          this.currentSale = sale;
        },
        removeSaleLineItem(item){
          this.$axios.post('/api/sale/deleteSaleLineItem',{
            saleLineItemId:item.sli_id
          }).then(res=>{
            this.currentSale = res.data;
          });
        },
        downOrder(){//下单
          if(this.currentSaleId === ""){
            this.$alert('当前订单为空，无法挂单！');
          }else {
            this.currentSaleId = "";
            this.currentSale = {};
            this.$alert('已挂单，可到订单列表中查看！', '提示', {
              confirmButtonText: '确定',
              callback:action =>  {
                this.$router.replace('/empty');
              }
            });
          }
        },
        deleteSale(){//删除整个订单
          this.$confirm('此操作将永久删除当前订单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$axios.post('/api/sale/deleteSale',{
              saleId : this.currentSaleId
            }).then(res=>{
              console.log(res);
              this.$router.replace("/empty");
            });
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });

        },
        showLinkSale(pickSale){
          this.currentSale = pickSale;
          this.currentSaleId = this.currentSale.saleId;
        },
        pickTable(){
          this.$router.push("/home/order/tablePicker");
        },
        payOrder(){
          this.$router.push("/home/order/orderPay");
        },
        listToTable(sale){
          this.currentSale = sale;
          this.currentSaleId = this.currentSale.saleId;
          this.pickTable();
        },
        listToPay(sale){
          this.currentSale = sale;
          this.currentSaleId = this.currentSale.saleId;
          this.payOrder();
        },
        showTime(){//实时显示当前时间
          let today = new Date();//定义日期对象
          let yyyy = today.getFullYear();
          let MM = today.getMonth() + 1;
          let dd = today.getDate();
          let hh = today.getHours();
          let mm = today.getMinutes();
          let ss = today.getSeconds();
          let checkTime = function(t){
            t = t < 10? "0"+ t : t;
            return t;
          };
          MM = checkTime(MM);
          dd = checkTime(dd);
          mm = checkTime(mm);
          ss = checkTime(ss);
          this.currentTime = yyyy + "/" + MM + "/" + dd + " " + hh + ":" + mm + ":" + ss;
          setTimeout(this.showTime, 1000);
        }
      },
      filters:{
        tagsToFormat: function(tags){
          let tagsDescription = "";
          if(!tags){
            return;
          }
          tags.forEach(tag => {
            tagsDescription += tag.tasteName + " ";
          });
          return tagsDescription;
        }
      }
    }
</script>

<style scoped lang="scss">
  #content {
    height: calc(100% - 70px);
    width: 100%;
    #order {
      background-color: #f5f5f5;
      border-right: 1px solid #dfdfdf;
      display: inline-block;
      width: calc(30% - 21px);
      padding: 10px;
      float: left;
      height: calc(100% - 20px);
      position: relative;
      .sub{
        width: 100%;
        padding: 10px 10px 5px 0;
        text-align: right;
        button{
          background-color: #af0d10;
          color: white;
          border: 0;
          padding: 5px 10px;
          cursor: pointer;
          border-radius: 3px;
          margin-right: 10px;
          &:last-child{
            margin-right: 0;
          }
          i{
            margin-right: 5px;
          }
        }
        button:first-child{
          background-color: white;
          border: 1px solid #eee;
          color: black;
          float: left;
        }
      }
    }
  }
  #container{
    width: 100%;
    height: 93%;
    border: 1px solid #e5e5e5;
    background-color: #fdfdfd;
    position: relative;
    .head{
      padding: 15px;
      border-bottom: 1px dashed #dfdfdf;
      &>div{
        &:first-child{
          &>span{
            &:first-child{
              color: #af0d10;
            }
            &:last-child{
              float: right;
              color: #b2b2b2;
            }
          }
        }
        &:last-child{
          margin-top: 10px;
          label{
            font-size: 22px;
            font-weight: bold;
            letter-spacing: 10px;
            color: #313236;
          }
          span{
            float: right;
            display: inline-block;
            padding: 0 5px;
            border-radius: 5px;
            font-size: 22px;
            background-color: #f5f5f5;
            cursor: pointer;
          }
        }
      }
    }
    .main{
      padding: 4px;
      table{
        width: 100%;
        padding: 10px;
        text-align: center;
        overflow: auto;
        border: 1px solid #eeeeee;
        th{
          text-align: center;
          border-bottom: 2px solid #e5e5e5;
          padding: 10px;
          color: #7D0C0F;
          font-size: 17px;
        }
        td{
          border-bottom: 1px dashed #e2e2e2;
          padding: 10px 0;
          i:hover{
            color: #7D0C0F;
            cursor: pointer;
            transform: scale(1.1);

          }
          div{
            font-size: 12px;
            color: #af0d10;
          }
        }
      }


    }
    .footer{
      position: absolute;
      bottom: 0;
      width: calc(100% - 20px);
      background-color: #fff;
      border-top: 1px solid #e0e0e0;
      right: 0;
      text-align: right;
      padding: 10px;
      span:first-child{
        float: left;
      }
      .light{
        color: #af0d10;
        margin: 0 10px ;
        font-size: 18px;
      }
    }
  }
 .slide-fade-enter-active, .slide-fade-leave{
   transition: all .3s ease;
 }
  .slide-fade-leave-active{
    transition: all .3s cubic-bezier(1.0, 0.5, 0.5, 1.0);
  }
  .slide-fade-enter, .slide-fade-leave-active{
    transform:  translate(100%);
    opacity: 0;
  }


</style>
