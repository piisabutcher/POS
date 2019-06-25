<template>
    <div class="pane" v-show="show">
      <slot></slot>
    </div>
</template>

<script>
    export default {
      name: "Pane",
      props:{
        name:{//指定标签的唯一字符串
          type: String,
        },
        label:{//指定标签的名称
          type: String,
          default:''
        }
      },
      data(){
        return{
          show: true
        }
      },
      methods:{
        updateNav(){
          this.$parent.updateNav();
        },
      },
      watch: {
        label(){
          this.updateNav();
        }
      },
      mounted() {//pane初始化时，调用一遍tabs的update方法，同时监听label
        this.updateNav();
      }
    }
</script>

<style scoped lang="scss">
  .pane{
    background: #eeeeee;
    height: 100px;
    border: 1px solid black;
  }

</style>
