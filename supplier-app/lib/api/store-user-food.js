import http from '../http'

export function search (params) {
  return http({
    url: '/food-supplier/store-user-food/search',
    method: 'get',
    params
  })
}

export function save (data) {
  return http({
    url: '/food-supplier/store-user-food',
    method: 'post',
    data
  })
}

export function applyFood (data) {
  return http({
    url: '/food-supplier/store-food-application',
    method: 'post',
    data
  })
}

export function changeSupplierAlterQuotePrice (id, alterQuotePrice) {
  return http({
    url: `/food-supplier/store-user-food/supplier-alter-quote-price/${id}`,
    method: 'put',
    data: {
      alterQuotePrice
    }
  })
}

export function soldOutByFoodId (foodId) {
  return http({
    url: '/food-supplier/store-user-food/sold-out/' + foodId,
    method: 'put'
  })
}

export function saleByFoodId (foodId) {
  return http({
    url: '/food-supplier/store-user-food/sale/' + foodId,
    method: 'put'
  })
}

export function sku (id) {
  return http({
    url: `/store-user-food/sku/${id}`,
    method: 'get'
  })
}

export function saveSpecialSku (id, data) {
  return http({
    url: `/food-supplier/store-user-food/special-sku/${id}`,
    method: 'put',
    data
  })
}
