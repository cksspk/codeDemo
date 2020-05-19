<template>
<!-- 顶部标签 -->
  <div id="app">
    <van-nav-bar
      title="标题"
      left-text="返回"
      right-text="按钮"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    />
    <!-- 路由组件 -->
    <router-view/>
    <!-- 底部组件 -->
    <!-- <van-tabbar v-model="active" @change="onChange">
      <van-tabbar-item icon="home-o">首页</van-tabbar-item>
      <van-tabbar-item icon="freinds-o">会员</van-tabbar-item>
      <van-tabbar-item icon="shopping-cart-o">购物车</van-tabbar-item>
      <van-tabbar-item icon="setting-o">查找</van-tabbar-item>
    </van-tabbar> -->
    
    <!-- 使用路由模式 -->
    <van-tabbar route>
      <van-tabbar-item replace to="/home" icon="home-o">
        首页
      </van-tabbar-item>
      <van-tabbar-item replace to="/vip" icon="home-o">
        会员
      </van-tabbar-item>
      <van-tabbar-item replace to="/shopcart" icon="shopping-cart-o">
        购物车
      </van-tabbar-item>
      <van-tabbar-item replace to="/search" icon="search">
        查找
      </van-tabbar-item>
    </van-tabbar>

  </div>
</template>

<script>
import { Toast, Notify} from 'vant';
  
export default {
  name: 'App',
  data(){
    return{
      active: 0,
    }
  },
   methods: {
    onClickLeft() {
      // Toast('返回');
      this.$router.go(-1)
    },
    onClickRight() {
      Toast('按钮');
    },
     onChange(index) {
      Notify({ type: 'primary', message: index });
    },
  },
}
</script>

<style>

</style>
