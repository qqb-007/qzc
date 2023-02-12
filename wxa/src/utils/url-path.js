var mode = 'pro'
var assetsBase, apiBase
function setEnv (env) {
  switch (env) {
    case 'pro':
      apiBase = 'https://api.wangxiaocai.cn'
      break
    case 'dev':
      assetsBase = 'http://192.168.31.174/mz-assets'
      apiBase = 'http://192.168.31.174:8080/'
      break
    case 'local':
      assetsBase = 'http://localhost/mz-assets'
      apiBase = 'http://localhost:8080/'
      break
    case 'phone':
      assetsBase = 'http://192.168.0.66/mz-assets'
      apiBase = 'http://192.168.0.66:8080/'
      break
    case 'wxc':
      assetsBase = 'http://192.168.124.37/mz-assets'
      apiBase = 'http://192.168.124.37:8080/'
      break
  }
}
setEnv(mode)

function wrapVersion (path, flag) {
  if (!flag) {
    return path
  }
  if (path.indexOf('?') !== -1) {
    path += '&_=' + new Date().getTime()
  } else {
    path += '?_=' + new Date().getTime()
  }
  return path
}

module.exports = {
  assets (path, version) {
    let base = assetsBase
    return wrapVersion(base + path, version)
  },
  api (path, version) {
    let base = apiBase
    return wrapVersion(base + path, version)
  }
}
