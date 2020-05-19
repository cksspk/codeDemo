<template>
  <div class="header">
      <van-icon name="arrow-left" @click="goback()"/>
      <h3 class="desc_title">
          {{title}}
      </h3>
  </div>
</template>

<script>
export default {
    name:'Navbar',
    props:['title'],
    data(){
        return {

        }
    },
    methods:{
        goback(){
            console.log('点击全局返回按钮')
            this.$router.go(-1)
        }
    }

}
</script>

<style scoped>

.header{
    padding-left: 5px;
    display: flex;
    align-items: center;
    height: 40px;
    text-align:center
}
.desc_title{
    flex:1;
}

</style>