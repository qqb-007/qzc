<style lang="less">
  .page-head {
    /*margin-bottom: 20rpx;*/
    padding: 20rpx;
    font-size: 40rpx;
  }

  .container {
    padding: 20rpx;
  }

  .bottom {
    padding-top: 50rpx;
  }

  .cell-group {
    margin-top: 10px;
  }
</style>
<template>
  <view class="container">
    <view class="page-head">
      <text class="page-title text-title">个人中心</text>
    </view>

    <view class="cu-list menu" >
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">供应商</text>
        </view>
        <view class="action">
          <text class="">{{foodSupplier.name}}</text>
        </view>
      </view>
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">手机号</text>
        </view>
        <view class="action">
          <text class="">{{foodSupplier.phone | phoneProtect}}</text>
        </view>
      </view>
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">地址</text>
        </view>
        <view class="action" style="max-width: 60%">
          <text class="">{{foodSupplier.address}}</text>
        </view>
      </view>
    </view>

    <view class="cu-bar bg-white solid-bottom margin-top">
      <view class="action">
        <text class="cuIcon-title text-orange "></text> 王小菜门店信息
      </view>
    </view>
    <view class="cu-list menu" >
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">门店</text>
        </view>
        <view class="action">
          <text class="">{{foodSupplier.storeUser.name}}</text>
        </view>
      </view>
      <view class="cu-item" @click="tel(foodSupplier.storeUser.phone)">
        <view class="content">
          <text class="text-grey">手机号</text>
        </view>
        <view class="action">
          <text class="">{{foodSupplier.storeUser.phone | phoneProtect}}</text>
        </view>
      </view>
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">地址</text>
        </view>
        <view class="action" style="max-width: 60%">
          <text class="">{{foodSupplier.storeUser.address}}</text>
        </view>
      </view>
      <view class="cu-item" @click="tel(foodSupplier.storeUser.bizManager ? foodSupplier.storeUser.bizManager.phone : '')">
        <view class="content">
          <text class="text-grey">客服1</text>
          <view class="text-gray text-sm">
            <text class=""></text> 如遇问题，请联系我帮您解决问题</view>
        </view>
        <view class="action" style="max-width: 60%">
          <text class="">{{foodSupplier.storeUser.bizManager | bizManagerInfo }}</text>
        </view>
      </view>
      <view class="cu-item" @click="tel(foodSupplier.storeUser.bizManager2 ? foodSupplier.storeUser.bizManager2.phone : '')">
        <view class="content">
          <text class="text-grey">客服2</text>
          <view class="text-gray text-sm">
            <text class=""></text> 如遇问题，请联系我帮您解决问题</view>
        </view>
        <view class="action" style="max-width: 60%">
          <text class="" >{{foodSupplier.storeUser.bizManager2 | bizManagerInfo }}</text>
        </view>
      </view>
    </view>
    <view class="bottom flex flex-direction">
      <button class='cu-btn bg-default shadow lg' @click="logout()">退出登录</button>
    </view>
  </view>
</template>

<script>

import { my as PageMy } from '../lib/api/page'
export default {
  data () {
    return {
      foodSupplier: {}
    }
  },
  filters: {
    phoneProtect (phone) {
      if (!phone) {
        return ''
      }
      phone = phone.replace(/^(.{3})(.{4})(.+)$/, ($0, $1, $2, $3) => {
        return $1 + '****' + $3
      })
      return phone
    },
    bizManagerInfo (bizManager) {
      if (!bizManager) {
        return ''
      }
      return bizManager.name + ' ' + bizManager.phone
    }
  },
  watch: {},
  computed: {

  },
  created () {

  },
  onPullDownRefresh () {
    this.loadData().finally(() => {
      wx.stopPullDownRefresh()
    })
  },
  onLoad () {
    uni.startPullDownRefresh()
  },
  methods: {
    logout () {
      this.$store.dispatch('session/logout')
    },
    tel (phone) {
      if (!phone) {
        return
      }
      uni.makePhoneCall({
        phoneNumber: phone // 仅为示例，并非真实的电话号码
      })
    },
    loadData () {
      return PageMy().then(rs => {
        const data = rs.data
        this.foodSupplier = data.foodSupplier
      })
    }
  }
}
</script>
