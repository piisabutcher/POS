<template>
  <div id="catalog">
    <div v-for="catalog in catalogList"
          @click="changeView(catalog.catalogId)">
      <div>
        <img :alt="catalog.catalogName"
             :src="catalog.catalogImg"/>
        <div>{{catalog.catalogName}}</div>
      </div>
    </div>
    <div id="add">
      <i class="fa fa-plus"></i>
    </div>
  </div>


</template>

<script>
    export default {
      name: "Catalog",
      data(){
        return{
          catalogList:[],

        }
      },
      created() {
        this.getAllCatalog();

      },
      methods: {
        getAllCatalog(){
          this.$axios.get("/api/catalog/getAllCatalog")
            .then(res=>{
              this.catalogList = res.data.dataList;
            });
        },
        changeView(catalogId){
          this.$router.push("/admin/food/"+catalogId);
        }
      }
    }
</script>

<style scoped lang="scss">
  #catalog{

    width: 100%;
    height: calc(100% - 70px);
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    overflow: auto;
    div{
      width: 300px;
      height: 220px;
      background-color: yellowgreen;
      margin: 10px;
      position: relative;
      overflow: hidden;
      div{
        width: 100%;
        height: 100%;
        position: absolute;
        top: -10px;
        left: -10px;
        transition: all 0.5s;
        &:hover{
          transform: scale(1.2);
          transition: all 0.8s;
          cursor: pointer;
        }
        img{
          width: 100%;
          height: 100%;
          display: block;
          object-fit: cover;

        }
        div{
          background: transparent url(../../../static/img/pattern.png) repeat top left;
          position: absolute;
          left: -10px;
          top: -10px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 50px;
          font-weight: bold;
          color: #fff;
          &:hover{
            font-size: 50px;
            transform: scale(0.9);
          }
        }

      }

    }
    #add{
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #dfdfdf;
      i{
        font-size: 90px;
        cursor: pointer;
        color: white;
      }
    }
  }


</style>
