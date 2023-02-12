let storage = require('./xstorage')
let Deferred = require('./deferred')
let storageKeys = require('./storage-keys')
let loginCallbackFnQueue = []
let loginDoing = false
module.exports = {
  invalidate () {
    return storage.removeItem(storageKeys.LOGIN)
  },
  // force 强制登录
  login (fn) {
    loginCallbackFnQueue.push(fn)
    if (loginDoing) {
      return
    }
    loginDoing = true
    wx.navigateTo({
      url: 'login'
    })
  },
  check () {
    return storage.getItem(storageKeys.LOGIN)
  },
  success () {
    // loginCallbackFnQueue.forEach(c => {
    //   c()
    // })
    // loginCallbackFnQueue = []
    // loginDoing = false
    return storage.setItem(storageKeys.LOGIN, true)
  },
  decorate (fn) {
    let self = this
    return function () {
      let args = Array.prototype.slice.call(arguments)
      let deferred = new Deferred()

      function callFn () {
        Deferred.when(fn.apply(null, args)).done(function () {
          deferred.resolve.apply(deferred, Array.prototype.slice.call(arguments))
        }).fail(function () {
          deferred.reject.apply(deferred, Array.prototype.slice.call(arguments))
        })
      }
      self.check().done(flag => {
        if (flag) {
          callFn()
        } else {
          self.login(callFn)
        }
      })
      return deferred.promise()
    }
  }
}
