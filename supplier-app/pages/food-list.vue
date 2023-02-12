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
  }

  .food {
    background-color: #ffffff;
    margin-bottom: 5px;
    padding: 10px;
    position: relative;
    &-info {
      display: flex;
      flex-direction: row;
      align-items: stretch;
    }
    &-checkbox {
      width: 20px;
      height: 20px;
      border-radius: 20px;
      background-color: #e9e9e9;
      display: flex;
      align-items: center;
      justify-content: center;
      color: @color-success;
      &-checked {
        background-color: @color-success;
        color: #ffffff;
      }
    }
    &-pic {
      border-radius: 5px;
      width: 60px;
      height: 60px;
    }
    &-detail {
      flex: 1;
      padding-left: 10px;
      display: flex;
      justify-content: space-between;
      flex-direction: column;
    }
    &-title {
      font-size: 16px;
    }
    &-price {
      font-size: 14px;
      color: @color-muted;
      &-val {
        font-size: 14px;
        color: @color-success;
      }
    }
    &-footer {
      border-top: 1rpx dashed #ddd;
      margin-top: 10px;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    &-sale {
      font-size: 14px;
      color: @color-title;
      &-btn {
        /*border: 1rpx solid #ddd;*/
        font-size: 12px;
        padding: 3px 8px;
        border-radius: 2px;
        background-color: @color-info;
        color: #ffffff;
        &-on {
          background-color: @color-success;
        }
      }
    }
  }

  .search-input {
    border-radius: 30px;
    height: 30px;
    line-height: 30px;
    border: 1rpx solid #ddd;
    padding: 0 20px;
    font-size: 14px;
    background-color: #ffffff;
    flex: 1;
    &-wrapper {
      display: flex;
      flex-direction: row;
      align-items: center;
      background-color: #efeff4;
      position: fixed;
      top: 0;
      right: 0;
      left: 0;
      padding: 5px 10px;
      z-index: 1000;
    }
    &-btn {
      border-radius: 30px;
      height: 30px;
      line-height: 30px;
      width: 40px;
      margin-left: 5px;
      font-size: 14px;
      color: #ffffff;
      text-align: center;
      background-color: @color-info;
    }
  }

  .category-view {
    position: fixed;
    top: 40px;
    width: 100px;
    background-color: #fff;
    left: 0;
    z-index: 10;
    bottom: 0;
  }

  .category {
    font-size: 14px;
    height: 34px;
    line-height: 34px;
    text-align: center;
    color: @color-title;
    &-selected {
      background-color: #efeff4;
      font-weight: bold;
    }
  }

  .food-list {
    padding-left: 95px;
  }

  .empty-tip {
    display: flex;
    flex-direction: row;
    justify-content: center;
    padding-top: 30px;
    padding-bottom: 30px;
    &-btn {
      font-size: 14px;
    }
  }

</style>
<template>
  <view class="container">
    <view class="search-input-wrapper">
      <input placeholder="输入商品名进行筛选" class="search-input" @input="searchInputChange"/>
    </view>
    <scroll-view
      scroll-y
      class="category-view">
      <view class="category"
            :class="{'category-selected' : searchParam.categoryName === '' }"
            @click="selectCategory('')" v-bind:key="''">所有分类</view>
      <view v-for="category in foodCategoryList"
            class="category"
            :class="[searchParam.categoryName === category.name && 'category-selected']"
            @click="selectCategory(category.name)" v-bind:key="category.name">{{category.name}}</view>
    </scroll-view>
    <view class="food-list">
      <template v-for="(food, idx) in foodList">
        <view class="food" v-bind:key="food.id">
          <view class="food-info">
            <image :src="food.picture" @click="preview(food.picture)" class="food-pic" mode="aspectFill"></image>
            <view class="food-detail">
              <view class="food-title">{{food.name}}</view>
              <view class="flex-row space-between align-flex-end">
                <view class="food-price">售价: ¥<text class="food-price-val">{{food.price}}</text>元/{{food.unit}}</view>
              </view>
            </view>
          </view>
          <view class="food-footer">
            <uni-number-box :value="food.priceShow" min="0" max="100000" :state-value="idx" step="0.1" @change="priceChange"/>
            <view class="food-sale-btn" :class="[food.added && 'food-sale-btn-on']" @click="add(idx)">{{food.added ? '已添加' : '添加'}}</view>
          </view>
        </view>
      </template>
      <view v-if="!hasNext" class="empty-tip">
        <button class='cu-btn bg-warning shadow lg' @click="addNew()">未找到商品，手动添加</button>
      </view>
    </view>
    <view class="scrolltop-btn" @tap="scrollTop">
      <text class="iconfont icon-fold"></text>
    </view>
  </view>
</template>

<script>
import { search as FoodSearch } from '../lib/api/food'
import { save as StoreUserFoodSave } from '../lib/api/store-user-food'
import uniNumberBox from '@/components/uni-number-box/uni-number-box.vue'
import { listWithFoodNum } from '../lib/api/food-category'
const DEFAULT_SEARCH_PARAM = {
  page: 1,
  categoryName: ''
}

export default {
  components: {
    uniNumberBox
  },

  data () {
    return {
      searchParam: Object.assign({}, DEFAULT_SEARCH_PARAM),
      loading: false,
      hasNext: true,
      foodList: [],
      foodCategoryList: [],
      keyword: ''
    }
  },

  methods: {
    priceChange (val, idx) {
      const food = this.foodList[idx]
      food.price = parseFloat(val)
      food.priceShow = food.price.toFixed(1)
    },
    addNew () {
      wx.navigateTo({
        url: '/pages/food-apply'
      })
    },
    scrollTop () {
      wx.pageScrollTo({
        scrollTop: 0,
        duration: 300
      })
    },
    preview (url) {
      wx.previewImage({
        current: '', // 当前显示图片的http链接
        urls: [url] // 需要预览的图片http链接列表
      })
    },
    searchInputChange (e) {
      DEFAULT_SEARCH_PARAM.name = e.detail.value
      this.resetSearchParam()
      this.loadingSearch()
    },
    selectCategory (category) {
      DEFAULT_SEARCH_PARAM.categoryName = category
      this.resetSearchParam()
      this.loadingSearch()
    },
    add (idx) {
      const food = this.foodList[idx]
      wx.showLoading()
      StoreUserFoodSave({ foodId: food.id, quotePrice: food.price, sale: false }).then(rs => {
        console.info(rs)
        if (rs.success) {
          food.added = true
          this.$set(this.foodList, idx, food)
          uni.showToast({
            title: '添加成功',
            icon: 'none'
          })
        } else {
          uni.showModal({
            title: '温馨提示',
            content: rs.errMsg
          })
        }
      }).finally(() => {
        wx.hideLoading()
      })
    },
    resetSearchParam () {
      Object.assign(this.searchParam, DEFAULT_SEARCH_PARAM)
      this.hasNext = true
    },
    onReachBottom () {
      if (this.hasNext) {
        this.searchParam.page++
        this.search(true)
      }
    },
    onPullDownRefresh () {
      this.resetSearchParam()
      this.search().finally(() => {
        wx.stopPullDownRefresh()
      })
    },
    loadingSearch (flag) {
      if (this.searchTimeout) {
        clearTimeout(this.searchTimeout)
      }
      this.searchTimeout = setTimeout(() => {
        wx.showLoading()
        this.search(flag).finally(() => {
          wx.hideLoading()
        })
      }, 500)
    },
    search (append, callback) {
      return FoodSearch(this.searchParam).then((rs) => {
        rs.data.results.forEach(food => {
          food.priceShow = food.price.toFixed(1)
        })
        if (append) {
          this.foodList = this.foodList.concat(rs.data.results)
        } else {
          this.foodList = rs.data.results
        }
        const paging = rs.data
        this.hasNext = paging.hasNext
      }).finally(() => {
        callback && callback()
      })
    },
    loadData () {
      return listWithFoodNum().then(rs => {
        this.foodCategoryList = rs.data
      })
    }
  },
  onLoad () {
    this.loadData().then(this.search()).then(() => {
      this.loaded = true
    })
  }
}
</script>
