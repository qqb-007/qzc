module.exports = function (obj) {
  var tmp = []
  for (var key in obj) {
    var val = obj[key]
    if (Array.isArray(val)) {
      for (var i = 0; i < val.length; i++) {
        tmp.push(key + '[' + i + ']=' + val[i])
      }
    } else {
      tmp.push(key + '=' + encodeURIComponent(val))
    }
  }
  return tmp.join('&')
}
