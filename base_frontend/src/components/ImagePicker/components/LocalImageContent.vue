<template>
  <div>
    本地服务器共有{{imageList.length}}张图
    <el-row :gutter="12">
      <el-col :span="8" v-for="item in imageList" :key="item">
        <div @click="onImgSelect(item)">
          <el-card shadow="hover" :class="{'selectImg':item.id==index}">
            <img :src=" baseApi + '/file/' + item.type + '/' + item.realName" class="image">
            <div style="padding: 14px;">
              <span class="name" :title="item.name">{{item.name}}</span>
              <div class="bottom clearfix">
                <time class="time">{{ item.createTime }}</time>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col span="8">
        123
        <el-upload
          class="avatar-uploader"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {listLocalImage} from "@/api/common"

  export default {
    name: "LocalImageContent",

    data() {
      return {
        /* <el-upload> 组件 */
        imageUrl: '',
        // baseApi: process.env.VUE_APP_BASE_API,
        baseApi : '/dev-api',
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
      this.getLocalImageContent()
    },
    methods: {
      load() {
        setTimeout(() => {
          this.queryParams.pageNum++;
          this.getLocalImageContent();
        }, 1000)
      },
      onImgSelect(item) {
        this.index = item.id;
        this.$emit('onImgSelect', this.baseApi + '/file/' + item.type + '/' + item.realName);
      },
      getLocalImageContent() {
        if (this.noMore) {
          return
        }
        this.loading = true;
        listLocalImage(this.queryParams).then(response => {
          if (this.imageList.length == response.total) {
            this.noMore = true;
          }
          this.imageList.push(...response.rows);
          this.loading = false;
        });
      },

      
      /* <el-upload> 组件methods */
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
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
  /* .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  } */
</style>
