import http from '../http'

export function search (params) {
  return http({
    url: '/food/search',
    method: 'get',
    params
  })
}
