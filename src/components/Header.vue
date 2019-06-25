<template>
  <div id="header">
    <div id="logo">LOGO</div>
    <ul>
      <li v-for="(item,index) in navList"
          @click="changeContent(index)" :class="{selected:index === currentSelect}">
        <i :class="item.iClass"></i>
        {{item.name}}</li>
    </ul>
  </div>
</template>

<script>
    export default {
      name: "Header",
      data(){
        return{
          currentSelect: 0,
          navList:[
            {
              name: "订单",
              iClass: "fa fa-cny",
            },
            {
              name: "报表",
              iClass: "fa fa-line-chart",
            },{
              name: "会员",
              iClass: "fa fa-user-o"
            },{
              name: "消息",
              iClass: "fa fa-envelope-o"
            },{
              name: "当班帐单",
              iClass: "fa fa-list-alt"
            },{
              name: "交接班",
              iClass: "fa fa-link"
            },{
              name: "更多",
              iClass: "fa fa-chevron-down"
            },{
              name: "",
              iClass: "fa fa-list"
            },
          ]
        }
      },
      methods: {
        changeContent: function (index) {
          this.currentSelect = index;
          this.$emit("changeView", index);
        }
      }
    }
</script>

<style scoped lang="scss">
  #header{
    height: 50px;
    padding: 10px;
    background-color: #313236;
    div{
      color: white;
      height: 100%;
      display: inline-block;
      float: left;
      font-size: 38px;
      width: 30%;
    }
    ul{
      display: inline-block;
      float: left;
      height: 100%;
      width: 70%;
      li{
        display: inline-block;
        width: 100px;
        height: 100%;
        padding: 0 5px;
        text-align: center;
        color: #b3b4b8;
        line-height: 50px;
        &:hover{
          cursor: pointer;
        }
        i{
          margin-right: 10px;
        }
        &:last-child{
          background-color: #46474c;
          float: right;
          text-align: center;
          padding: 0;
          width: 50px;
          height: 50px;
          border-radius: 50%;
          i{
            margin: 0;
          }
        }
      }
    }
  }
  .selected{
    background-color: #46474c;
    border-radius: 2px;
    cursor: pointer;
  }

</style>
