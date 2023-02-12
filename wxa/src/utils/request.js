let extend = require('./extend')
let urlPath = require('./url-path')
let Deferred = require('./deferred')
let serialize = require('./serialize')
let login = require('./login')
var loginDoing = false
var loginOptions = []
let cookie = require('./cookie')
var API = {
  request: function (option) {
    option = extend({}, option || {})
    option.header = option.header || {}
    if (!option.header['Content-Type']) {
      option.header['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8'
    }
    option.header['Cookie'] = cookie.cookie()
    let deferred = new Deferred()
    deferred.fail(function (data, res) {
      if (res.errMsg) {
        wx.showModal({
          title: '信息提示',
          content: res.errMsg.split(' ')[1],
          success: function () {
          }
        })
      }
    })
    option.success = function (res) {
      var data = res.data
      if (res === undefined || data === undefined) {
        deferred.reject(data, res)
        return
      }
      if (data.success) {
        deferred.resolve(res.data, res)
      } else {
        if (data.statusCode === 403) {
          if (loginDoing) {
            loginOptions.push(option)
            return
          }
          loginOptions.push(option)
          loginDoing = true
          login.invalidate().done(() => {
            login.login(() => {
              loginDoing = false
              while (loginOptions.length) {
                API.request(loginOptions.pop()).done(function () {
                  deferred.resolve.apply(deferred, Array.prototype.slice.call(arguments))
                }).fail(rs => {
                  deferred.reject.apply(deferred, Array.prototype.slice.call(arguments))
                })
              }
            })
          })
        }
        if (data.level === 'SYSTEM') {
          wx.showModal({
            title: '信息提示',
            content: data.errMsg,
            success: function (res) {
            }
          })
          deferred.reject(res.data, res)
        }
      }
    }
    option.fail = function (res) {
      wx.showModal({
        title: '信息提示',
        content: res.errMsg.split(' ')[1],
        success: function (res) {
        }
      })
      deferred.reject(res.data, res)
    }
    option.complete = function (res) {
      if (!res.header) {
        return
      }
      let cookies = res.header['Set-Cookie']
      if (cookies) {
        cookie.setCookies(cookies)
      }
    }
    wx.request(option)
    return deferred
  },
  do_request: function (url, param, method, type) {
    if (typeof url === 'function') {
      var tmp = url(param)
      if (typeof tmp === 'string') {
        url = tmp
      } else {
        url = tmp[0]
        if (tmp.length > 1) {
          param = tmp[1]
        }
      }
    }
    url = urlPath.api(url)
    // modal.alert({message: url})
    // 处理url中的placeholder
    url = url.replace(/\{([^}]+)}/gi, function ($0, $1) {
      var val = param[$1]
      delete param[$1]
      return val
    })
    if (method.toLowerCase() === 'get') {
      if (url.indexOf('?') === -1) {
        url += '?_t=' + new Date().getTime()
      } else {
        url += '&_t=' + new Date().getTime()
      }
      url = url + '&' + (typeof param !== 'string' ? serialize(param) : (param === undefined ? '' : param))
      param = ''
    }
    return this.request({
      url: url,
      type: type,
      data: param,
      method: method
    })
  },
  createFunction: function (url, method) {
    var self = this
    return function (body, type, listener) {
      listener && listener.onRequest && listener.onRequest()
      return self.do_request(url, body, method || 'GET', type || 'json').done(rs => {
        listener && listener.onSuccess && listener.onSuccess(rs)
      }).fail((rs) => {
        listener && listener.onFail && listener.onFail(rs)
      }).always(rs => {
        listener && listener.onFinish && listener.onFinish(rs)
      })
    }
  },
  createUrl: function (url) {
    url = urlPath.api(url)
    return function (param) {
      return url.replace(/\{([^}]+)}/gi, function ($0, $1) {
        var val = (typeof param === 'string') ? param : param[$1]
        return val === undefined ? '' : val
      })
    }
  }
}

module.exports = API
