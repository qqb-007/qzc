"use strict";module.exports=function(a){var e="";switch(a.page){case"COUPON_ITEM":e="coupon-item";break;case"COUPON_SEARCH":e="search-coupon";break;case"SHOPCAT":return void wx.switchTab({url:"/pages/category"})}wx.navigateTo({url:e+"?"+a.params})};