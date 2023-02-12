let cookieMap = {}
let storage = require('./xstorage')
let storageKey = require('./storage-keys')
cookieMap = wx.getStorageSync(storageKey.COOKIE)

module.exports = {
  clear() {
    cookieMap = {}
    storage.setItem(storageKey.COOKIE, cookieMap)
  },
  setCookies(cookies) {
    cookies.split(',').forEach(str => {
      let tmps = str.split(';')[0].split('=')
      if (tmps.length < 2) {
        return
      }
      cookieMap[tmps[0]] = tmps[1]
    })

    wx.setStorageSync(storageKey.COOKIE, cookieMap)
  },

  cookie() {
    let list = []
    for (var key in cookieMap) {
      list.push(key + '=' + cookieMap[key])
    }
    return list.join(';')
  }
}
