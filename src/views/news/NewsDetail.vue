<template>
  <div class="detail">
    <Navbar title="新闻详情"/>
    <div class="item">
        <p>{{detail.title}}</p>
        <div>
            <span>{{detail.click}}次点击</span>
            <span>分类：民生经济</span>
            <span>添加时间：{{detail.add_time | dateformat('YYYY-MM-DD')}}</span>
        </div>
        <van-divider />
        <div class="detail-content">{{detail.content}}</div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
      detail:{}
    }
  },
  created(){
    console.log('新闻详情created');
    let id = this.$route.query.id 
    // console.log('id:',id);
    //请求本地json数据
    this.$axios.get('static/data/detail.json').then(resp=>{
      // console.log('请求数据成功：',resp.data);
      // this.newsList = resp.data
      this.detail = resp.data
    }).catch(err=>{
      console.log('err:',err);
    })
    
  }

}
</script>

<style scoped>
.item p{
    color: #1a1a1a;
    font-size: 25px;
}
.item span{
    font-size: 12px;
    margin-right: 25px;
    /* background-color: aqua; */
}
</style>