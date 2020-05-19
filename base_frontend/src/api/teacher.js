import request from '@/utils/request' //模板提供的组件，axios封装

export default {
    //分页条件查询的方法
    //三个参数：当前页，每页记录数，条件封装对象
    getTeacherPageList(page,limit,searchObj) {
        return request({
            //后端controller里面的路径
            url: '/eduservice/teacher/moreCondtionPageList/'+page+'/'+limit,
            //提交方式
            method: 'post',
            //传递条件对象,如果传递json数据，使用data。如果不是json，使用params
            data: searchObj
        })
    },
    deleteTeacherId(id) {
        return request({
            //后端controller里面的路径
            url: '/eduservice/teacher/'+id,
            //提交方式
            method: 'delete'
        })
    }
}