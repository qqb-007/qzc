module.exports = {
  hold: function (loginInfo) {
    wx.setStorageSync('loginInfo', loginInfo)
  },
  get: function () {
    return wx.getStorageSync('loginInfo') || {}
  }
}
