<template>
  <div class="tablePicker">
    <nav>
      <label>订单</label>
      <el-dropdown trigger="click" @command="orderChange">
      <span class="el-dropdown-link">
        {{this.mySaleId}}<i class="el-icon-arrow-down el-icon--right"></i>
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
            @click="closeTablePicker">
        <i class="fa fa-close"></i></span>
    </nav>

    <div class="content">
      <table>
        <tr>
          <td>2<br>人<br>桌</td>
          <td v-for="item in tableList.two"
              :class="item.is_people?'tdBkgIsUsing':'tdBkgEmpty'"
              @click="selectTable(item)">
            {{item.dt_Id}}号
          </td>
        </tr>
        <tr>
          <td>4<br>人<br>桌</td>
          <td v-for="item in tableList.four"
              :class="item.is_people?'tdBkgIsUsing':'tdBkgEmpty'"
              @click="selectTable(item)">
            {{item.dt_Id}}号<br>
            <span></span>
          </td>
        </tr>
        <tr>
          <td>6<br>人<br>桌</td>
          <td v-for="item in tableList.six"
              :class="item.is_people?'tdBkgIsUsing':'tdBkgEmpty'"
              @click="selectTable(item)">
            {{item.dt_Id}}号
          </td>
        </tr>
      </table>

    </div>
    <div class="tableFooter">
      <div class="dinnerNum">
        <label>就餐人数</label>
        <el-input-number size="small"
                         v-model="dinner_num"
                         :min="1"
                         :max="6"></el-input-number>
      </div>
      <div class="pickNum">
        <label>已选：<span>{{pickTable}}</span></label>
      </div>
      <el-button
        type="primary"
        size="medium"
        @click="subSelectTable">
        确定</el-button>

    </div>

  </div>
</template>

<script>
    export default {
      name: "TablePicker",
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
          mySaleId:"",
          mySale:{},
          saleList:[],
          tableList:{
            two:[],
            four:[],
            six:[]
          },
          dinner_num:1,
          pickTable:"",


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
        this.getTableList();
        this.getUncompletedSale();
        this.mySaleId = this.currentSaleId;
        this.mySale = this.currentSale;
        console.log(this.mySaleId);
        if(this.mySaleId === ''){
          this.mySale = this.saleList[0];
          this.$emit("showLinkSale",this.mySale);
        }

      },
      methods:{
        closeTablePicker(){
          this.$router.push("/home/order/orderPicker");
        },
        getUncompletedSale(){
          this.$axios.get("/api/sale/getUncompletedSale")
            .then(res=>{
              this.saleList = res.data;
              if(this.mySaleId === ""){
                this.mySaleId = this.saleList[0].saleId;
              }
            });
        },
        getTableList(){
          this.tableList.two = [];
          this.tableList.four = [];
          this.tableList.six = [];
          this.$axios.get("/api/diningTable/getAllDiningTable")
            .then(res=>{
              res.data.dataList.forEach(table=>{
                let tableNum = table.diningTableType.dtt_num;
                if(tableNum === 2){
                  this.tableList.two.push(table);
                }else if(tableNum === 4){
                  this.tableList.four.push(table);
                }else {
                  this.tableList.six.push(table);
                }
              });
            });
        },
        showLinkSale(sale){
          this.$emit("showLinkSale", sale);
        },
        orderChange(item){
          this.mySaleId = item.saleId;
          this.mySale = item;
          this.$emit("orderChange",item);
        },
        selectTable(table){//前端显示
          this.pickTable = table.dt_Id + "号桌";
          if(table.is_people){
            console.log(this.pickTable);
            this.$confirm(table.dt_Id + "号桌已被占用，是否空出？", '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.emptyTable();
            }).catch(() => {
              this.$message("已取消");
            });
          }
        },
        subSelectTable(){//选择了桌子，点击了提交按钮
          if(this.mySale.diningTableList.length > 0){
            this.$confirm("当前订单已分配餐桌,请选择换桌或增加餐桌", '提示', {
              confirmButtonText: '换桌',
              cancelButtonText: '增桌',
              type: 'warning'
            }).then(() => {
              this.apiDistributeTable(false);
            }).catch(() => {
              this.apiDistributeTable(true);
            });

          }
          else {
            this.apiDistributeTable(true);
          }
        },
        //处理餐桌分配
        apiDistributeTable(add){
          this.$axios.post("/api/sale/updateSaleTable",{
            saleId : this.mySale.saleId,
            tableId : this.pickTable.charAt(0),
            dinner_num : this.dinner_num,
            isAdd : add
          }).then(res => {
            this.getTableList();
          })

        },
        emptyTable(){
          this.$axios.post("/api/sale/emptyDiningTable",{
            tableId : this.pickTable.charAt(0),
          }).then(() => {
            this.getTableList();
          })
        }
      }
    }
</script>

<style scoped lang="scss">

  .tablePicker {
    width: 70%;
    height: 100%;
    display: inline-block;
    position: relative;
    nav {
      border-bottom: 1px solid #dfdfdf;
      padding: 10px 20px;
      height: 35px;
      label {
        margin-right: 20px;
      }

      .el-dropdown {
        border: 1px solid #e2e2e2;
        padding: 5px;
        border-radius: 5px;
      }
      .close {
        float: right;
        display: inline-block;
        font-size: 23px;
        cursor: pointer;

        i {
          color: #4e555b;
        }

        &:hover {

        }
      }
    }
    .content{
      width: calc(100% - 40px);
      height: calc(90% - 96px);
      padding: 20px;
      background-color: #fbfbfb;
     table{
       width: 100%;
       background-color: #fff;
       height: 100%;
       tr{
         border: {
           top: 2px solid #e0e0e0;
           right: 2px solid #e0e0e0;
         };
         &:last-child{
           border-bottom: 2px solid #e0e0e0;
         }
         td{
           border: 1px dashed #dfdfdf;
           text-align: center;
           width: 23%;
           cursor: pointer;
           font-size: 20px;

         }
         td:first-child{
           width: 40px;
           border: {
             left: 2px solid #e0e0e0;
             right: 2px solid #e0e0e0;
             top: 2px solid #af0d10;
             bottom: 2px solid #af0d10;
           }
         }

       }
       tr:nth-child(1),tr:nth-child(3){
         background-color: #e6ffd7;
       }
       tr:nth-child(2){
         background-color: #bcffa6;
       }
      }
    }
    .tableFooter{
      height: 10%;
      width: 100%;
      background-color: #fff;
      border-top: 1px solid #e0e0e0;
      .dinnerNum{
        padding: 15px 24px;
        display: inline-block;
        border-right: 1px solid #e0e0e0;
        .el-input-number{
          margin-left: 10px;
        }
      }
      .pickNum{
        display: inline-block;
        padding: 15px 24px;
        span{
          font-weight: bold;
          color: #af0d10;
        }
      }
      .el-button{
        float: right;
        margin: 15px 20px;
      }
    }
  }
  .tdBkgIsUsing{
    background: radial-gradient(circle, #e92a0f, #fff, #fff);

  }
  .tdBkgEmpty{
    background: radial-gradient(circle, #57d412, #fff, #fff);


  }

</style>
