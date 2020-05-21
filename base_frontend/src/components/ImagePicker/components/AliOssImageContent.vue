<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="8" v-for="item in imageList" :key="item.id">
        <div @click="onImgSelect(item)">
          <el-card shadow="hover" :class="{'selectImg':item.id==index}">
            <img :src="item.url" class="image">
            <div style="padding: 14px;">
              <span class="name" :title="item.name">{{item.name}}</span>
              <div class="bottom clearfix">
                <time class="time">{{ item.createTime }}</time>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
        <!-- 上传图片到AliOss -->
      <el-col :span="8">
        <el-upload
          class="avatar-uploader cb-ava"
          :action="BASE_API+'/tool/aliOss'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <!-- <img v-if="imageUrl1" :src="imageUrl1" class="avatar"> -->
          <!-- <i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {listAliOssImage} from "@/api/common"

  export default {
    name: "AliOssImageContent",

    data() {
      return {
        BASE_API: process.env.BASE_API, // 接口API地址
        index: 0,
        imageList: [],
        // 查询参数
        queryParams: {
          pageNum: 0,
          pageSize: 2000,
          orderByColumn: "createTime",
          isAsc: "desc"
        },
        loading: false,
        noMore: false,
      }
    },
    mounted() {
      this.getAliOssImageContent()
    },
    methods: {
      load() {
        setTimeout(() => {
          this.queryParams.pageNum++;
          this.getAliOssImageContent();
        }, 1000)
      },
      onImgSelect(item) {
        this.index = item.id;
        this.$emit('onImgSelect', item.url);
      },
      getAliOssImageContent() {
        if (this.noMore) {
          return
        }
        this.loading = true;
        listAliOssImage(this.queryParams).then(response => {
          if (this.imageList.length == response.total) {
            this.noMore = true;
          }
          this.imageList.push(...response.rows);
          this.loading = false;
        });
      },

      /* <el-upload> 组件methods 新上传的文件没有时间参数是因为给，时间在数据库中生成*/
      handleAvatarSuccess(res, file) {
        // debugger
        if(res.code === 200){
           this.imageList.push(res.data);
        }else{
          this.$message.error('上传图片失败');
        }
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    }
  }
</script>

<style scoped>
  .selectImg {
    /*filter: blur(1px);*/
    border-radius: 4px !important;
    border: 1px solid #67C23A !important;
    background-color: #DCDFE6 !important;
    overflow: hidden !important;
    color: #409EFF !important;
  }

  .selectImg .el-card__body {
    background-color: cornflowerblue;
  }

  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    height: 100px;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .name {
    overflow: hidden;
    display: inline;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
  }

/* <el-upload> 组件样式 */
  /deep/ .cb-ava>div{
    height: 207px;
  }
</style>
