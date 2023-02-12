import axios from '../js_sdk/uni-axios'
import { getToken, removeToken } from './auth-token'

import server from './server'

function _reqlog (req) {
  if (process.env.NODE_ENV === 'development') {
    console.debug('请求地址：' + req.url, req.data || req.params)
  }
}

/**
 * 响应接口日志记录
 */
function _reslog (res) {
  if (process.env.NODE_ENV === 'development') {
    console.debug(`${res.config.url}响应结果：`, res)
  }
}

// 创建自定义接口服务实例
const http = axios.create({
  // baseURL: [baseURL],
  timeout: 60000, // 不可超过 manifest.json 中配置 networkTimeout的超时时间
  // #ifdef H5
  withCredentials: true,
  // #endif
  headers: {
    'Content-Type': 'application/json'
    // 'X-Requested-With': 'XMLHttpRequest',
  }
})

// 拦截器 在请求之前拦截
http.interceptors.request.use(config => {
  config.headers['x-auth-token'] = getToken()
  /**
	 * 处理protocol
	 * */
  config.url = server.url(config.url)
  _reqlog(config)
  return config
})

// 拦截器 在请求之后拦截
http.interceptors.response.use(response => {
  _reslog(response)
  const { code } = response
  const res = response.data
  // if the custom code is not 20000, it is judged as an error.
  if (!res.success) {
    // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
    if (response.status === 401 || response.status === 403) {
      // to re-login
      removeToken()
      uni.redirectTo({
        url: '/pages/login'
      })
    }
  } else {
    return res
  }
  return res
}, error => {
  console.error(error)
  const { response } = error
  const { status } = response
  if (status === 401 || status === 403) {
    removeToken()
    uni.redirectTo({
      url: '/pages/login'
    })
    return
  }
  return Promise.reject(error.message)
})

export default http
