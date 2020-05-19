<template>
  <div>
    <!-- 轮播图 -->
    <div class="swiper">
        <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
            <van-swipe-item v-for="(item, index) in images" :key="index">
              <img v-lazy="item.image" />
            </van-swipe-item>
        </van-swipe>
    </div>
    
      
      <!-- 宫格组件 -2 -->
      <van-grid :column-num="4"  :border=false>
        <van-grid-item v-for="item in grids" 
          :key="item.id"  
          :icon ="item.src" 
          :text="item.title"
          :to="item.router"
        />
      </van-grid>

  </div>
</template>

<script>
import Vue from 'vue';
import { Lazyload } from 'vant';
import g1 from '../../assets/grids/news.jpg'
import g2 from '../../assets/grids/2.png'
import g3 from '../../assets/grids/3.png'
import g4 from '../../assets/grids/4.png'
import g5 from '../../assets/grids/5.png'
import g6 from '../../assets/grids/6.png'
import g7 from '../../assets/grids/7.png'
import g8 from '../../assets/grids/8.png'
import g9 from '../../assets/grids/9.png'
import g10 from '../../assets/grids/10.png'


Vue.use(Lazyload);


//九宫格数据
var grids = [
  {id:1,src: g1, title:'新闻资讯',router:{name:'NewsList'}},
  {id:2,src: g2, title:'肉禽蛋类',router:{name:'Test'}},
  {id:3,src: g3, title:'水产海鲜',router:{name:'NewsList'}},
  {id:4,src: g4, title:'新鲜水果',router:{name:'news.list'}},
  {id:5,src: g5, title:'粮油调味',router:{name:'news.list'}},
  {id:6,src: g6, title:'网红卤味',router:{name:'news.list'}},
  {id:7,src: g7, title:'腌制净菜',router:{name:'news.list'}},
  {id:8,src: g8, title:'烟酒保健',router:{name:'news.list'}},
  // {id:9,src: g9, title:'豆腐工坊'},
  // {id:10,src: g10, title:'饮料小吃'},
]
export default {
  data(){
    return {
      //轮播图数据
      images :[],
      //九宫格数据
      grids:grids,
    }
  },
  created(){
    
    //请求本地json数据
    this.$axios.get('static/data/swiper.json').then(resp=>{
      this.images = resp.data
    }).catch(err=>{
      console.log('err:',err);
      
    })
  }
}
</script>

<style scoped>
  .swiper {
    width: 100%;
    height: 9.8rem;
  }
  .van-swipe{
    width: 100%;height: 100%;
  }
  img {
    width: 100%;
    height: 100%;
    pointer-events: none;
  }
  .custom-indicator {
    position: absolute;
    right: 5px;
    bottom: 5px;
    padding: 2px 5px;
    font-size: 12px;
    color: #fff;
    background: #fff;
  }
  
  /* 九宫格样式 */
/* .mygrids{
  padding: 0 10px;
  text-align: center;
} */
</style>