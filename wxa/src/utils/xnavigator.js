let urlPath = require('./url-path')
let Deferred = require('./deferred')
module.exports = {
  push (options, callback) {
    if (typeof options === 'string') {
      options = {
        url: options
      }
    }
    let deferred = new Deferred()
    urlPath.page(options.url, (localJs, path) => {
      options.url = platform.isWeb() ? path.replace('.js', '.html') : localJs
      // console.info(path.split('?')[0])
      // router.push({path: '/' + path.split('?')[0]})
      if (platform.isWeb()) {
        window.parent.location.href = options.url
        callback && callback('WX_SUCCESS')
        return
      }
      navigator.push(options, (rs) => {
        callback && callback(rs)
        if (rs !== 'WX_SUCCESS') {
          // modal.alert({message: JSON.stringify(options)})
        }
        deferred.resolve(rs)
      })
    })
    return deferred.promise()
  },
  pop (options, callback) {
    let deferred = new Deferred()
    navigator.pop(options || {}, (rs) => {
      callback && callback(rs)
      deferred.resolve(rs)
    })
    return deferred.promise()
  }
}
