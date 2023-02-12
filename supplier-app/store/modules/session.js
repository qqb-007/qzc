import { login, logout, checkLogin } from '../../lib/api/session'
import { getToken, setToken, removeToken } from '@/lib/auth-token'

const state = {
  name: '',
  avatar: ''
}

const mutations = { }

const actions = {
  // user login
  login ({ commit }, req) {
    const { phone, verifyCode } = req
    return new Promise((resolve, reject) => {
      login({ phone: phone.trim(), verifyCode }).then(response => {
        const { data } = response
        setToken(data.token)
        if (!data.success) {
          uni.showModal({
            title: '温馨提示',
            content: data.errMsg,
            success (res) {

            }
          })
        } else {
          uni.reLaunch({
            url: '/pages/index'
          })
        }
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout ({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        removeToken()
        uni.redirectTo({
          url: '/pages/login'
        })
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  checkLogin ({ commit, state }) {
    return new Promise((resolve, reject) => {
      checkLogin().then((res) => {
        const { data } = res
        if (data) {
          uni.reLaunch({
            url: '/pages/index'
          })
        }
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken ({ commit }) {
    return new Promise(resolve => {
      removeToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
