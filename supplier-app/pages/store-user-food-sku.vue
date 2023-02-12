<style lang='css' src='../static/css/icon.css'></style>
<style lang='less' src='../static/css/color.less'></style>
<style lang='less' src='../static/css/text.less'></style>
<style lang='less' src='../static/css/utils.less'></style>
<style lang='less' src='../static/css/bg.less'></style>
<style lang='less' src='../static/css/style.less'></style>
<style lang="less">
  @import "../static/css/var";

  .container {
    padding: 10px;
    background-color: #efeff4;
    padding-top: 40px;
    .food-name {
      text-align: center;
      margin-bottom: 20px;
      font-size: 20px;
    }
  }

</style>
<template>
  <view class="container">
    <view class="food-name">{{foodName}}</view>
    <view class="cu-bar bg-white solid-bottom margin-top">
      <view class="action">
        <text class="cuIcon-title text-orange"></text> 美团规格(美团平台不指定规格，则发布所有规格)
      </view>
      <view class="action"></view>
    </view>
    <form>
      <checkbox-group class="block" @change="handleSpecialSkuChange">
        <view class="cu-form-group" v-for="foodSku in foodSkuList" v-bind:key="foodSku.spec">
          <view class="title">{{foodSku.spec}}</view>
          <checkbox :class="specialSkuIdList.indexOf(foodSku.skuId) !== -1 ? 'checked' : ''" :checked="specialSkuIdList.indexOf(foodSku.skuId) !== -1" :value="foodSku.skuId"></checkbox>
        </view>
      </checkbox-group>
    </form>
    <view class="cu-bar bg-white solid-bottom margin-top">
      <view class="action">
        <text class="cuIcon-title text-orange"></text> 饿百规格(只能选择一个规格进行发布)
      </view>
      <view class="action"></view>
    </view>
    <form>
      <radio-group class="block" @change="handleEleSkuChange">
        <view class="cu-form-group" v-for="foodSku in foodSkuList" v-bind:key="foodSku.spec">
          <view class="title">{{foodSku.spec}}</view>
          <radio :class="eleSkuId === foodSku.skuId ? 'checked' : ''" :checked="eleSkuId === foodSku.skuId" :value="foodSku.skuId"></radio>
        </view>
      </radio-group>
    </form>
    <view style="margin-top: 40px;">
      <button class='cu-btn bg-green shadow lg' style="width: 100%;" @click="save()" >发布规格</button>
    </view>
  </view>
</template>

<script>
import { sku, saveSpecialSku, saleByFoodId } from '../lib/api/store-user-food'

export default {
  components: {},

  watch: {
    specialSkuIdList (newVal) {
      this.specialSkuNameList = newVal.map(id => this.foodSkuMap[id].spec)
    },
    eleSkuId (id) {
      if (!id) {
        return
      }
      this.eleSkuName = this.foodSkuMap[id].spec
    }
  },

  data () {
    return {
      loading: false,
      specialSkuIdList: [],
      specialSkuNameList: [],
      eleSkuId: '',
      eleSkuName: '',
      foodSkuList: [],
      id: 0,
      foodId: 0,
      foodSkuMap: {},
      foodName: ''
    }
  },

  methods: {
    handleSpecialSkuChange ({ detail = {} }) {
      this.specialSkuIdList = detail.value
    },
    handleEleSkuChange ({ detail = {} }) {
      this.eleSkuId = detail.value
    },
    save () {
      const self = this
      wx.showModal({
        title: '提示',
        content: '确认发布所选规格吗？',
        success: (res) => {
          if (res.confirm) {
            saveSpecialSku(self.id, {
              specialSkuIdList: this.specialSkuIdList,
              eleSkuId: this.eleSkuId
            }).then(rs => {
              const data = rs.data
              if (data) {
                self.sale()
              }
            })
          } else if (res.cancel) {

          }
        }
      })
    },
    sale () {
      wx.showLoading()
      saleByFoodId(this.foodId).then(rs => {
        const data = rs.data
        if (data) {
          uni.showModal({
            title: '温馨提示',
            icon: 'success',
            content: '规格发布成功',
            success () {
              uni.navigateBack()
            }
          })
        } else {
          uni.showModal({
            title: '温馨提示',
            icon: 'success',
            content: '规格设置成功，请等待客服审核！',
            success () {
              uni.navigateBack()
            }
          })
        }
      }).finally(function () {
        wx.hideLoading()
      })
    },
    loadData () {
      return sku(this.id).then(rs => {
        const data = rs.data
        this.foodId = data.foodId
        this.foodName = data.foodName
        this.specialSkuIdList = data.specialSkuIdList
        this.foodSkuList = data.foodSkuList
        this.foodSkuList.forEach(fs => {
          this.foodSkuMap[fs.skuId] = fs
        })
        this.eleSkuId = data.eleSkuId
      })
    }
  },

  onPullDownRefresh () {
    this.loadData()
  },
  onLoad (options) {
    this.id = +options.id
    this.loadData().finally(() => {
      this.loaded = true
    })
  }
}
</script>
