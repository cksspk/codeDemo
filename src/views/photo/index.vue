<template>
  <div class="photoList">
    <van-tabs v-model="active">
        <van-tab title="标签 1">
            <ul>
                <li>
                    <a href=""></a>
                    <p>
                        <span>图片摘要</span>
                        <br>
                        <span>图片摘要</span>
                    </p>
                </li>
            </ul>
        </van-tab>
        <van-tab title="标签 2">内容 2</van-tab>
        <van-tab title="标签 3">内容 3</van-tab>
        <van-tab title="标签 4">内容 4</van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
    data(){
        return {
            active: 0,
        }
    },
    created(){
        console.log('图片列表：created');
        
    }
}
</script>

<style>

</style>