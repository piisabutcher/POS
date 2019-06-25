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
        style="width: 100%">
        <el-table-column
          prop="saleId"
          label="订单号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="dinners_num"
          label="就餐人数"
          width="180">
        </el-table-column>
        <el-table-column
          prop="date"
          label="下单时间">
        </el-table-column>
        <el-table-column
          prop="total_amt"
          label="订单金额">
        </el-table-column>
        <el-table-column
          prop="dt_id"
          label="桌号">
        </el-table-column>
        <el-table-column
          prop="is_complete"
          label="状态">
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
        }
      },
      created(){
        this.getAllSale();
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
