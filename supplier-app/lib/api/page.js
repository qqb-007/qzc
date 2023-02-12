import http from '../http'

export function foodApply (params) {
  return http({
    url: '/food-supplier/page/store-food-application/apply',
    method: 'get',
    params
  })
}

export function storeUserFoodList () {
  return http({
    url: '/food-supplier/page/store-user-food-list',
    method: 'get'
  })
}

export function my () {
  return http({
    url: '/food-supplier/page/my',
    method: 'get'
  })
}

export function index () {
  return http({
    url: '/food-supplier/page/index',
    method: 'get'
  })
}
