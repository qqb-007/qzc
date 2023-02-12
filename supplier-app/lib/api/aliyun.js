import http from '../http'

export function acs (params) {
  return http({
    url: '/aliyun/sts/acs',
    method: 'get',
    params
  })
}
