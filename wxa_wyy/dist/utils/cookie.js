"use strict";var cookieMap={},storage=require("./xstorage.js"),storageKey=require("./storage-keys.js");cookieMap=wx.getStorageSync(storageKey.COOKIE),module.exports={clear:function(){cookieMap={},storage.setItem(storageKey.COOKIE,cookieMap)},setCookies:function(e){e.split(",").forEach(function(e){var o=e.split(";")[0].split("=");o.length<2||(cookieMap[o[0]]=o[1])}),wx.setStorageSync(storageKey.COOKIE,cookieMap)},cookie:function(){var e=[];for(var o in cookieMap)e.push(o+"="+cookieMap[o]);return e.join(";")}};