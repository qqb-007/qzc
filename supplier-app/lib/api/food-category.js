import http from '../http'

export function listWithFoodNum () {
  return http({
    url: '/food-category/list-with-food-num',
    method: 'get'
  })
}
