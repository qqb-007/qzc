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
			margin-top: 10rpx;
			margin-bottom: 10rpx;
		}
		&-price {
			font-size: 14px;
			color: @color-muted;
			&-val {
				font-size: 16px;
				color: @color-success;
				font-weight: 500;
			}
		}
		&-footer {
			/*border-top: 1rpx dashed #ddd;*/
			margin-top: 10px;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}
		&-sku-btn {
			font-size: 12px;
			padding: 3px 8px;
			border-radius: 2px;
			background-color: @color-info;
			color: #ffffff;
		}
		&-publish-sku-btn {
			font-size: 12px;
			padding: 3px 8px;
			border-radius: 2px;
			background-color: @color-warning;
			color: #ffffff;
		}
		&-sale {
			font-size: 14px;
			color: @color-title;
			&-btn {
				/*border: 1rpx solid #ddd;*/
				font-size: 12px;
				padding: 3px 8px;
				border-radius: 2px;
				background-color: @color-danger;
				color: #ffffff;
			}
			&-btn-on {
				/*border: 1rpx solid #ddd;*/
				font-size: 12px;
				padding: 3px 8px;
				border-radius: 2px;
				background-color: @color-success;
				color: #ffffff;
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
		margin-top: 34px;
	}

	.filter-bar {
		position: fixed;
		left: 100px;
		padding-left: 10px;
		top: 40px;
		right: 0;
		height: 34px;
		line-height: 34px;
		background-color: #efeff4;
		text-align: center;
		font-size: 14px;
		z-index: 10;
	}
	.quote-status {
		position: absolute;
		right: 0;
		top: 0;
		font-size: 24rpx;
		height: 36rpx;
		line-height: 36rpx;
		padding: 0 10rpx;
		color: #ffffff;
		border-bottom-left-radius: 18rpx;
		&-VERIFY_SUCCESS {
			background-color: #39b54a;
		}
		&-VERIFY_FAIL {
			background-color: #e54d42;
		}
		&-WAIT_VERIFY {
			background-color: #fbbd08;
		}
	}
	.sale-label {
		position: absolute;
		left: 0;
		top: 0;
		font-size: 24rpx;
		height: 36rpx;
		line-height: 36rpx;
		padding: 0 10rpx;
		color: #ffffff;
		border-bottom-right-radius: 18rpx;
		&-success {
			background-color: #39b54a;
		}
		&-default {
			background-color: #cdc7c7;
		}
	}
</style>
<template>
	<view class="container">
		<view class="search-input-wrapper">
			<input placeholder="输入商品名进行筛选" class="search-input" @input="searchInputChange"/>
			<view class="search-input-btn iconfont icon-plus" @click="open('food-list')"></view>
		</view>
		<scroll-view
				scroll-y
				class="category-view">
			<view :class="{category: true, 'category-selected': !searchParam.foodCategoryName}"
						@click="selectCategory('')" v-bind:key="''">所有分类</view>
			<view v-for="(category, idx) in foodCategoryList"
						:class="{category: true, 'category-selected': searchParam.foodCategoryName === category}"
						@click="selectCategory(category)" v-bind:key="category">{{category}}</view>
		</scroll-view>
		<picker @change="saleChange" mode="selector" :value="saleIndex" :range="saleList">
			<view class="filter-bar">{{saleShow}}<text class="iconfont status-select-icon icon-unfold"></text></view>
		</picker>
		<view class="food-list">
			<template v-for="(suf, idx) in sufList">
				<view class="food" v-bind:key="suf.id">
					<view class="food-info">
						<image :src="suf.food.picture" @click="preview(suf.food.picture)" class="food-pic" mode="aspectFill"></image>
						<view class="food-detail">
							<view class="food-title">{{suf.food.name}}</view>
							<view class="flex-row space-between align-flex-end">
								<view class="food-price">报价: ¥
									<text class="food-price-val" >{{suf.supplierQuotePrice ? suf.supplierQuotePrice : 0}}</text>元/{{suf.foodUnit}}
								</view>
							</view>
						</view>
					</view>
					<view :class="['quote-status', 'quote-status-' + (suf.quoteStatus)]">{{suf.quoteStatusTitle}}</view>
					<view :class="['sale-label', 'sale-label-' + (suf.sale ? 'success' : 'default')]">{{suf.sale ? '已上架' : '已下架'}}</view>
					<view class="food-footer flex-row space-between align-flex-end">
						<uni-number-box :value="(suf.supplierAlterQuotePrice)" min="0" max="100000" :state-value="idx" step="0.1" @change="quotePriceChange"/>
					</view>
					<text class="text-orange" v-if="suf.warningPrice && suf.supplierAlterQuotePrice > suf.warningPrice">此产品价格过高影响销售，请降低报价</text>
					<view class="food-footer">
						<button class="cu-btn round bg-bg" @click="publishSku(idx)">规格</button>
						<button class="cu-btn round bg-orange" v-if="suf.sale" @click="soldOut(idx)">下架</button>
						<button class="cu-btn round bg-cyan" v-else @click="sale(idx)">上架</button>
					</view>
				</view>
			</template>
		</view>
		<view class="scrolltop-btn" @click="scrollTop">
			<text class="iconfont icon-fold"></text>
		</view>
	</view>
</template>

<script>
import uniNumberBox from '@/components/uni-number-box/uni-number-box.vue'
import uniTag from '@/components/uni-tag/uni-tag.vue'
import { search as SufSearch, changeSupplierAlterQuotePrice, soldOutByFoodId, saleByFoodId } from '../lib/api/store-user-food'
import { storeUserFoodList as PageSufList } from '../lib/api/page'
const DEFAULT_SEARCH_PARAM = {
  page: 1,
  foodCategoryName: ''
}
let changeTimeout = 0
export default {
  components: {
    uniNumberBox,
    uniTag
  },
  filters: {
    quoteStatusTagType (quoteStatus) {
      switch (quoteStatus) {
        case 'WAIT_VERIFY': return 'warning'
        case 'VERIFY_FAIL': return 'error'
        case 'VERIFY_SUCCESS': return 'success'
      }
    }
  },
  data () {
    return {
      searchParam: Object.assign({}, DEFAULT_SEARCH_PARAM),
      loading: false,
      hasNext: true,
      sufList: [],
      foodCategoryList: [],
      keyword: '',
      saleList: ['全部', '已上架', '未上架'],
      saleIndex: 1
    }
  },
  watch: {},
  computed: {
    saleShow () {
      return this.saleList[this.saleIndex]
    }
  },
  created () {
    this.selectSale(1)
    this.loadData().then(this.search()).finally(() => {
      this.loaded = true
    })
  },
  onReachBottom () {
    if (this.hasNext) {
      this.searchParam.page++
      this.search(true)
    }
  },

  onPullDownRefresh () {
    this.resetSearchParam()

    this.loadData().then(this.search()).finally(() => {
      uni.stopPullDownRefresh()
    })
  },
  methods: {
    publishSku (idx) {
      const suf = this.sufList[idx]
      wx.navigateTo({
        url: '/pages/store-user-food-sku?id=' + suf.id
      })
    },
    quotePriceChange (val, idx) {
      const suf = this.sufList[idx]
      suf.$lastAlterQuotePrice = suf.supplierAlterQuotePrice
      suf.supplierAlterQuotePrice = val
      if (changeTimeout) {
        clearTimeout(changeTimeout)
      }
      changeTimeout = setTimeout(() => {
        changeSupplierAlterQuotePrice(suf.id, suf.supplierAlterQuotePrice).then(rs => {
          Object.assign(suf, rs.data)
        })
      }, 500)
    },
    preview (url) {
      uni.previewImage({
        current: '', // 当前显示图片的http链接
        urls: [url] // 需要预览的图片http链接列表
      })
    },
    saleChange ({ detail }) {
      this.selectSale(detail.value)
      this.resetSearchParam()
      this.loadingSearch()
    },
    scrollTop () {
      uni.pageScrollTo({
        scrollTop: 0,
        duration: 300
      })
    },
    searchInputChange (e) {
      DEFAULT_SEARCH_PARAM.foodName = e.detail.value
      this.resetSearchParam()
      this.loadingSearch()
    },
    selectCategory (category) {
      DEFAULT_SEARCH_PARAM.foodCategoryName = category
      this.selectSale(0)
      this.resetSearchParam()
      this.loadingSearch()
    },
    open (url) {
      uni.navigateTo({
        url: url
      })
    },
    soldOut (idx) {
      const suf = this.sufList[idx]
      const self = this
      uni.showModal({
        title: '提示',
        content: '确认下架该商品吗？',
        success (res) {
          if (res.confirm) {
            soldOutByFoodId(suf.food.id).then(rs => {
              const data = rs.data
              if (data) {
                suf.sale = false
                uni.showToast({
                  title: '下架成功',
                  icon: 'success',
                  duration: 500
                })
              }
            })
          } else if (res.cancel) {

          }
        }
      })
    },
    sale (idx) {
      const suf = this.sufList[idx]
      const self = this
      uni.showModal({
        title: '提示',
        content: '确认上架该商品吗？',
        success (res) {
          if (res.confirm) {
            saleByFoodId(suf.food.id).then(rs => {
              const data = rs.data
              if (data) {
                suf.sale = true
                uni.showToast({
                  title: '上架成功',
                  icon: 'success',
                  duration: 500
                })
              }
            })
          } else if (res.cancel) {

          }
        }
      })
    },
    resetSearchParam () {
      Object.assign(this.searchParam, DEFAULT_SEARCH_PARAM)
      this.hasNext = true
    },

    selectSale (idx) {
      this.saleIndex = idx
      switch (idx + '') {
        case '0':
          DEFAULT_SEARCH_PARAM.sale = ''
          break
        case '1':
          DEFAULT_SEARCH_PARAM.sale = true
          break
        case '2':
          DEFAULT_SEARCH_PARAM.sale = false
          break
      }
    },

    loadingSearch (flag) {
      if (this.searchTimeout) {
        clearTimeout(this.searchTimeout)
      }
      this.searchTimeout = setTimeout(() => {
        uni.showLoading()
        this.search(flag).finally(() => {
          uni.hideLoading()
        })
      }, 500)
    },

    search (append, callback) {
      return SufSearch(this.searchParam).then((rs) => {
        const { data } = rs
        if (append) {
          this.sufList = this.sufList.concat(data.results)
        } else {
          this.sufList = data.results
        }
        this.hasNext = data.hasNext
      }).finally(() => {
        callback && callback()
      })
    },

    loadData () {
      return PageSufList().then(rs => {
        this.foodCategoryList = []
        this.$nextTick(() => {
          this.foodCategoryList = rs.data.foodCategoryList
        })
      })
    }
  }
}
</script>
