import keys from './storage-keys'

export function getToken () {
  return uni.getStorageSync(keys.xAuthToken)
}

export function setToken (token) {
  uni.setStorageSync(keys.xAuthToken, token)
}

export function removeToken () {
  return uni.removeStorageSync(keys.xAuthToken)
}
