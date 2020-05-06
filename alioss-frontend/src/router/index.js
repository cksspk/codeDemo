import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // },
    {
      path: '/list',
      name: 'list',
      component: () => import('@/views/list'),
    },
    // {
    //   path: '/1',
    //   name: '1',
    //   component: () => import('@/views/AliUpload'),
    // },
    {
      path: '/',
      // name: 'list',
      component: () => import('@/views/AliUpload'),
    }
  ]
})
