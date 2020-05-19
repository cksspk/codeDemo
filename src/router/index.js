import Vue from 'vue'
import Router from 'vue-router'
//1. 导入组件
import Home from '@/components/home/index'
import Vip from '@/components/vip/index'
import Shopcart from '@/components/Shopcart/index'
import Search from '@/components/search/index'

//新闻列表
import NewsList from '@/views/news/index'
import Test from '@/views/test/index'

Vue.use(Router)

/**
 * 路由组件
 */
export default new Router({
  routes: [
    //测试路由
    {path: '/', redirect : 'home'},
    {
      path: '/home',
      name: 'Home',
      component: Home,
    },
    {
      path: '/vip',
      name: 'Vip',
      component: Vip
    },
    {
      path: '/shopcart',
      name: 'Shopcart',
      component: Shopcart
    },
    {
      path: '/search',
      name: 'Search',
      component: Search,
    },
    {
      path: '/list',
      name: 'NewsList',
      component: NewsList
    },
    {
      path: '/test',
      name: 'Test',
      component: Test
    }
    
  ]
})
