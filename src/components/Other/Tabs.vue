<template>
    <div class="tabs">
      <div class="tabs-bar">
        <div v-for="(item, index) in navList">
          {{item}}
        </div>
      </div>
      <div class="tabs-content">
        <!--这里的slot是嵌套的pane-->
        <slot></slot>
      </div>
    </div>
</template>

<script>
    export default {
      name: "Tabs",
      data(){
        return{
          currentValue:this.value,
          navList:[]
        }
      },
      props:{
        value:{
          type:[String,Number]
        }
      },
      methods:{
        tabCls: function (item) {
          return [
            'tabs-tab',
            {
              'tabs-tab-active': item.name === this.currentValue
            }
          ]
        },
        getTabs(){
          return this.$children.filter(function (item) {
            return item.$options.name === 'pane';
          });
        },
        updateNav(){
          console.log("tab监听到了:)");
          this.navList = [];
          const _this = this;
          this.getTabs().forEach(function (pane, index) {
              console.log(pane + index);
              _this.navList.push({
              label: pane.label,
              name: pane.name || index
            });
            if(!pane.name) pane.name = index;
            if(index === 0){
              if(!_this.currentValue){
                _this.currentValue = pane.name || index;
              }
            }
          });
          this.updateStatus();
        },
        updateStatus: function () {
          let tabs = this.getTabs();
          const _this = this;
          tabs.forEach(function (tab) {
            return tab.show = tab.name === _this.currentValue;
          });
        },
        handleChange: function (index) {
          var nav = this.navList[index];
          var name = nav.name;
          this.currentValue = name;
          this.$emit('input',name);
          this.$emit('on-click', name);
        }
      },
      watch: {
        value: function (val) {
          this.currentValue = val;
        },
        currentValue: function () {
          this.updateStatus();
        }
      }

    }
</script>

<style scoped lang="scss">
  .tabs{
    .tabs-bar{
      background-color: red;

    }
  }

</style>
