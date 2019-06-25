<template>
  <div id="log">
    <ul class="cb-slideshow">
      <li v-for="word in hoveWord">
        <span></span>
        <div><h3>{{word}}</h3></div>
      </li>
    </ul>
    <div id="content"
          :class="activeKey ? 'down':'up'">
      <ul>
        <li v-for="(item,index) in title"
            :class="index === activeKey ? 'on':'off'"
            @click="changeTab(index)"
        >{{item}}</li>
      </ul>
      <div id="login" v-if="activeKey === 0">
        <div autocomplete="off">
          <label for="userId"></label>
          <input type="text"
                 id="userId"
                 placeholder="用户名"
                 v-model="login.id">
          <label for="password"></label>
          <input type="password"
                 id="password"
                 placeholder="密码"
                 v-model="login.password">
          <button @click="postLogin()">马上登录</button>
        </div>
      </div>
      <div id="register" v-if="activeKey === 1">
        <form id="registerForm">
          <input placeholder="用户名"
                 type="text"
                 v-model="register.id.value"/>
          <div v-if="checkRegister(1)">{{register.id.tip}}</div>
          <input type="password"
                 placeholder="密码"
                 v-model="register.pass.value">
          <div v-if="checkRegister(2)">{{register.pass.tip}}</div>
          <input type="password"
                 placeholder="确认密码"
                 v-model="register.rePass.value">
          <div v-if="checkRegister(3)">{{register.rePass.tip}}</div>
          <input type="tel"
                 placeholder="联系电话"
                 v-model="register.tel.value">
          <div v-if="checkRegister(4)">{{register.tel.tip}}</div>
          <div id="rdo">
            <label class="sex">性别</label><!--
           -->
            <div>
              <input id="male"
                     type="radio"
                     name="sex"
                     v-model="register.sex.value1"
                     checked>
              <label for="male"></label>
              <span>男</span>
            </div><!--
           -->
            <div>
              <input id="female"
                     type="radio"
                     name="sex"
                     v-model="register.sex.value2">
              <label for="female"></label>
              <span>女</span>
            </div>
          </div>
          <button>确认注册</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "Login",
      data(){
        return {
          title:['登录','注册'],
          hoveWord:['p·ea·ce·ful','com·for·ta·ble',
          'bal·ance','e·qua·nim·i·ty','se·ren·i·ty'],
          activeKey: 0,//0登录，1注册
          login:{
            id:'',
            password:'',
          },
          register:{
            id:{
              value:'',
              tip:''
            },
            pass:{
              value:'',
              tip:''
            },
            rePass:{
              value:'',
              tip:''
            },
            tel:{
              value:'',
              tip:''
            },
            sex:{
              value1:'',
              value2:'',
              tip:''
            }
          }
        }
      },
      methods:{
        changeTab(index){
          if(index !== this.activeKey){
            this.activeKey = this.activeKey? 0:1;
          }
        },
        checkRegister(which){
          switch (which) {
            case 1:{
              let id = this.register.id.value;
              if(id.length < 2 || id.length > 16){
                if(id === ''){
                  return false;
                }
                this.register.id.tip = "WARN：请输入长度为2~16的用户名";
                return true;
              }else{
                return false;
              }
            }
            case 2:{
              let pass = this.register.pass.value;
              let pwdPattern = /^[0-9a-zA-Z]+$/;
              if(pass.length < 6 || pass.length > 16){
                if(pass === ''){
                  return false;
                }
                this.register.pass.tip = "WARN：密码长度要求为6~16";
                return true;
              }else if(!pwdPattern.test(pass)){
                this.register.pass.tip = "WARN：密码中含有非法字符";
                return true;
              }else {
                return false;
              }
            }
            case 3:{
              let pass = this.register.pass.value;
              let rePass = this.register.rePass.value;
              if(rePass !== pass){
                this.register.rePass.tip = "WARN：两次密码输入不一致";
                return true;
              }else {
                return false;
              }
            }
            case 4:{
              let tel = this.register.tel.value;
              let telPattern = /^[1][3,4,5,7,8][0-9]{9}$/;
              if(!telPattern.test(tel)){
                if(tel === ''){
                  return false;
                }
                this.register.tel.tip = 'WARN：请输入正确格式的手机号码';
                return true;
              }else {
                return false;
              }
            }
          }
        },
        postLogin(){
          this.$axios.post('/api/user/login',{
            userId: this.login.id,
            password: this.login.password
          }).then(res=>{
            console.log(res);
            let code = res.data.code;
            if(code === 200){
              if(res.data.msg === 'admin'){
                this.$router.push('/admin');
              }else{
                this.$router.push('/home');
              }
            }else if(code === 500){
              this.$alert("服务器发生未知错误");
            }else {
              this.$alert("用户名或密码错误！");
            }
          });
        }
      }
    }
</script>

<style scoped lang="scss">
  @import "../assets/css/login.css";
  input{
    width: calc(100% - 42px);
    height: 45px;
    margin: 10px 0 0 0;
    padding: 0 20px;
  }
  #log{
    width: 100%;
    height: 100%;
    text-align: center;
    position: relative;
    #content{
      position: fixed;
      width: 28%;
      left: 0;
      right: 0;
      margin: 0 auto;
      padding: 0;
      background-color: rgba(206, 212, 218, 0.3);
      &.up{
        top:30%;
        transition: top 0.2s;
      }
      &.down{
        top: 15%;
        transition: top 0.2s;
      }
    }
  }
  #login{
    padding: 0 10px;
  }
  ul{
    width: 100%;
    li{
      width: 50%;
      display: inline-block;
      height: 50px;
      line-height: 50px;
      font-size: 24px;
      letter-spacing: 5px;
      padding: 10px 0;
      cursor: pointer;
      &.on{
        background-color: #af0d10;
        color: white;
      }
      &.off{
        background-color: rgba(238, 238, 238, 0.5);
        color: #000000;
      }
    }
  }
  #register{
    padding: 0 10px;
    input[type=radio]{
      height: 10px;
      width: 10px;
    }
    input+div{
      text-align: left;
      margin-left: 5px;
      color:#af0d10;
      margin-top: 5px;
    }
  }
  button{
    width: 100%;
    margin: 10px 0;
    height: 50px;
    background-color: #af0d10;
    border: 0;
    font-size: 18px;
    letter-spacing: 4px;
    color: white;
    cursor: pointer;
  }
  input+div#rdo{
    height: 25px;
    line-height: 25px;
    margin-top: 10px;
    padding: 10px 0;
    margin-left: 0;
    background-color: #fff;
    text-align: left;
    border:1px solid #eee;
    label{
      margin-left: 20px;
    }
    div{
      position: relative;
      display: inline-block;
      width: 20%;
      label{
        position: absolute;
        left: 5px;
        top: 3px;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        border: 1px solid #af0d10;
        &+span{
          position: relative;
          left: 40px;
        }
      }
    }
  }
  input[type=radio] {
    width: 20px;
    height: 20px;
    opacity: 0;
    display: inline-block;
    margin: 0 auto;
    &:checked+label{
      background-color: #af0d10;
      border: 1px solid #af0d10;
      &::after{
        position: absolute;
        content: "";
        width: 5px;
        height: 10px;
        top: 3px;
        left: 6px;
        border: 2px solid #fff;
        border-top: none;
        border-left: none;
        transform: rotate(45deg);
      }
    }
  }


</style>

