<style lang="less">
	@import "../static/css/var";

	.op-item {
		align-items: center;
		display: flex;
		flex-direction: column;
		padding: 30rpx;
		&-title {
			font-size: 12px;
			color: #495060;
			margin-top: 5px;
		}
		&-icon {
			font-size: 36px;
		}
	}

	.top {
		background-color: @color-primary;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		&-item {
			flex: 1;
			display: flex;
			flex-direction: column;
			align-items: center;
			padding: 20px 0;
		}
		&-val {
			font-weight: bold;
			color: #ffffff;
			font-size: 30px;
		}
		&-txt {
			font-weight: 300;
			font-size: 12px;
			color: #8f9aa6;
		}
	}

	.time-range-bar {
		height: 40px;
		flex-direction: row;
		display: flex;
		align-items: center;
		font-size: 14px;
		background-color: #fff;
		border-bottom: 1rpx solid #ddd;
	}

	.day-input {
		border: 1px solid #ddd;
		border-radius: 5px;
		margin: 0 20px;
		padding: 0px 20px;
		height: 20px;
		line-height: 20px;
	}

	.biz-cell {
		padding: 10px;
		&-title {
			color: @color-success;
			font-size: 14px;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		&-val {
			font-size: 18px;
		}
		&-plat {
			@height: 20px;
			flex: 1;
			font-size: 12px;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			margin: 0px 5rpx;
			border-radius: 3px;
			overflow: hidden;
			&:first-child {
				border-right: none;
			}

			line-height: @height;
			text-align: right;
			&-val {
				text-align: right;
				flex: 1;
				font-size: 12px;
				border: 1rpx solid #ddd;
				background-color: #efefef;
				padding-right: 5rpx;
			}
			&-m, &-e {
				width: @height;
				text-align: center;
				display: inline-block;
			}
			&-m{
				background-color: #06c0ab;
				color: #fff;
			}
			&-e{
				background-color: #1ca8e4;
				color: #fff;
			}
			&-info {
				display: flex;role
				flex-direction: row;
				align-items: stretch;
				height: @height;
				overflow: hidden;
			}
		}
	}

	.container {
		background-color: #efeff4;
	}

	.tabs {
		margin-top: 10px;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		background-color: #ffffff;
		border-bottom: 1rpx solid #ddd;
	}

	.tab-item {
		padding: 0 10px;
		height: 80rpx;
		line-height: 80rpx;
		font-size: 14px;
		&-selected {
			color: @color-primary;
			font-weight: bold;
		}
	}

	.biz-data-grid {
		background-color: #ffffff;
	}

	.sticky-top {
		position: sticky;
		top: 0rpx;
		z-index: 1000;
	}

	.icon-detail {
		color: #243850;
		&-show {
			color: #1ca8e4;
		}
	}
	.balance-card {
		margin: 10px;
		border-radius: 10px;
		height: 170px;
		flex-direction: row;
		display: flex;
		justify-content: space-between;
		background-image: linear-gradient(to top, #7C8AA0, #637083);
		color: #ffffff;
		padding: 20px;
		position: relative;
		box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.43);
		&-dqye {
			font-size: 12px;
		}
		&-ljsr {
			font-size: 12px;
			margin-top: 3px;
			&-value {
				font-weight: bold;
			}
		}
		&-value {
			position: relative;
			&-y, &-r {
				font-size: 22px;
				/*font-weight: bold;*/
			}
			&-r {
				font-weight: bold;
			}
			&-l {
				font-size: 40px;
				font-weight: bold;
			}
			&-detail-btn {
				font-size: 14px;
				margin-left: 5px;
			}
		}
		&-withdraw-btn {
			background-color: #ffffff;
			height: 30px;
			width: 90px;
			text-align: center;
			line-height: 30px;
			border-radius: 30px;
			font-size: 14px;
			color: #545f71;
			&:active {
				background-color: #6f7f99;
			}
		}
		&-sd {
			background-color: #545f71;
			height: 30px;
			position: absolute;
			bottom: 15px;
			right: 0;
			left: 0;
			padding: 10px 20px;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			font-size: 14px;
			align-items: center;
		}
	}
	.sales {
		&-detail {
			display: flex;
			flex-direction: row;
			background-color: #ffffff;
			border-bottom: 1rpx dashed #ddd;
			padding: 20rpx;
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
</style>
<template>
	<view class="container">
		<view class="top">
			<view class="top-item">
				<view class="top-val">{{(bizData.remainMoney || 0).toFixed(1)}}</view>
				<view class="top-txt">今日订单收入/元</view>
			</view>
			<div class="top-item">
				<view class="top-val">{{bizData.validOrderNum || 0}}</view>
				<view class="top-txt">今日单量/单</view>
			</div>
		</view>
		<view class="sticky-top">
			<view class="tabs">
				<view :class="{ 'tab-item': true, 'tab-item-selected': dayType === 'today' }" @click="dayChange('today')">今天</view>
				<view :class="{ 'tab-item': true, 'tab-item-selected': dayType === 'last1Days' }" @click="dayChange('last1Days')">昨天</view>
				<view :class="{ 'tab-item': true, 'tab-item-selected': dayType === 'last7Days' }" @click="dayChange('last7Days')">近7天</view>
				<view :class="{ 'tab-item': true, 'tab-item-selected': dayType === 'last30Days' }" @click="dayChange('last30Days')">近30天</view>
				<view :class="{ 'tab-item': true, 'tab-item-selected': dayType === 'customDays' }" @click="dayChange('customDays')">自定义</view>
			</view>
			<view class="time-range-bar" v-if="dayType === 'customDays'">
				<picker @change="bindPickerChange" data-key="startDate" mode="date" :value="startDate" :end="endDate">
					<view class="day-input">{{startDate}}</view>
				</picker>
				<view>至</view>
				<picker @change="bindPickerChange" data-key="endDate" mode="date" :value="endDate" :start="startDate"
								:end="maxDate">
					<view class="day-input">{{endDate}}</view>
				</picker>
			</view>
		</view>
		<view class="x-grid biz-data-grid" @click="toggleDetailShow">
			<view class="x-grid-row">
				<view class="x-grid-item biz-cell">
					<view class="biz-cell-title">
						<text>营业额</text>
					</view>
					<view class="biz-cell-val">¥{{(dayBizData.totalMoney || 0).toFixed(1)}}</view>
				</view>
				<view class="x-grid-item biz-cell">
					<view class="biz-cell-title">单均价</view>
					<view class="biz-cell-val">¥{{(dayBizData.perFee || 0).toFixed(1)}}</view>
				</view>
			</view>
			<view class="x-grid-row">
				<view class="x-grid-item biz-cell">
					<view class="biz-cell-title">
						有效订单
					</view>
					<view class="biz-cell-val">{{(dayBizData.validOrderNum || 0)}}单</view>
				</view>
				<view class="x-grid-item biz-cell">
					<view class="biz-cell-title">无效订单</view>
					<view class="biz-cell-val">{{(dayBizData.invalidOrderNum || 0)}}单</view>
				</view>
			</view>
		</view>
		<view class="cu-bar bg-white solid-bottom margin-top-sm">
			<view class="action">
				<text class="cuIcon-titles text-orange"></text> 销售排行
			</view>
			<view class="action">
				<text class="">{{dayTypeTitle}}</text>
			</view>
		</view>
		<view class="">
			<view class="sales-detail" v-for="detail in salesData" @click="preview(detail.foodPicture)" v-bind:key="detail.id">
				<image :src="detail.foodPicture + '?x-oss-process=style/xs'" class="sales-food-pic" mode="aspectFill"></image>
				<view class="sales-food-name">{{detail.foodName}}</view>
				<view class="sales-food-quantity">{{detail.sales}} × {{detail.unit}}</view>
				<view class="sales-food-money text-orange text-lg">¥{{Math.floor((detail.money || 0) * 10) / 10}}</view>
			</view>
		</view>
	</view>
</template>

<script>
import { statFoodSupplierBizData, statFoodSupplierFoodSales } from '../lib/api/stat'
import { index as PageIndex } from '../lib/api/page'
const color = require('../lib/color')
const DAY_TYPE_TITLE = {
  today: '今天',
  last1Days: '昨天',
  last7Days: '近7天',
  last30Days: '近30天',
  customDays: '自定义'
}
export default {
  data () {
    return {
      bizData: {},
      dayType: 'last1Days',
      startDate: '',
      salesData: [],
      endDate: '',
      maxDate: '',
      tabColor: color.success,
      dayBizData: {},
      obtainedMoney: 0,
      showDetail: false
    }
  },
  watch: {},
  computed: {
    dayTypeTitle () {
      return DAY_TYPE_TITLE[this.dayType]
    }
  },
  created () {
    uni.startPullDownRefresh()
  },
  onPullDownRefresh () {
    this.loadData().then(() => {
      uni.stopPullDownRefresh()
    })
  },
  methods: {
    loadBizData (showLoading) {
      return statFoodSupplierBizData({
        dayType: this.dayType,
        startTime: this.startDate,
        endTime: this.endDate
      }).then(rs => {
        this.dayBizData = rs.data
      })
    },
    loadSales () {
      return statFoodSupplierFoodSales({
        dayType: this.dayType,
        startTime: this.startDate,
        endTime: this.endDate
      }).then(rs => {
        this.salesData = rs.data
      })
    },
    open (url) {
      uni.navigateTo({
        url: url
      })
    },
    dayChange (type) {
      this.dayType = type
      uni.showLoading()
      this.loadBizData().then(this.loadSales()).finally(() => {
        uni.hideLoading()
      })
    },
    bindPickerChange ({ detail, currentTarget }) {
      this[currentTarget.dataset.key] = detail.value
      this.loadBizData(true)
    },
    toggleDetailShow () {
      this.showDetail = !this.showDetail
    },
    loadData () {
      this.loadBizData()
      this.loadSales()
      return PageIndex().then(rs => {
        const data = rs.data
        this.bizData = data.bizData
        this.startDate = data.startDate
        this.endDate = data.endDate
        this.maxDate = data.endDate
      })
    }
  }
}
</script>
