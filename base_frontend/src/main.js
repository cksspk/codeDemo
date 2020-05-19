import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import './assets/styles/element-variables.scss'
import 'element-ui/lib/theme-chalk/index.css'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css
import '@/assets/styles/index.scss' // global css
import '@/assets/styles/dimple.scss' // 自定义样式



import App from './App'
import router from './router'
import store from './store'

import '@/icons' // icon
import '@/permission' // permission control


import {parseTime, resetForm, addDateRange, selectDictLabel} from "@/utils/util";
//分页组件
import Pagination from "@/components/Pagination";

// 全局方法挂载
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;

//全局提示组件
Vue.prototype.msgSuccess = function (msg) {
  this.$message({showClose: true, message: msg, type: "success"});
};

Vue.prototype.msgError = function (msg) {
  this.$message({showClose: true, message: msg, type: "error"});
};

Vue.prototype.msgWarning = function (msg) {
  this.$message({showClose: true, message: msg, type: "warning"});
};


Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
};

// 全局组件挂载
Vue.component('Pagination', Pagination);

Vue.use(ElementUI, { locale })

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router, //挂载路由
  store,
  render: h => h(App)
})
