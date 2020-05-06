import axios from 'axios'

// 创建axios实例
const service = axios.create({
//   baseURL: process.env.BASE_API, // api 的 base_url
    baseURL: 'http://127.0.0.1:8999',
  // timeout: 5000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    // if (store.getters.token) {
    //   config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    // }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

export default service
