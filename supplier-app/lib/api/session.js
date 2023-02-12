import http from '../http'

export function login (params) {
  return http({
    url: '/login-phone',
    method: 'get',
    params: {
      ...params,
      role: 'FOOD_SUPPLIER'
    }
  })
}

export function logout () {
  return http({
    url: '/logout',
    method: 'get'
  })
}

export function checkLogin () {
  return http({
    url: '/login/check',
    method: 'get'
  })
}
