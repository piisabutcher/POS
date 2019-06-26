<template>
  <div id="admin">
    <div id="left">
      <div class="logo">
        <div>
          <img src="../../static/img/micky.png"
               alt="logo"/>
        </div>
      </div>
      <ul>
        <li v-for="(item,index) in li"
            :class="{'click':activeLi === index}"
            @click="changeView(index)">
          <div>
            <i :class="[{'click':activeLi === index},item.class]"></i>
          </div>
          <span>{{item.label}}</span>
        </li>
      </ul>
    </div>
    <div id="right">
      <div id="head">
        <span @click="myQuit"><i class="fa fa-power-off"></i>退出</span>
        <span @click="myBack"><i class="fa fa-mail-reply"></i>返回</span>
      </div>
      <router-view class="router-view"></router-view>
    </div>
  </div>

</template>

<script>
    export default {
      name: "Admin",
      data(){
        return{
          li:[
            {
              label:'用户',
              class:'fa fa-user'
            },{
              label:'餐饮',
              class:'fa fa-glass'
            }
          ],
          activeLi:1,


        }
      },
      created() {

      },
      methods:{
        changeView(index){
          if(index === this.activeLi){
            return;
          }
          this.activeLi = index === 0 ? 0 : 1;
          if(index === 0)
            this.$router.push('/admin/user');
          else if(index === 1){
            this.$router.push("/admin/catalog");
          }

        },
        myBack(){
          this.$router.back();
        },
        myQuit(){
          this.$router.push("/login");
        }

      }
    }
</script>

<style scoped lang="scss">
  #admin {
    height: 100%;
    width: 100%;
    display: flex;
  }
  #left {
    width: 10%;
    height: 100%;
    float: left;
    background-color: #3a4a5a;
    .logo{
      background-color: #313236;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 40px;
      color: white;
      div{
        height: 60px;
        width: 100px;
        position: absolute;
        left: 5px;
        top: 5px;
        img{
          height: 100%;
          object-fit: cover;
          display: block;
        }
      }
    }
    div{
      height: 70px;
    }
    ul{
      width: 100%;
      li{
        display: flex;
        flex-direction: column;
        margin-bottom: 10px;
        align-items: center;
        padding: 12px;
        -webkit-transition: background-color 200ms linear;
        -moz-transition: background-color 200ms linear;
        -o-transition: background-color 200ms linear;
        div{
          height: 90px;
          width: 90px;
          line-height: 90px;
          border-radius: 50%;
          background: white;
          text-align: center;
          font-size: 60px;
        }
        span{
          font-size: 25px;
          color: white;
        }
        &.click{
          background-color: #af0d10;
          box-shadow: 2px 0 5px black;
        }
      }
      i{
        color: #3a4a5a;
        &.click{
          color: #af0d10;
        }
      }
    }
  }
  #head{
    height: 69px;
    border-bottom: 1px solid #e0e0e0;
    background-color: #313236;
    span{
      cursor: pointer;
      display: inline-block;
      background-color: #5F7888;
      color: white;
      width: 100px;
      height: 50px;
      border-radius: 5%;
      line-height: 50px;
      text-align: center;
      margin: 10px;
      float: right;
      i{
        margin-right: 10px;
      }
    }
  }
  #right{
    width: 90%;
  }

</style>
