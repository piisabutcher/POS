import Vue from 'vue'
import Router from 'vue-router'
import Login from "../components/Login";
import Home from "../components/Home";
import Empty from "../components/Empty"
import Admin from "../components/Admin"
import Order from "../components/Order/Order";
import OrderPicker from "../components/Order/OrderPicker";
import Statistics from "../components/Statistics/Statistics";
import TablePicker from "../components/Order/TablePicker";
import OrderList from "../components/Order/OrderList"
import OrderPay from "../components/Order/OrderPay"
import User from "../components/User/User"
import Food from "../components/Food/Food"
import Catalog from  "../components/Food/Catalog"
import Charts from "../components/Statistics/Charts";



Vue.use(Router);


export default new Router({
  mode: 'history',
  routes: [{
    path: '/',
    component: Login
  },{
    path: '/login',
    component: Login
  },{
    path: '/admin',
    component: Admin,
    children:[
      { path:"/", component:Catalog },
      { path:'user', component:User },
      { path:'food/:catalog', component:Food },
      { path:'catalog', component:Catalog },
    ]
  },{
    path: '/home',
    component: Home,
    children:[
      {
        path:'/',
        component: Order,
      },
      {
        path: 'order',
        component: Order,
        children: [
          {path: '/', component: OrderPicker},
          {path:'orderPicker', component:OrderPicker},
          {path:'tablePicker', component:TablePicker},
          {path:'orderList', component: OrderList},
          {path:'orderPay', component: OrderPay}
        ]
      },
      {
        path: 'statistics',
        component: Statistics,
      }
    ]
  },{
    path: '/empty',
    component: Empty
  }],

})

