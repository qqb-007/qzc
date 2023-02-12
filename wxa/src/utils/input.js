var input = function (e) {
  var dataset = e.currentTarget.dataset
  let segs = dataset.key.split(".")
  var currentPath = this.data
  let val = e.detail.detail ? e.detail.detail.value : e.detail.value
  for(var i = 0; i < segs.length; i++) {
    if(i == segs.length - 1) {
      currentPath[segs[i]] = val
    } else {
      currentPath = this.data[segs[i]] = {}
    }
  }
}

module.exports = input
