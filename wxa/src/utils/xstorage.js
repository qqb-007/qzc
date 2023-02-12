let Deferred = require('./deferred')
module.exports = {
  setItem(key, value) {
    var deferred = new Deferred()
    value = JSON.stringify(value)
    wx.setStorage({
      key: key,
      data: value,
      success(rs) {
        deferred.resolve(rs)
      },
      fail(rs) {
        deferred.resolve(false)
      }
    })
    return deferred.promise()
  },
  getItem(key, defaultVal) {
    var deferred = new Deferred()
    wx.getStorage({
      key: key,
      success(rs) {
        try {
          deferred.resolve(JSON.parse(rs.data), rs)
        } catch (e) {
          deferred.resolve(null || defaultVal, rs)
        }
      },
      fail(rs) {
        deferred.resolve(null || defaultVal, rs)
      }
    })
    return deferred.promise()
  },
  removeItem(key) {
    var deferred = new Deferred()
    wx.removeStorage({
      key: key,
      success(rs) {
        deferred.resolve(rs)
      }
    })
    return deferred.promise()
  }
}
