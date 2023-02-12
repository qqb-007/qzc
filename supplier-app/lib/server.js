const ENV = 'prod'

const SERVER_MAP = {
  supplierapi: {
    prod: 'https://api.wangxiaocai.cn',
    dev: 'https://merchantapi.wangxiaocai.cn',
    local: 'http://192.168.31.174:8080',
    wxc: 'http://192.168.124.37:8080'
  }
}

const PROTOCOL_MAP = {}

for (const key in SERVER_MAP) {
  const urlMap = SERVER_MAP[key]
  PROTOCOL_MAP[key + '://'] = urlMap[ENV]
}

module.exports = {
  url (url) {
    if (url.startsWith('/')) {
      url = 'supplierapi:/' + url
    }
    /**
     * 处理protocol
     * */
    const protocolIdx = url.indexOf('://') + 3
    const protocol = url.substring(0, protocolIdx)
    if (PROTOCOL_MAP[protocol]) {
      url = PROTOCOL_MAP[protocol] + '/' + url.substring(protocolIdx)
    }
    return url
  }
}
