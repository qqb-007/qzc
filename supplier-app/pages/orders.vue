<style lang="less">
  @import "../static/css/var";

  .container {
    padding: 10px;
    background-color: #efeff4;
    font-size: 16px;
    padding-top: 30px;
  }

  .order {
    border: 1rpx solid #ddd;
    margin-bottom: 10px;
    overflow: hidden;
    background-color: #ffffff;
    &-head {
      font-size: 14px;
      background-color: #f9f9f9;
      padding: 8px 0;
      color: #fff;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    }
    &-status {
      font-size: 14px;
      color: @color-title;
      margin-right: 10px;
    }
    &-id {
      height: 24px;
      padding: 0 10px 0 6px;
      line-height: 24px;
      font-size: 16px;
      border-top-right-radius: 24px;
      border-bottom-right-radius: 24px;
      background-color: @color-success;
    }
    &-create {
      font-size: 12px;
    }
    &-body {
      padding: 10px;
    }
    &-detail {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      font-size: 16px;
      padding: 2px 4px;
      border-bottom: 1rpx dashed #cecece;
      &:last-child {
        border-bottom: none;
      }
      &-list {
        background-color: #fefcec;
      }
    }
    &-divide {
      border-top: 1rpx dashed #ddd;
      margin: 10px 0;
    }
    &-footer {
      font-size: 12px;
      color: @color-muted;
      position: relative;
    }
    &-info {
      font-size: 14px;
      color: @color-muted;
      &-text {
        color: @color-title;
        font-size: 16px;
        margin-left: 10px;
      }
    }
    &-total {
      font-size: 18px;
      font-weight: bold;
      color: @color-primary;
    }

    &-refund {
      font-size: 18px;
      font-weight: bold;
      color: @color-danger;
    }

    &-income {
      font-size: 18px;
      font-weight: bold;
      color: @color-success;
    }

    &-fee {
      text-align: right;
    }
    &-toggle {
      color: @color-success;
      font-size: 14px;
    }
    &-food {
      &-pic {
        border-radius: 3px;
        width: 30px;
        height: 30px;
        margin-right: 2px;
      }
      &-name {
        font-size: 14px;
        /*font-weight: bold;*/
        flex: 2;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      &-quantity {
        font-size: 14px;
        font-weight: bold;
        flex: 1;
        &-label {
          font-size: 12px;
          font-weight: normal;
        }
      }
      &-spec {
        font-size: 12px;
        margin-top: 6rpx;
      }
      &-total {
        font-size: 14px;
        /*font-weight: bold;*/
        flex: 1;
        text-align: right;
      }
    }
  }

  .order-panel {
    &-head {
      flex-direction: row;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    &-title {
      font-weight: bold;
      color: @color-title;
      font-size: 20px;
      &-tip {
        font-size: 12px;
        color: @color-muted;
        font-weight: normal;
        margin-left: 20px;
      }
    }
  }

  .copy-btn {
    color: @color-title;
  }

  .search-bar {
    position: fixed;
    z-index: 100;
    top: 0;
    left: 0;
    right: 0;
    height: 30px;
    background-color: @color-primary;
    padding: 5px 10px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
  }

  .date-show {
    color: #ffffff;
    font-size: 14px;
    &-icon {
      font-size: 12px;
    }
  }

  .search-input {
    border-radius: 2px;
    height: 30px;
    line-height: 30px;
    border: 1rpx solid #ddd;
    padding: 0 10px;
    font-size: 14px;
    background-color: #fff;
    flex: 1;
    /*margin: 0 10px;*/
    color: @color-primary;
    margin-bottom: 10px;
  }

  .status-select {
    color: #ffffff;
    font-size: 14px;
    &-icon {
      font-size: 12px;
    }
  }

  .print-btn {
    position: absolute;
    right: 0px;
    top: 0px;
    text-align: center;
    &-icon {
      font-size: 24px;
    }
    &-txt {
      font-size: 12px;
    }
  }
</style>
<template>
  <view class="container">
    <view v-if="empty" class="page-notice">
      <view class="page-notice-info">暂无订单</view>
    </view>
    <view class="search-bar">
      <picker @change="dateChange" mode="date" :value="search.date">
        <view class="date-show">{{dateShow}}
          <text class="iconfont date-show-icon icon-unfold"></text>
        </view>
      </picker>
      <picker @change="platChange" mode="selector" :value="platListIndex" :range="platList">
        <view class="status-select">{{platShow}}
          <text class="iconfont status-select-icon icon-unfold"></text>
        </view>
      </picker>
      <picker @change="statusChange" mode="selector" :value="statusListIndex" :range="statusList">
        <view class="status-select">{{statusShow}}
          <text class="iconfont status-select-icon icon-unfold"></text>
        </view>
      </picker>
    </view>
    <view>
      <uni-search-bar @input="searchOrderId" placeholder="输入平台流水号进行筛选"></uni-search-bar>
    </view>
    <template v-for="(order, idx) in orderList">
      <view class="order" v-bind:key="order.id">
        <view class="order-head flex-row space-between">
          <view class="order-id">#{{order.daySeq}}<text>{{order.platTitle}}</text></view>
          <view class="order-status">{{order.statusTitle}}</view>
        </view>
        <view class="order-body">
          <view class="order-panel">
            <view class="order-panel-head" @click="showDetail(idx)">
              <view class="order-panel-title">商品</view>
              <view class="order-toggle">展开
                <text :class="['iconfont', order.detailShown ? 'icon-fold' : 'icon-unfold']"></text>
              </view>
            </view>
            <view class="order-detail-list" v-if="order.detailShown && order.orderDetailList">
              <view class="order-detail" v-for="detail in order.orderDetailList" @click="preview(detail.foodPicture)" v-bind:key="detail.id">
                <image :src="detail.foodPicture + '?x-oss-process=style/xs'" class="order-food-pic" mode="aspectFill"></image>
                <view class="order-food-name">{{detail.foodName}}({{detail.spec}})</view>
                <view>
                  <view class="order-food-quantity">×{{detail.quantity}}</view>
                  <view class="order-food-spec">¥{{detail.supplierQuotePrice}}</view>
                </view>
                <view class="order-food-quantity">
                  <template v-if="detail.refundQuantity">
                    <text class="order-food-quantity-label">退</text>
                    <text>×{{detail.refundQuantity}}</text>
                  </template>
                </view>
                <view class="order-food-total">¥{{detail.supplierIncome.toFixed(1)}}</view>
              </view>
            </view>
          </view>
          <view class="order-divide"></view>
          <view class="order-panel">
            <view class="order-panel-head">
              <view class="order-panel-title">总计</view>
              <view class="order-fee">
                <view class="order-income">¥{{order.remainMoneyForSupplier.toFixed(1)}}</view>
              </view>
            </view>
          </view>
          <view class="order-divide"></view>
          <view class="order-footer">
            <view>下单：{{order.createTime}}</view>
            <view>单号：{{order.platOrderId}}
              <text class="copy-btn" @click="copyClipboard(order.platOrderId)">复制</text>
            </view>
          </view>
        </view>
      </view>
    </template>
    <i-divider content="没有更多订单了" v-if="!hasNext && !empty"></i-divider>
  </view>
</template>
<script>
import { search, findOrderDetailListById } from '../lib/api/order'
import uniSearchBar from '@/components/uni-search-bar/uni-search-bar.vue'
const color = require('../lib/color')
const DEFAULT_SEARCH_PARAM = {
  platOrderId: '',
  daySeq: '',
  statusType: '',
  plat: '',
  date: ''
}

function calDetailTotalFee (detail) {
  return ((detail.quantity - (detail.refundQuantity || 0)) * (detail.supplierQuotePrice || 0))
}
export default {
  components: {
    uniSearchBar
  },
  data () {
    return {
      searchParam: Object.assign({}, DEFAULT_SEARCH_PARAM),
      loading: false,
      hasNext: true,
      empty: false,
      orderList: [],
      platOrderId: '',
      statusList: ['全部订单', '进行中', '已完成', '已取消'],
      statusType: ['ALL', 'PROCESSING', 'FINISHED', 'CANCELED'],
      statusListIndex: 0,
      platList: ['全部平台', '美团', '饿了么'],
      platType: ['', 'MEITUAN', 'ELE'],
      platListIndex: 0,
      page: 1
    }
  },
  filters: {
    detailTotalFee (detail) {
      return calDetailTotalFee(detail).toFixed(1)
    }
  },
  watch: {
    searchParam: {
      deep: true,
      handler () {
        this.page = 1
        this.loadingSearch()
      }
    },
    page () {
      this.search(true)
    }
  },
  computed: {
    statusShow () {
      return this.statusList[this.statusListIndex]
    },
    platShow () {
      return this.platList[this.platListIndex]
    },
    dateShow () {
      let date
      if (!this.searchParam.date) {
        date = new Date()
      } else {
        date = new Date(Date.parse(this.searchParam.date))
      }
      return (date.getMonth() + 1) + '.' + date.getDate()
    }
  },
  created () {
    this.search().finally(() => {
      this.loaded = true
    })
  },
  onPullDownRefresh () {
    this.page = 1
    this.search().finally(() => {
      uni.stopPullDownRefresh()
    })
  },
  onReachBottom () {
    if (this.hasNext) {
      this.page++
      this.search(true)
    }
  },
  onLoad () {

  },
  methods: {
    searchOrderId ({ value }) {
      this.page = 1
      this.searchParam.daySeq = value
    },
    tellPhone (value) {
      uni.makePhoneCall({
        phoneNumber: value + '' // 仅为示例，并非真实的电话号码
      })
    },
    showDetail (idx) {
      const order = this.orderList[idx]
      order.detailShown = !order.detailShown
      if (order.orderDetailList) {
        this.$set(this.orderList, idx, order)
        return
      }
      this.loadDetailList(order).then(() => {
        this.$set(this.orderList, idx, order)
      })
    },
    copyClipboard (str) {
      uni.setClipboardData({
        data: str + '',
        success () {
          uni.showToast({
            title: '复制成功',
            icon: 'success',
            duration: 500
          })
        }
      })
    },
    preview (url) {
      uni.previewImage({
        current: '', // 当前显示图片的http链接
        urls: [url] // 需要预览的图片http链接列表
      })
    },
    statusChange ({ detail }) {
      this.statusListIndex = detail.value
      this.searchParam.statusType = this.statusType[this.statusListIndex]
    },
    platChange ({ detail }) {
      this.platListIndex = detail.value
      this.searchParam.plat = this.platType[this.platListIndex]
    },
    dateChange ({ detail }) {
      this.searchParam.date = detail.value
    },
    resetSearchParam () {
      Object.assign(this.searchParam, DEFAULT_SEARCH_PARAM)
      this.hasNext = true
    },

    loadingSearch () {
      if (this.searchTimeout) {
        clearTimeout(this.searchTimeout)
      }
      this.searchTimeout = setTimeout(() => {
        uni.showLoading()
        this.search().finally(() => {
          uni.hideLoading()
        })
      }, 500)
    },

    loadDetailList (order) {
      uni.showLoading()
      return findOrderDetailListById(order.id).then(rs => {
        order.orderDetailList = rs.data
      }).finally(() => {
        uni.hideLoading()
      })
    },
    search (append, callback) {
      uni.showLoading({
        title: '加载中'
      })
      return search({
        ...this.searchParam,
        page: this.page
      }).then((rs) => {
        // this.handleOrderList(rs.data.results)
        if (append) {
          this.orderList = this.orderList.concat(rs.data.results)
        } else {
          this.orderList = rs.data.results
        }
        const paging = rs.data
        this.hasNext = paging.hasNext
        this.empty = this.page === 1 && this.orderList.length === 0
      }).finally(() => {
        uni.hideLoading()
        callback && callback()
      })
    },

    getStatusStyle (status) {
      switch (status) {
        case 'PAID':
          return color.warning
        case 'VERIFY_SUCCESS':
          return color.success
        case 'VERIFY_FAIL':
          return color.danger
      }
    },

    getPublishStatusStyle (status) {
      switch (status) {
        case 'WAIT':
          return 'yellow'
        case 'WAIT_MERCHANT_CONFIRM':
          return 'green'
        case 'MERCHANT_CONFIRMED':
          return 'red'
        case 'CANCELED':
          return 'red'
        case 'SHIPPING':
          return 'red'
      }
    }
  }
}
</script>
