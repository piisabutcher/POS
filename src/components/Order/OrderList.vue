<template>
  <div class="orderList">
    <div class="nav">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
        @click.native="getAllSale">
          所有</el-breadcrumb-item>
        <el-breadcrumb-item
        @click.native="getUncompletedSale">
          未完成</el-breadcrumb-item>
        <el-breadcrumb-item
        @click.native="getCompletedSale">
          已完成</el-breadcrumb-item>
      </el-breadcrumb>
      <span class="close"
        @click="closeOrderList"
      ><i class="fa fa-close"></i></span>
    </div>
    <div class="table-container">
      <el-table
        :data="orderList"
        height="100%"
        border
        stripe
        :cell-style="myTable"
        :header-cell-style="myTable"
        style="width: 100%"
        @row-click="handleRowClick">
        <el-table-column
          prop="saleId"
          label="订单号">
        </el-table-column>
        <el-table-column
          prop="dinners_num"
          label="就餐人数"
          width="80">
        </el-table-column>
        <el-table-column
          prop="sale_time"
          label="下单时间"
          :formatter="formatDate">
        </el-table-column>
        <el-table-column
          prop="total_amt"
          label="订单金额"
          width="100">
        </el-table-column>
        <el-table-column
          prop="diningTableList"
          label="桌号"
          :formatter="formatTable">
        </el-table-column>
        <el-table-column
          prop="is_complete"
          label="状态"
          width="120"
          :formatter="formatIs_Complete">
        </el-table-column>
        <el-table-column label="操作"
          :formatter="formatOperate">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              v-show="scope.row.is_complete === 0"
              @click="handlePickTable(scope.$index, scope.row)">选桌</el-button>
            <el-button
              size="mini"
              type="danger"
              v-show="scope.row.is_complete === 0"
              @click="handlePayOrder(scope.$index, scope.row)">结账</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
  </div>

</template>

<script>
    export default {
      name: "OrderList",
      data(){
        return{
          orderList:[],
          mySaleId:"",
          mySale:{}
        }
      },
      created(){
        this.getUncompletedSale();
      },
      methods:{
        closeOrderList(){
          this.$router.back();
        },
        getAllSale(){
          this.$axios.get("/api/sale/getAllSale")
            .then(res=> {
              this.orderList = res.data;
            })
        },
        //未完成
        getUncompletedSale(){
          this.$axios.get("/api/sale/getUncompletedSale")
            .then( res=> {
              this.orderList = res.data;
            })
        },
        //已完成
        getCompletedSale(){
          this.$axios.get("/api/sale/getCompletedSale")
            .then( res=> {
              this.orderList = res.data;
            })
        },
        handlePayOrder(index, row){
          this.currentSale = row;
          this.$emit("listToPay", this.currentSale);
        },
        handlePickTable(index, row){
          this.currentSale = row;
          this.$emit("listToTable", this.currentSale);
        },
        handleRowClick(row){
          this.currentSale = row;
          this.$emit("showLinkSale",this.currentSale);
        },
        formatDate:function (row) {
          return this.getYYYYMMDD(row.sale_time);
        },
        formatIs_Complete:function(row, column){
          return row.is_complete === 0 ? "未完成":"已完成";
        },
        formatTable:function(row){
          let str = "";
          row.diningTableList.forEach(table => {
            str += table.dt_Id + " ";
          });
          return str;
        },
        formatOperate:function(row){

        },
        getYYYYMMDD (str) {
          let nDate = new Date(str);
          let nYear = nDate.getFullYear();
          let nMonth = nDate.getMonth() + 1;
          let nDay = nDate.getDate();
          let nHours = nDate.getHours();
          let nMinutes = nDate.getMinutes();
          let nSeconds = nDate.getSeconds();
          let nTime = nYear + '-' + this.addZero(nMonth) + '-' + this.addZero(nDay); // YYYY-MM-DD
          let nDateTime = nTime + ' ' + this.addZero(nHours) + ':' + this.addZero(nMinutes) + ':' + this.addZero(nSeconds);// YYYY-MM-DD-MM-SS
          return nDateTime // 格式为 YYYY-MM-DD-HH-MM-SS
        },
        addZero (num) {
          if (parseInt(num) < 10) {
            num = '0' + num
          }
          return num
        },
        myTable(){
          return "text-align:center";

        }
      }
    }
</script>

<style scoped lang="scss">
  .orderList{
    width: 70%;
    height: 100%;
    display: inline-block;
    position: relative;
    background-color: #f0f0f0;
    .nav{
      background-color: #fff;
      padding: 15px;
      .el-breadcrumb{
        display: inline-block;
      }
      .el-breadcrumb__item{
        cursor: pointer;
      }
      .close{
        float: right;
        i{
          font-size: 22px;
        }
        cursor: pointer;
      }
    }
    .table-container{
      width: calc(100% - 20px);
      padding: 10px;
      height: calc(100% - 71px);
      background-color: #fbfbfb;
    }

  }

</style>
