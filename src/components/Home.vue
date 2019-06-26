<template>
    <div id="home">
      <Header @changeView="changeView"></Header>
      <router-view></router-view>
    </div>
</template>

<script>
    import Header from "./Header";
    export default {
      name: "Home",
      components: {Header},
      data(){
        return{

        }
      },
      created(){
        this.changeView(0);
      },
      methods: {
        changeView: function (index) {
          switch (index) {
            case 0 : {this.$router.push("/home/order");}break;
            case 1 : {this.$router.push("/home/statistics");}break;
            case 2 : {}break;
            case 3 : {}break;
            case 4 : {}break;
            case 5 : {}break;
            case 6 : {this.$router.push("/login");}break;
            case 7 : {this.$router.push("/home/order/orderList");}break;
            default: {this.$router.replace({name:"statistics"});}
          }

        },
      },

    }
</script>

<style scoped lang="scss">
  #home{
    width: 100%;
    height: 100%;
    overflow: hidden;
  }

</style>
