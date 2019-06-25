<template>
    <div></div>
</template>

<script>
    export default {
      name: "Empty",
      data() {
        return{

        }
      },
      created(){
        this.$router.back();
      }
    }
</script>

<style scoped>

</style>
