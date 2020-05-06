<template>
  <div>
        <h1>上传文件-场景一</h1>
        <van-uploader 
        :after-read="afterRead" 
        :before-read="beforeRead"
        />
        <van-list
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
        >
          <!-- @load="onLoad" -->
          <!-- <van-cell v-for="item in list" :key="item" :title="item" /> -->
          <van-image
            v-for="item in list" :key="item" :title="item"
            width="10rem"
            height="10rem"
            fit="contain"
            :src="item"
          />
            <!-- src="https://img.yzcdn.cn/vant/cat.jpeg" -->
        </van-list>
  </div>
</template>

<script>
import Vue from 'vue';
import { Uploader, Form } from 'vant';
import {upLoaderImg, upLoadImg} from '@/common/uploadImg'
import { Toast, List, Cell,Image as VanImage  } from 'vant'	//引入Toast
//导入请求
import {getPolicy} from '@/api/alioss'

Vue.use(Uploader);
Vue.use(List);
Vue.use(Cell);
Vue.use(VanImage);
export default {
  data() {
    return {
      list: [],
      loading: true,
      finished: true,
    };
  },
  
 methods: {
    //文件校验
    beforeRead(file){
      // console.log('文件校验')
      const isJPEG = file.name.split('.')[1] === 'jpeg';
      const isJPG = file.name.split('.')[1] === 'jpg';
      const isPNG = file.name.split('.')[1] === 'png';
      const isLt500K = file.size / 1024 / 500 < 2;
      if (!isJPG && !isJPEG && !isPNG) {
         Toast.fail('上传图片只能是 JPEG/JPG/PNG 格式!');
      }
      if (!isLt500K) {
         Toast.fail('单张图片大小不能超过 500KB!');
      }
      return (isJPEG || isJPG || isPNG) && isLt500K;
    },
    
    // 请求凭证，上传文件到服务器
    afterRead(file) {
      this. finished = false
      //请求后台获取凭证，然后直传阿里Oss
      getPolicy().then(resp=>{
        // debugger
        console.log('请求后台返回：',resp)
        if(resp.status === 200){
          //组织参数
          const data = resp.data.data.data
          //请求阿里oss
          upLoadImg(file.file,data).then(url=>{
            // debugger
            this.finished = true
            console.log('请求阿里oss返回',url)
            this.list.push(url);
          }).catch(err=>{
            // debugger
            this.finished = true
            Toast.fail('系统异常')
            console.log('请求aliyun异常')
          })
        }
      }).catch(err=>{
        Toast.fail('系统异常')
        this.finished = true
        console.log('error12312321',err)
      })

      
      // upLoaderImg(file).then(res=>{
      //   // debugger
      //   console.log('调用上传产生的url:',res)
      //   this. finished = true,
      //   this.list.push(res);
      // }).catch((err) => {
      //   this. finished = true,
      //   Toast.fail('系统异常',err)
      // });
    },
  },
}

</script>

<style>

</style>