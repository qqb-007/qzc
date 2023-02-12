"use strict";function _interopRequireDefault(e){return e&&e.__esModule?e:{default:e}}function _classCallCheck(e,n){if(!(e instanceof n))throw new TypeError("Cannot call a class as a function")}function _possibleConstructorReturn(e,n){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!n||"object"!=typeof n&&"function"!=typeof n?e:n}function _inherits(e,n){if("function"!=typeof n&&null!==n)throw new TypeError("Super expression must either be null or a function, not "+typeof n);e.prototype=Object.create(n&&n.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),n&&(Object.setPrototypeOf?Object.setPrototypeOf(e,n):e.__proto__=n)}Object.defineProperty(exports,"__esModule",{value:!0});var _createClass=function(){function e(e,n){for(var t=0;t<n.length;t++){var o=n[t];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}return function(n,t,o){return t&&e(n.prototype,t),o&&e(n,o),n}}(),_wepy=require("./../npm/wepy/lib/wepy.js"),_wepy2=_interopRequireDefault(_wepy),_bind=require("./../mixins/bind.js"),_bind2=_interopRequireDefault(_bind),api=require("./../utils/api.js"),login=require("./../utils/login.js"),Index=function(e){function n(){var e,t,o,i;_classCallCheck(this,n);for(var r=arguments.length,a=Array(r),s=0;s<r;s++)a[s]=arguments[s];return t=o=_possibleConstructorReturn(this,(e=n.__proto__||Object.getPrototypeOf(n)).call.apply(e,[this].concat(a))),o.config={navigationBarTitleText:"我的",enablePullDownRefresh:!0,usingComponents:{"i-input":"../iview/input/index","i-button":"../iview/button/index","i-cell-group":"../iview/cell-group/index","i-cell":"../iview/cell/index"}},o.mixins=[_bind2.default],o.data={storeUser:{}},o.watch={},o.methods={open:function(e){wx.navigateTo({url:e})},logout:function(){login.invalidate().done(function(e){api.logout().done(function(e){wx.reLaunch({url:"login"})})})},tel:function(e){e&&wx.makePhoneCall({phoneNumber:e})},addPrinter:function(){var e=this;wx.showActionSheet({itemList:["扫描二维码添加","测试打印"],success:function(n){switch(n.tapIndex){case 0:e.scanBind();break;case 1:e.storeUser.feiePrinterSn?api.printer.test({sn:e.storeUser.feiePrinterSn}).done(function(e){e.data?wx.showToast({title:"测试打印成功",icon:"success",duration:1e3}):wx.showToast({title:"测试打印失败",icon:"none",duration:1e3})}):e.scanBind()}},fail:function(e){console.log(e.errMsg)}})}},o.events={},i=t,_possibleConstructorReturn(o,i)}return _inherits(n,e),_createClass(n,[{key:"loadData",value:function(){var e=this;return api.page.my().done(function(n){var t=n.data;e.storeUser=t.storeUser,e.$apply()})}},{key:"scanBind",value:function(){var e=this;wx.scanCode({scanType:["qrCode"],success:function(n){wx.showModal({title:"提示",content:"确定绑定该打印机吗？",success:function(t){if(t.confirm){var o=n.result.split(":");wx.showLoading(),api.printer.bind({sn:o[0],key:o[1]}).done(function(n){e.loadData(),wx.showToast({title:"打印机绑定成功",icon:"success",duration:1e3})}).always(function(){wx.hideLoading()})}}})}})}},{key:"onPullDownRefresh",value:function(){this.loadData().done(function(){wx.stopPullDownRefresh()})}},{key:"onLoad",value:function(){wx.startPullDownRefresh()}}]),n}(_wepy2.default.page);Page(require("./../npm/wepy/lib/wepy.js").default.$createPage(Index,"pages/my"));