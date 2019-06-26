<template>
  <div id="orderPicker">
    <div class="nav">
      <div id="or">
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
      </div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
          @click.native="changeCatalog(0)">
          所有
        </el-breadcrumb-item>
        <el-breadcrumb-item
          v-for="item in catalogList"
          :key="item.catalogId"
          @click.native="changeCatalog(item.catalogId)"
        >{{item.catalogName}}</el-breadcrumb-item>
      </el-breadcrumb>



    </div>
    <div class="content">
      <ul>
        <li v-for="(item,index) in foodList"
            :key="index">
          <span>{{item.foodId}}</span>
          <div class="image">
            <img :src="item.image"
                 :alt="item.foodName">
          </div>
          <label>{{item.foodName}}</label>
          <span class="salePrice">售价：￥{{item.price}}</span>
          <div class="taste">
            <el-tag size="medium"
                    v-for="tas in item.taste"
                    :key="tas.tasteId"
                    :type="tas.pick?'danger':'info'"
                    @click="addTag(index, tas)"
            >{{tas.tasteName}}</el-tag>
          </div>
          <span class="num">
            <el-input-number v-model="item.num"
                             size="mini"
                             controls-position="right"
                             @change=""
                             :min="1"
                             :max="10"
            ></el-input-number>
          </span>
          <span><i class="fa fa-plus" @click="addSaleLineItem(item)"></i></span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
    export default {
      name: "OrderPicker",
      props:{
        currentSaleId:{
          type: String,
        },
        currentSale:{
          type: Object
        },

      },
      created(){
        this.mySaleId = this.currentSaleId;
        this.mySale = this.currentSale;
        this.getCatalogList();
        this.getUncompletedSale();
      },
      data(){
        return{
          catalogList:[],
          foodList:[],
          saleList:[],
          mySaleId:"",
          mySale:{}
        }
      },
      mounted() {
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
      methods: {
        getCatalogList() {
          this.$axios.get('/api/catalog/getAllCatalog').then(res => {
            this.catalogList = res.data.dataList;
            let foodSet = [];
            this.catalogList.forEach(catalog => {
              catalog.foodList.forEach(food => {
                foodSet.push(food);
              });
            });
            this.makeFoodList(foodSet);
          });
        },
        getUncompletedSale(){
          this.$axios.get("/api/sale/getUncompletedSale")
            .then(res=>{
              this.saleList = res.data;
              if(this.mySaleId === ""){
                this.mySale = this.saleList[0];
                this.mySaleId = this.mySale.saleId;
                this.$emit('showLinkSale',this.mySale);
              }
            });
        },
        orderChange(item){
          this.mySaleId = item.saleId;
          this.$emit("orderChange",item);
        },
        makeFoodList(foodSet) {
          foodSet.forEach(food => {
            this.$set(food, 'tags', []);
            this.$set(food, 'num', 1);
            this.$set(food, 'pick', 1);
            food.taste.forEach(taste => {
              this.$set(taste, 'pick', false);
            });
          });
          this.foodList = foodSet;
        },
        checkSale() {
          if (this.mySaleId === "") {
            this.$alert("当前没有操作订单哦","提示",{
              type:"warning"
            });
            return false;
          } else
            return true;
        },
        addTag(index, taste) {
          if (this.checkSale())
            if (taste.pick) {
              taste.pick = false;
              this.foodList[index].tags.splice(
                this.foodList[index].tags.indexOf(taste), 1);
            } else {
              taste.pick = true;
              this.foodList[index].tags.push(taste);
            }
        },
        addSaleLineItem(food) {
          if (this.checkSale()) {
            let tasteList = [];
            food.tags.forEach(t => {
              delete t.pick;
              tasteList.push(t);
            });
            this.$axios({
              method: "post",
              url: '/api/sale/addNewSaleLineItem',
              data: {
                sale: {
                  saleId: this.mySaleId,
                },
                food: {
                  foodId: food.foodId
                },
                sli_num: food.num,
                pickTaste: tasteList
              }
            }).then(res => {
              console.log(res);
              this.$emit("addSlt", res.data);
            });
          }
        },
        changeCatalog(catalogId) {
          if (catalogId === 0) {
            this.getCatalogList();
          } else {
            this.$axios.get('/api/catalog/getCatalogById?cid=' + catalogId)
              .then(res => {
                this.makeFoodList(res.data.foodList);
              });
          }
        },
      }
    }

</script>

<style scoped lang="scss">
  #orderPicker{
    width: 70%;
    height: calc(100% - 4px);
    display: inline-block;
    position: relative;
    background-color: #fff;
    text-align: center;
    .nav{
      padding: 12px;
      background-color: #fbfbfb;
      border-bottom: 1px solid #eee;
      .el-breadcrumb{
        padding-top: 10px;
      }
      .el-breadcrumb__item{
        cursor: pointer;
        &:hover{color: #af0d10;}
      }
      #or{
        text-align: left;
        padding-bottom: 10px;
        border-bottom: 1px solid #e0e0e0;
        .el-dropdown{
          border: 1px solid #e0e0e0;
          margin-left: 10px;
          padding: 5px;
          border-radius: 4px;
        }
      }
    }
    .content{
      background-color: #f0f0f0;
      height: calc(100% - 107px);
      padding: 10px 0;
      ul{
        background-color: #f0f0f0;
        width: calc(100% - 20px);
        height: 100%;
        padding: 0 10px;
        overflow: auto;
        li{
          background-color: #fdfdfd;
          width: 100%;
          border: 1px solid #e5e5e5;
          margin-bottom: 4px;
          border-top-right-radius: 4px;
          border-bottom-right-radius: 4px;
          text-align: left;
          height: 80px;
          line-height: 80px;
          &>span:first-child{
            display: inline-block;
            height: 80px;
            padding: 0 20px;
            border-right: 1px solid #b2b2b2;
          }
          &>label{
            display: inline-block;
            width: 15%;
            text-align: center;
          }
          .salePrice{
            padding: 0 15px;
            color: #af0d10;
            border-right: 1px solid #b2b2b2;
            border-left: 1px solid #b2b2b2;
          }
          .taste{
            display: inline-block;
            border-right: 1px solid #b2b2b2;
            width: 38%;
            height: 100%;
            vertical-align: top;
            padding: 0 10px;
            .el-tag{
              margin-right: 10px;
              cursor: pointer;
            }
          }
          .num{
            padding: 0 10px;
            text-align: center;
            input{
              width: 50px;
              vertical-align: middle;
            }
            .el-input-number{
              width: 100px;
            }

          }
          .image{
            width: 60px;
            height: 70px;
            padding: 5px;

            display: inline-block;
            vertical-align: top;
            img{
              width: 100%;
              height: 100%;
              display: block;
              object-fit: cover;
            }
          }
          &>span:last-child{
            display: inline-block;
            box-shadow: 0 0 10px #98d261;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            line-height: 40px;
            margin-top: 20px;
            margin-right: 18px;
            text-align: center;
            font-size: 16px;
            background-color: #98d261;
            float: right;
            i{
              cursor: pointer;
              color: white;
              font-weight: bold;
              &:hover{
                font-weight: bold;
                transform: scale(1.1);
              }
            }
          }
        }
      }
    }
  }

</style>
