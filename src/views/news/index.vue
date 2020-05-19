<template>
  <div>
    <Navbar title="新闻列表"/>
    <!-- <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      @load="onLoad"
      > -->
      <!-- <van-cell v-for="item in list" :key="item" :title="item" /> -->
      <div class="news">
        <div class="newList">
            <div ></div>
          <ul v-for="(news) in newsList" :key="news.id">
          <!-- <ul> -->
            <li>
              <router-link :to='{name:"Detail",query:{id:news.id}}' href="#">
                <div class="new_img">
                  <!-- <img src="https://n.sinaimg.cn/default/feedbackpics/transform/116/w550h366/20180418/w4eZ-fzihnep5214985.png" alt=""> -->
                  <img :src="news.img_url" alt="">
                </div>
                <div class="content">
                  <!-- <p class="title">今天天气很不错</p> -->
                  <p class="title">{{news.title}}</p>
                  <div class="new_desc">
                    <!-- <p class="summary">摘要内容</p> -->
                    <p class="summary">{{news.zhaiyao}}</p>
                    <p>
                      <span class="praise">点赞数：{{news.click}}</span>
                      <!-- <span class="time">发表时间：{{moment(news.add_time).format('YYYY-MM-DD HH:mm:SS')}}</span> -->
                      <span class="time">发表时间：{{news.add_time | dateformat('YYYY-MM-DD')}}</span>
                    </p>
                  </div>
                </div>
                </router-link>
              <!-- </a> -->
            </li>
          </ul>

          <!-- 原型 -->
           <!-- <ul>
            <li>
              <a href="#">
                <div class="new_img">
                  <img src="https://n.sinaimg.cn/default/feedbackpics/transform/116/w550h366/20180418/w4eZ-fzihnep5214985.png" alt="">
                </div>
                <div class="content">
                  <p class="title">今天天气很不错</p>
                  <div class="new_desc">
                    <p class="summary">摘要内容</p>
                    <p>
                      <span class="praise">点赞数</span>
                      <span class="time">发表时间</span>
                    </p>
                  </div>
                </div>
              </a>
            </li>
          </ul> -->
        </div>
        
      </div>
      
      <van-divider>分割线</van-divider>

      <!-- 模板 -->
      <!-- <ul class="news">
        <li class="list list-right-img">
          <div class="left">
            <div class="new">
              <p class="title list-ellipsis-1">这是风水堪舆</p>
              <p class="content">风水之法，得水为上，藏风次之。”后世术家兼作“堪舆”的代称，相宅、相墓之法，辨证研究风水，去其糟粕，取其精华，用科学合理的方法来解释风水现象，只可用于趋吉避凶，不可痴迷其中。</p>
            </div>
            <span class="time">2018-10-22</span>
          </div>
          <div class="right">
            <img src="https://n.sinaimg.cn/default/feedbackpics/transform/116/w550h366/20180418/w4eZ-fzihnep5214985.png" alt="">
          </div>
        </li>
      </ul> -->
    <!-- </van-list> -->
    <van-divider>分割线</van-divider>
  </div>    
</template>

<script>
//使用momentjs对时间进行格式化
// let moment = require("moment");
export default {
  // name:'newsList',
  data() {
    return {
      list: [],
      loading: false,
      finished: false,
      //新闻
      newsList:[],
    };
  },
  created(){
    console.log('create');
    // console.log('moment：',moment);
    // console.log('moment:',moment('2019-06-04T02:51:31.000+0000').format('YYYY-MM-DD HH:mm:SS'));
    

    //请求本地json数据
    this.$axios.get('static/data/newsList.json').then(resp=>{
      // console.log('请求数据成功：',resp.data);
      this.newsList = resp.data
    }).catch(err=>{
      console.log('err:',err);
    })
  },
  methods: { 
    onLoad() {
      // 异步更新数据
      // setTimeout 仅做示例，真实场景中一般为 ajax 请求
      setTimeout(() => {
        for (let i = 0; i < 10; i++) {
          this.list.push(this.list.length + 1);
        }

        // 加载状态结束
        this.loading = false;

        // 数据全部加载完成
        if (this.list.length >= 40) {
          this.finished = true;
        }
      }, 1000);
    },
  },
}
</script>

<style  scoped>

/** 图片在左侧 */
.newList a{
  display: flex;
  align-items: center;
}

.new_img{
  width: 30%;
  /* padding: 10px; */
  /* margin-right: 50px; */
}
.new_img img{
  width: 100%;
}

.newList .content{
  width: 60%;
  overflow: hidden;
  /* display: flex; */
}

/* .newList li{
  margin-top: 20px;
} */
.newList .title{
  color: #1a1a1a;
  /* font-weight: bold; */
  font-weight: 400;
  font-size: 14px;
}
.newList .new_desc{
  font-size: 12px;
  color: #888;
}

.time{
  margin-left: 30px;
}


/* 第二个，图片在右侧 */
.news {
    background: #fff;
}

.news .list {
    display: flex;
    display: -webkit-flex;
    padding: 10px 5px 5px;
    margin: 0 15px;
    border-bottom: 1px solid #ddd;
    /* flex-direction: row; */
}

.news .list:last-child {
    border: 0;
}

/* 右图模式 */

.list-right-img .left {
    width: 60%;
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: space-between;
}

.list-right-img .left .title {
    font-weight: bold;
    height: 30%;
    font-size: 14px;
}

.list-right-img .left .content {
    font-size: 12px;
    color: #888;
    margin: 5px 0;
    display: -webkit-box;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    line-height: 16px;
}

.list-right-img .left .time {
    font-size: 10px;
    color: #bbb;
}

.list-right-img .right {
    width: 40%;
    margin-left: 10px;
}

.list-right-img .right img {
    width: 100%;
    height: 80px;
}


</style>