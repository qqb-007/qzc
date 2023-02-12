import http from '../http'

export function statFoodSupplierBizData (params) {
  return http({
    url: '/food-supplier/stat/food-supplier-biz-data',
    method: 'get',
    params
  })
}

export function statFoodSupplierFoodSales (params) {
  return http({
    url: '/food-supplier/stat/food-sales',
    method: 'get',
    params
  })
}
