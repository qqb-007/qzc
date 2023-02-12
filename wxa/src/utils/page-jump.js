module.exports = function (pageJump) {
  var page = ''
  switch (pageJump.page) {
    case 'COUPON_ITEM':
      page = 'coupon-item'
      break
    case 'COUPON_SEARCH':
      page = 'search-coupon'
      break
    case 'SHOPCAT':
      wx.switchTab({
        url: '/pages/category'
      })
      return
  }
  wx.navigateTo({
    url: page + '?' + pageJump.params
  })
}
