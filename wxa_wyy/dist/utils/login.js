"use strict";var storage=require("./xstorage.js"),Deferred=require("./deferred.js"),storageKeys=require("./storage-keys.js"),loginCallbackFnQueue=[],loginDoing=!1;module.exports={invalidate:function(){return storage.removeItem(storageKeys.LOGIN)},login:function(e){loginCallbackFnQueue.push(e),loginDoing||(loginDoing=!0,wx.navigateTo({url:"login"}))},check:function(){return storage.getItem(storageKeys.LOGIN)},success:function(){return storage.setItem(storageKeys.LOGIN,!0)},decorate:function(e){var r=this;return function(){function o(){Deferred.when(e.apply(null,n)).done(function(){t.resolve.apply(t,Array.prototype.slice.call(arguments))}).fail(function(){t.reject.apply(t,Array.prototype.slice.call(arguments))})}var n=Array.prototype.slice.call(arguments),t=new Deferred;return r.check().done(function(e){e?o():r.login(o)}),t.promise()}}};