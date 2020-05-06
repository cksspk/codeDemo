//upLoaderImg.js
import axios from 'axios'		//引入axios
import { Toast} from 'vant'	//引入Toast
import uuid from 'uuid'
// import request from '@/utils/request'

export function upLoadImg (file, params) {
            const host = params.host
            let formData = new FormData()
            // debugger
            let fileName = `${uuid.v4()}.${file.type.split('/')[1]}`  //定义唯一的文件名
            const keyValue = params.dir+fileName
            // 文件名字，可设置路径
            // formData.append('key', keyValue)
            // formData.append('key', params.dir+"/${filename}")
            // formData.append('key', params.dir+"${filename}")
            // console.log("uuid名称：",fileName)
            formData.append('key', params.dir+fileName)
            // policy规定了请求的表单域的合法性
            formData.append('policy', params.policy)
            // Bucket 拥有者的Access Key Id
            formData.append('OSSAccessKeyId', params.ossAccessKeyId)
            // 让服务端返回200,不然，默认会返回204
            formData.append('success_action_status', '200')
            // 根据Access Key Secret和policy计算的签名信息，OSS验证该签名信息从而验证该Post请求的合法性


            //签名后回调内容 参数前后顺序有要求，否则回调不会触发
            if(params.callback){
                formData.append('callback', params.callback)
            }
            
            formData.append('signature', params.signature)
            // formData.append('name', fileName)
            // 需要上传的文件filer
            formData.append('file', file)

        //   debugger
            //请求alioss
            return new Promise((resolve, reject) => {
                axios({
                    url: host,
                    header:{
                    'Content-Type': 'multipart/form-data'
                    },
                    method: 'post',
                    data: formData
                })
                .then(res => {
                    // debugger
                    //调用resolve修改Promise状态
                //    resolve(res)
                    if(res.status === 200){
                        const url = host+"/"+params.dir+fileName
                        // const url = host+"/"+params.dir+file.name
                        //返回url地址
                        resolve(url)
                    }else if(res.status === 203){
                        console.log('上传成功，回调地址失败');
                        reject(res)
                    }else{
                        reject(res)
                    }
                }).catch(err=>{
                    // debugger
                    console.log(err)
                    reject(err)
                })
            })
}


// export function upLoaderImg (params) {	//file 读取成功的回调文件信息
//     //1. 请求凭证  可以添加判断凭证是否过期
//     return getPolicy().then(resp=>{
//         console.log(resp)
//         resp = resp.data
//         // debugger
//         if(resp.success){
//             // debugger
//             //new 一个FormData格式的参数
//             console.log('请求授权返回',resp)
//             const host = resp.data.data.host
//             let formData = new FormData()
//             // debugger
//             let fileName = `${uuid.v4()}.${params.file.type.split('/')[1]}`  //定义唯一的文件名
//             const keyValue = resp.data.data.dir+fileName
//             // 文件名字，可设置路径
//             formData.append('key', keyValue)
//             // policy规定了请求的表单域的合法性
//             formData.append('policy', resp.data.data.policy)
//             // Bucket 拥有者的Access Key Id
//             formData.append('OSSAccessKeyId', resp.data.data.ossAccessKeyId)
//             // 让服务端返回200,不然，默认会返回204
//             formData.append('success_action_status', '200')
//             // 根据Access Key Secret和policy计算的签名信息，OSS验证该签名信息从而验证该Post请求的合法性
//             // formData.append('callback', params.callback)
//             formData.append('signature', resp.data.data.signature)
//             formData.append('name', fileName)
//             // 需要上传的文件filer
//             formData.append('file', params.file)
            
//             return new Promise((resolve, reject) => {
//                 axios({
//                     url: host,
//                     header:{
//                     'Content-Type': 'multipart/form-data'
//                     },
//                     method: 'post',
//                     data: formData
//                 })
//                 .then(res => {
//                     if(res.status===200){
//                         console.log(`阿里云OSS上传图片成功回调`);
//                         const url = host+"/"+keyValue
//                         //返回url地址
//                         resolve(url)
//                     }
//                 }).catch((err) => {
//                     console.log(`阿里云OSS上传图片失败回调`, err);
//                     Toast.fail('系统异常')
//                     reject(err)
//                 });
//             })
//         }
//     })
// }

// function getPolicy(){
//     return request({
//         url: '/aliOss/policy',
//         method: 'get',
//         // params: query
//       })
// }


