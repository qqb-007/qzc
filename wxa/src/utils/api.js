var client = require('./request')
var login = require('./login')
let Deferred = require('./deferred')
var API_FUNCTION = {
  page: {
    index: login.decorate(client.createFunction('/page/index', 'GET')),
    my: login.decorate(client.createFunction('/page/my', 'GET')),
    storeUserFoodList: login.decorate(client.createFunction('/page/store-user-food-list', 'GET')),
    withdraw: login.decorate(client.createFunction('/page/withdraw', 'GET')),
    withdrawInfo: login.decorate(client.createFunction('/page/withdraw/wallet-flow-detail/{walletFlowDetailId}', 'GET')),
    storeFoodApplicationApply: login.decorate(client.createFunction('/page/store-food-application/apply', 'GET')),
    settlementSheetList: login.decorate(client.createFunction('/page/settlement-sheet-list', 'GET')),
    supplierFoodList: login.decorate(client.createFunction('/page/supplier-food-list', 'GET'))
  },
  stat: {
    storeUserBizData: login.decorate(client.createFunction('/stat/store-user-biz-data', 'GET'))
  },
  food: {
    search: login.decorate(client.createFunction('/food/search', 'GET'))
  },
  foodCategory: {
    listByParentName: client.createFunction('/food-category/list-by-parent-name/{parentName}', 'GET')
  },
  login: {
    password: client.createFunction('/login', 'POST'),
    check: client.createFunction('/login/check', 'GET'),
    phoneLogin: client.createFunction('/login-phone', 'GET'),
  },
  logout: client.createFunction('/logout', 'GET'),
  quote: {
    foodSearch: login.decorate(client.createFunction('/quote/food/search', 'GET')),
    food: login.decorate(client.createFunction('/quote/food', 'POST')),
  },
  foodQuoteSheet: {
    search: login.decorate(client.createFunction('/food-quote-sheet/search', 'GET'))
  },
  foodQuoteSheetDetail: {
    list: client.createFunction('/food-quote-sheet-detail/list/{foodQuoteSheetId}', 'GET')
  },
  order: {
    search: login.decorate(client.createFunction('/order/search', 'GET')),
    detailList: login.decorate(client.createFunction('/order/detail-list/{id}', 'GET')),
    groupSupplierDetailList: login.decorate(client.createFunction('/order/group-supplier-detail-list/{id}', 'GET')),
    receipt: login.decorate(client.createFunction('/order/receipt/{id}', 'POST')),
    cancelDelivery: login.decorate(client.createFunction('/order/cancelDelivery/{id}', 'POST')),
    sendDelivery: login.decorate(client.createFunction('/order/sendDelivery/{id}', 'POST')),
    print: login.decorate(client.createFunction('/order/print/{id}', 'POST'))
  },
  storeUserFood: {
    soldOut: login.decorate(client.createFunction('/store-user-food/sold-out/{foodId}', 'PUT')),
    sale: login.decorate(client.createFunction('/store-user-food/sale/{foodId}', 'PUT')),
    changeAlterQuotePrice: login.decorate(client.createFunction('/store-user-food/alter-quote-price/{id}', 'PUT')),
    search: login.decorate(client.createFunction('/store-user-food/search', 'GET')),
    save: login.decorate(client.createFunction('/store-user-food', 'POST')),
    sku: client.createFunction('/store-user-food/sku/{id}', 'GET'),
    specialSku: client.createFunction('/store-user-food/special-sku/{id}', 'PUT')
  },
  withdraw: login.decorate(client.createFunction('/withdraw', 'POST')),
  walletFlowDetail: {
    search: login.decorate(client.createFunction('/wallet-flow-detail/search', 'GET'))
  },
  settlementSheet: {
    search: login.decorate(client.createFunction('/settlement-sheet/search', 'GET'))
  },
  printer: {
    bind: login.decorate(client.createFunction('/printer', 'POST')),
    test: login.decorate(client.createFunction('/printer/test/{sn}', 'POST')),
    bindYl: login.decorate(client.createFunction('/printer/bindYlPrinter', 'POST'))
  },
  aliyun: {
    sts: {
      acs: login.decorate(client.createFunction('/aliyun/sts/acs', 'GET'))
    }
  },
  storeFoodApplication: {
    apply: login.decorate(client.createFunction('/store-food-application', 'POST'))
  },
  sms: {
    phoneLogin: client.createFunction('/sms/phone-login/{phone}', 'POST'),
  },
  foodSupplier: {
    listOfCurrent: login.decorate(client.createFunction('/food-supplier/list-of-current', 'GET'))
  }
}

function loginRefreshDecorate(fn, predicate) {
  return function () {
    let deferred = new Deferred()
    Deferred.when(fn()).done(function () {
      let args = Array.prototype.slice.call(arguments)
      if (predicate && predicate.apply(null, args)) {
        API_FUNCTION.login.refresh().done(() => {
          deferred.resolve.apply(deferred, args)
        }).fail(() => {
          deferred.reject.apply(deferred, Array.prototype.slice.call(arguments))
        })
      } else {
        deferred.resolve.apply(deferred, args)
      }
    }).fail(function () {
      deferred.reject.apply(deferred, Array.prototype.slice.call(arguments))
    })
    return deferred
  }
}

module.exports = API_FUNCTION
