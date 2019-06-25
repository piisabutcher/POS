// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from "element-ui"
import Axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/font-awesome.min.css'
import './assets/css/demo.css'
//import "../mock/mock.js"

Vue.config.productionTip = false;
Vue.use(ElementUI);
//将axios写成vue的原型属性
Vue.prototype.$axios = Axios;

/* eslint-disable no-new */
new Vue({
  http:{

  },
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
