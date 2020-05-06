import request from '@/utils/request'

export function getPolicy(query){
    return request({
      //接口3.0
      //没有回调接口
      // url: '/api/aliOss/policy',
      //回调接口
      url: '/api/aliOss/policyCallback',
      method: 'get',
      params: query
      })
}