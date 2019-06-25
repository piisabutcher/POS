<template>
    <div id="user">
      <div class="left">
        <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            prop="userId"
            label="工号">
          </el-table-column>
          <el-table-column
            prop="password"
            label="密码">
          </el-table-column>
          <el-table-column
            prop="userName"
            label="姓名">
          </el-table-column>
          <el-table-column
            prop="gender"
            label="性别">
          </el-table-column>
          <el-table-column
            prop="tel"
            label="联系电话">
          </el-table-column>
          <el-table-column
            prop="power"
            label="权限">
          </el-table-column>
          <el-table-column
            prop="operate"
            label="操作">
          </el-table-column>
        </el-table>
        <div class="page">
          <el-pagination
            layout="prev, pager, next"
            :total="page.total"
            :current-page="page.currentPage"
            :page-size="page.pageSize"
            @current-change="handleCurrentChange">
          </el-pagination>
        </div>

      </div>
      <div class="right"></div>

    </div>
</template>

<script>
    export default {
      name: "User",
      data(){
        return {
          tableData: [],
          page:{
            total: 0,
            currentPage:1,
            pageSize: 10
          }
        }
      },
      created() {
        this.refreshTable();

      },
      methods:{
        refreshTable(){
          this.$axios.post("/api/user/getUserList",{
            page: this.page.currentPage,
            size: this.page.pageSize
          }).then(res=>{
              this.tableData = res.data.content;
              this.page.total = res.data.totalElements;
          });
        },
        handleCurrentChange(currentPage){
          this.page.currentPage = currentPage;
          this.refreshTable();
        },
      }

    }
</script>

<style scoped lang="scss">
  #user{
    width: 100%;
    height: calc(100% - 70px);
    display: flex;
  }
  .left{
    width: 68%;
    background-color: #fbfbfb;
    padding: 15px;
    position: relative;
  }
  .page{
    position: absolute;
    width: calc(100% - 30px);
    text-align: center;
    padding: 30px 0;
    bottom: 0;
    .el-pagination{
      background-color: #fbfbfb;
    }
  }
  .right{
    width: 30%;
  }

</style>
