<template>
  <div id="food">
    <div class="left">
      <div class="title">
        {{this.catalog.catalogName}}
      </div>
      <div id="content">
        <ul>
          <li v-for="food in foodList"
              class="u"
              @click="showFood(food)">
            <div class="img">
              <img :src="food.image"
                   :alt="food.foodName">
            </div>
            <div class="msg">
              <label>{{food.foodName}}</label><br>
              <div>
                口味：
                <el-tag v-for="tag in food.taste"
                        :key="tag.tasteId"
                >{{tag.tasteName}}</el-tag>
              </div>
              <span>售价：￥
                <label>{{food.price}}.00</label>
              </span><br/>
              <span>供应时段：下午2:00 ~ 下午12:00</span>
            </div>
          </li>

        </ul>
      </div>

    </div>
    <div class="right">
      <div class="title">
        <el-button type="success"
                   @click="addFood"
        ><i class="fa fa-plus"></i>添加</el-button>
        <el-button type="danger"
                   @click="deleteFood"
        ><i class="fa fa-trash"></i>删除</el-button>
      </div>
      <div class="con">
        <div class="con-1">
          <div id="image">
            <img :src="food.image"
                 :alt="food.foodName">
          </div>
          <!--<el-dialog :visible.sync="dialogVisible" size="tiny">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>-->
        </div>
        <div class="con-1">
          <label>菜类:</label>
          <el-dropdown trigger="click" @command="change">
            <el-button>
              {{this.selectCatalog.catalogName}}
              <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-for="item in catalogList"
                :key="item.catalogId"
                :command="item"
              >{{item.catalogName}}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <label id="foodN">菜名:</label>
          <el-input v-model="food.foodName"></el-input>
        </div>
        <div class="con-1">
          <label>口味:</label>
          <span>
            <el-tag v-for="tag in myTags.selected"
                    :key="tag.tasteId"
                    closable
                    @close="handleCloseTag(tag)">
              {{tag.tasteName}}
            </el-tag>
          </span>
          <div class="tags">
            <el-tag v-for="tag in myTags.noSelected"
                    :key="tag.tasteId"
                    type="success"
                    @click.native="addTag(tag)">
              {{tag.tasteName}}</el-tag>
          </div>
        </div>
        <div class="con-1">
          <label>售价：</label>
          <el-input v-model="food.price"></el-input>
          元
        </div>
        <el-button class="sub"
        @click="updateFood(isAdd)">提交</el-button>



      </div>

    </div>

  </div>

</template>

<script>
    export default {
      name: "Food",
      data(){
        return{
          catalogId:'',
          catalog:{},
          foodList:[],
          catalogList:[],
          selectCatalog:'',
          food:{},
          isAdd: false,
          myTags:{
            all:[
              {
                tasteId:'a_4',
                tasteName:'加沙拉酱'
              },{
                tasteId:'b_1',
                tasteName:'去冰'
              },{
                tasteId:'a_3',
                tasteName:'加番茄酱'
              },{
                tasteId:'a_2',
                tasteName:'微辣'
              },{
                tasteId:'b_4',
                tasteName:'去糖'
              },{
                tasteId:'b_5',
                tasteName:'少糖'
              },{
                tasteId:'b_6',
                tasteName:'多糖'
              },{
                tasteId:'a_5',
                tasteName:'加黑椒汁'
              },{
                tasteId:'a_1',
                tasteName:'加辣'
              },{
                tasteId:'c_1',
                tasteName:'小份'
              },{
                tasteId:'c_2',
                tasteName:'大份'
              },{
                tasteId:'b_2',
                tasteName:'多冰'
              },{
                tasteId:'b_3',
                tasteName:'少冰'
              },{
                tasteId:'a_6',
                tasteName:'微辣'
              },
            ],
            selected:[
            ],
            noSelected:[

            ]
          },
        }
      },
      created() {
        this.getCatalogById();
      },
      methods:{
        getCatalogById(){
          this.catalogId = this.$route.params.catalog;
          this.$axios.get('/api/catalog/getCatalogById?cid='+this.catalogId)
            .then(res=>{
              this.catalog = res.data;
              this.selectCatalog = JSON.parse(JSON.stringify(this.catalog));
              this.foodList = this.catalog.foodList;
              this.food = this.foodList[0];
              this.initFood();
            });
          this.getAllCatalog();
        },
        initFood(){
          this.myTags.selected.length = 0;
          this.myTags.noSelected.length = 0;
          this.myTags.all.forEach(tag=>{
            let have = false;
            for(let i=0; i<this.food.taste.length; i++){
              if(tag.tasteId === this.food.taste[i].tasteId){
                this.myTags.selected.push(tag);
                have = true;
                break;
              }
            }
            if(!have){
              this.myTags.noSelected.push(tag);
            }
          });
          this.linkCatalog();
        },
        linkCatalog(){
          let tempCatalog = JSON.parse(JSON.stringify(this.catalog));
          delete tempCatalog.foodList;
          this.$set(this.food,'catalog',tempCatalog);
        },
        getAllCatalog(){
          this.$axios.get("/api/catalog/getAllCatalog")
            .then(res=>{
              this.catalogList = res.data.dataList;
            });
        },
        change(catalog){
          this.selectCatalog = catalog;
        },
        showFood(food){
          this.food = food;
          this.isAdd = false;
          this.initFood();
        },
        handleCloseTag(tag){
          let index;
          let item;
          let o = this.myTags.selected;
          for(let i=0; i<o.length; i++){
            if(tag === o[i]){
              index = i;
              item = o[i];
              break;
            }
          }
          o.splice(index,1);
          this.myTags.noSelected.push(item);
        },
        addTag(tag){
          let noSelect = this.myTags.noSelected;
          for(let i=0; i<noSelect.length; i++){
            if(tag === noSelect[i]){
              this.myTags.selected.push(tag);
              console.log("++");
              noSelect.splice(i ,1);
            }
          }
        },
        updateFood(isAdd){
          if(isAdd){
            this.$axios.post("/api/food/saveFood",this.food)
              .then(res => {
                this.$alert('添加成功！', '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.getCatalogById();
                    this.showFood();
                  }
                });
              })

          }else{
            this.isAdd = false;
            let postFood = JSON.parse(JSON.stringify(this.food));
            postFood.taste = this.myTags.selected;
            this.$axios.post("/api/food/updateFood",postFood)
              .then(res => {
                this.$alert('修改成功！', '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.getCatalogById();
                  }
                });
              });
          }

        },
        addFood(){
          this.food = {};
          this.myTags.noSelected = JSON.parse(JSON.stringify(this.myTags.all));
          this.myTags.selected.length = 0;
          this.isAdd = true;
          this.food.taste = this.myTags.selected;
          this.food.supPeriod = 1;
          this.food.image = "../../../static/img/list/default.jpg";
          this.linkCatalog();
        },
        deleteFood(){
          if(!this.food.foodId){
            this.$alert('该菜品不存在！', '标题名称', {
              confirmButtonText: '确定',
            });
            return;
          }
          this.$confirm('此操作将永久删除此菜品, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$axios.post("/api/food/deleteByFoodId",{
              foodId:this.food.foodId
            }).then( res=>{
              this.getCatalogById();
              this.initFood();
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
        }
      }



    }
</script>

<style scoped lang="scss">
  #food{
    width: 100%;
    height: calc(100% - 70px);
    display: flex;
  }
  .left{
    width: 50%;
    height: 100%;
    background-color: #fbfbfb;
  }
  .right{
    width: 50%;
    height: 100%;
    background-color: #fbfbfb;
    .title{
      text-align: right;
      i{
        margin-right: 5px;
      }

    }
  }
  .title{
    height: 5%;
    padding: 20px;
    font-size: 24px;
    border-bottom: 1px solid #e0e0e0;
    background-color: #fff;
  }
  #content{
    width: 100%;
    height: 88.7%;
    overflow: auto;
  }
  li.u{
    background-color: white;
    box-shadow: 0 0 2px rgba(120,120,120,0.5);
    margin: 20px;
    padding: 10px;
    cursor: pointer;
    .img{
      width: 150px;
      height: 150px;
      display: inline-block;
      img{
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
    .msg{
      display: inline-block;
      height: 150px;
      padding: 0 20px;
      vertical-align: top;
      label{
        display: inline-block;
        font-size: 24px;
        margin-bottom: 10px;
      }
      div{
        .el-tag{
          margin-right: 10px;
          margin-bottom: 10px;
        }
      }
      span{
        label{
          font-size: 24px;
          color: #af0d10;
        }
      }
    }
  }
  .con{
    width: auto;
    height: auto;
    padding: 20px 20px 0 20px;
    .con-1{
      width: auto;
      margin-bottom: 5px;
      padding: 10px 20px;
      background-color: #fff;
      border: 1px solid #eee;
      #image{
        height: 150px;
        width: 150px;
        display: inline-block;
        img{
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
      .el-upload{
        display: inline-block;
        width: 100px;
      }
      #foodN{
        margin-left: 135px;
      }
      .el-input{
        display: inline-block;
        width: 250px;
      }
      label{
        display: inline-block;
        margin-right: 20px;
      }
      .el-tag{
        margin: 5px;
      }
      .tags{
        padding: 5px;
        flex-wrap: wrap;
        background-color: #f2fef5;
        margin-top: 10px;
        border-radius: 5px;
      }
    }
    .sub{
      color: white;
      background-color: #af0d10;
      margin-top: 15px;
      float: right;
    }

  }



</style>
