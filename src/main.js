// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Axios from 'axios'

//使用vant引入全部组件
import vant from 'vant'

//全局css
import './assets/resset.css'
import 'vant/lib/index.css'

//使用moment对时间进行格式化
import moment from 'moment'
Vue.filter('dateformat', function(dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)

})
//全局挂载vant
Vue.use(vant)

//使用normalize样式
import 'normalize.css'

//配置Axios
Vue.prototype.$axios = Axios;

Vue.config.productionTip = false

//注册全局导航组件

import Navbar from '@/components/common/Navbar'

Vue.component(Navbar.name,Navbar)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
