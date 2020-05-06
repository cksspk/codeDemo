<template>
  <div>
        <h1>上传文件</h1>
        <div class = "sh-doc">
          <p>1. 基于vant封装</p>
          <p>2. 通过获取java后台签名前台直传</p>
          <p>3. 只能上传特定格式图片上传文件</p>
          <p>4. 后台回调需要外网ip/域名，nginx配置</p>
        </div>
        <!-- layout布局 -->
        <van-row type="flex" justify="center" class="sh-radio">
          <van-col>
          <!-- 单选框 通过name控制凭证过期时间-->
          <van-radio-group v-model="radio" direction="horizontal">
            <van-radio name="1">直接请求凭证</van-radio>
            <van-radio name="2">通过缓存凭证</van-radio>
          </van-radio-group>
          </van-col>
        </van-row>
       
        <!-- 上传组件 -->
        <van-uploader 
          :after-read="afterRead" 
          :before-read="beforeRead"
        />
        <!-- 展示组件 -->
        <van-list
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
        >

          <van-image
            v-for="item in list" :key="item" :title="item"
            width="10rem"
            height="10rem"
            fit="contain"
            :src="item"
          />
        </van-list>
  </div>
</template>

<script>
import Vue from 'vue';
import { Uploader, Form, CellGroup,RadioGroup, Radio, Col, Row  } from 'vant';
import {upLoaderImg, upLoadImg} from '@/common/uploadImg'
import { Toast, List, Cell,Image as VanImage  } from 'vant'	//引入Toast
//导入请求
import {getPolicy} from '@/api/alioss'

Vue.use(Uploader);
Vue.use(List);
Vue.use(Cell);
Vue.use(VanImage);
Vue.use(Radio);
Vue.use(RadioGroup);
Vue.use(Col);
Vue.use(Row);
export default {
  data() {
    return {
      list: [],
      loading: true,
      finished: true,
      radio: '1',       //单选按钮
      oss:{       //   oss签名数据
          key : null,
          dir : null,
          policy : null,
          ossAccessKeyId : null,
          callback : null,
          signature : null,
          expire : null,
      },
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
      
      var _this = this
      //设置参数
      const param = {
        fileType : this.radio
      }
      
      this. finished = false
      //请求后台获取凭证，然后直传阿里Oss
      const expire = Number(this.oss.expire)
      const now = new Date().getTime()/1000
      console.log("时间差值：",expire - now)
      console.log("请求前判断是否过期：",expire < now)
      
      if(_this.radio === "1" || expire < (now) ){
        console.log("**************请求后台凭证")
        getPolicy(param).then(resp=>{
        // debugger
        if(resp.status === 200){
          //组织参数
          const data = resp.data.data.data
          this.oss = data
          //请求阿里oss
          upLoadImg(file.file,data).then(url=>{
            // debugger
            this.finished = true
            console.log('请求阿里oss返回',url)
            this.list.push(url);
          }).catch(err=>{
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
      }else{
        //不请求后台
        console.log("**************通过缓存凭证请求alioss")
        upLoadImg(file.file,this.oss).then(url=>{
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
    },
  },
}

</script>

<style>
/* 样式调整 */
.sh-radio{
  margin: 20px 0 50px 0;
}
.sh-doc{
  margin-left: 10px;
  /* text-align: left; */
  /* display: flex; */
  /* align-items: center; */
   /* flex-direction:  row; */
}
.sh-doc>p{
  color: gray;
  margin: 0;
}
</style>