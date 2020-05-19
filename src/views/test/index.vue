<template>
    <!-- 手机端新闻列表展示 样式demo-->
    <div>
        <ul class="news">
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
        </ul>
    </div>
</template>

<script>
export default {

}
</script>

<style>
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