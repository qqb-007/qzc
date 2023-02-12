import http from '../http'

export function search (params) {
  return http({
    url: '/food-supplier/order/search',
    method: 'get',
    params
  })
}

export function findOrderDetailListById (orderId) {
  return http({
    url: `/food-supplier/order/detail-list/${orderId}`,
    method: 'get'
  })
}
