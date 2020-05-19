import request from '@/utils/request'

// 查询AliOss图片
export function listAliOssImage(query) {
  return request({
    url: '/tool/aliOss/list',
    method: 'get',
    params: query
  })
}

// 查询FastDfs图片
export function listFastDfsImage(query) {
  return request({
    url: '/tool/fastDfs/list',
    method: 'get',
    params: query
  })
}

// 查询本地图片
export function listLocalImage(query) {
  return request({
    url: '/tool/localStorage/list',
    method: 'get',
    params: query
  })
}

//上传图片
export function uploadImgToAliOss(data) {
  return request({
    url: '/tool/aliOss',
    method: 'post',
    data: data
  })
}
