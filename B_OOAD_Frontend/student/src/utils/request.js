import axios from 'axios'
import cookie from 'js-cookie'
// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8075', // api的base_url
  timeout: 20000 // 请求超时时间
})
service.interceptors.request.use(    //每次发送请求使用这个拦截器
  config => {
    //debugger
    // console.log("request format",config);
    if (cookie.get('user_token')) {
      config.headers['token'] = cookie.get('user_token');
    }
  
      return config
    },
    err => {
    return Promise.reject(err);
  })
 export default service