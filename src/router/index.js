import Vue from 'vue'
import Router from 'vue-router'
//1. 导入组件
import Home from '@/components/home/index'
import Vip from '@/components/vip/index'
import Shopcart from '@/components/Shopcart/index'
import Search from '@/components/search/index'

//新闻列表
import NewsList from '@/views/news/index'
//新闻内容
import NewsDetail from '@/views/news/NewsDetail'

//图片列表
import PhotoList from '@/views/photo/index'

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
    //新闻列表
    {
      path: '/news/list',
      name: 'NewsList',
      component: NewsList
    },
     //新闻内容
     {
      path: '/news/detail',
      name: 'Detail',
      component: NewsDetail
    },
     //图片列表
     {
      path: '/photo/list/:categoryId',
      name: 'photo.list',
      component: PhotoList
    },
    {
      path: '/test',
      name: 'Test',
      component: Test
    }
    
  ]
})
