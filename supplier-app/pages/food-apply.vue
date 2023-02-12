<style lang='css' src='../static/css/icon.css'></style>
<style lang='less' src='../static/css/color.less'></style>
<style lang='less' src='../static/css/text.less'></style>
<style lang='less' src='../static/css/utils.less'></style>
<style lang='less' src='../static/css/bg.less'></style>
<style lang='less' src='../static/css/style.less'></style>
<style lang="less">

  @import "../static/css/var";

  .container {
    margin: 20rpx;
    /*border-radius: 10rpx;*/
    /*box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.25);*/
    overflow: hidden;
  }

  .panel {
    padding: 20px;
    background-color: #ffffff;
  }

  .bank {
    &-card {
      height: 60px;
      border-radius: 5px;
      background-image: linear-gradient(to right, #65AFE4, #1DB8A3);
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px;
      &-icon {
        width: 54px;
        height: 54px;
        border-radius: 54px;
        background-color: #ffffff;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #1DB8A3;
        .iconfont {
          font-size: 34px;
        }
      }
    }
    &-name {
      color: #ffffff;
      font-size: 14px;
    }
    &-account {
      color: #ffffff;
      font-size: 16px;
    }
    &-owner {
      color: #ffffff;
      font-size: 18px;
    }
  }

  .tip {
    color: @color-muted;
    font-size: 12px;
    text-align: center;
    margin-top: 5px;
  }

  .money {
    font-size: 14px;
    margin-top: 20px;
    color: @color-title;
    &-value {
      font-size: 20px;
      color: @color-danger;
    }
  }

  .withdraw-input {
    border-bottom: 1rpx solid #ddd;
    display: flex;
    align-items: center;
    margin-top: 15px;
    &-y {
      font-size: 18px;
      color: #000;
      width: 30px;
      text-align: center;
    }
    &-val {
      flex: 1;
      height: 40px;
      font-size: 20px;
      line-height: 40px;
    }
  }

  .bottom {
    padding: 40rpx 0;
  }

  .form-input {
    font-size: 14px;
  }

  .food-pic {
    width: 200px;
    height: 200px;
  }
  .food-pic-item {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    background-color: #ffffff;
    border-bottom: 1rpx solid #ddd;
    padding: 20rpx;
  }
</style>
<template>
  <view class="container">
    <view class="cu-list menu" >
      <view class="food-pic-item">
        <image :src="foodPicture ? foodPicture : (form.foodPicture ? form.foodPicture : 'http://wxc123456.oss-cn-hangzhou.aliyuncs.com/images/upload-bg.png')"
               slot="footer" class="food-pic" mode="aspectFill" @click="selectPicture"></image>
      </view>
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">商品名称</text>
        </view>
        <view class="action">
          <input type="text" v-model="form.foodName" class="form-input" placeholder="请输入商品名称"/>
        </view>
      </view>
      <view class="cu-item">
        <view class="content">
          <text class="text-grey">规格</text>
        </view>
        <view class="action">
          <input type="text" v-model="form.unit" class="form-input" placeholder="请输入规格"/>
        </view>
      </view>
      <view class="cu-item" v-if="!foodId">
        <view class="content">
          <text class="text-grey">售价</text>
        </view>
        <view class="action" >
          <uni-number-box v-model="form.price" min="0" max="100000" step="0.1" />
        </view>
      </view>
    </view>
    <view class="bottom">
      <button class='cu-btn bg-green shadow lg' style="width: 100%;" @click="applyFood()" :disabled="!validateOk">提 交</button>
    </view>
  </view>
</template>

<script>
import { acs } from '../lib/api/aliyun'
import { applyFood } from '../lib/api/store-user-food'
import { foodApply } from '../lib/api/page'
import uniNumberBox from '@/components/uni-number-box/uni-number-box.vue'
export default {
  components: {
    uniNumberBox
  },
  data () {
    return {
      foodPicture: '',
      foodId: 0,
      form: {
        foodName: '',
        unit: '',
        price: 0,
        foodPicture: ''
      }
    }
  },

  computed: {
    validateOk () {
      return this.validate()
    }
  },

  watch: {
    form: {
      handler () {
      },
      deep: true
    }
  },

  methods: {
    selectPicture () {
      const self = this
      wx.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success (res) {
          // tempFilePath可以作为img标签的src属性显示图片
          const tempFilePaths = res.tempFilePaths
          self.foodPicture = tempFilePaths[0]
        }
      })
    },
    validate (showFlag) {
      console.info('validate')
      if (this.form.foodName.trim().length < 1) {
        showFlag && this.showMsg('请填写商品名')
        return false
      }
      if (this.form.unit.length < 1) {
        showFlag && this.showMsg('请填写商品规格')
        return false
      }
      if (this.form.price <= 0) {
        showFlag && this.showMsg('商品报价输入不正确')
        return false
      }
      console.info(true)
      return true
    },
    showMsg (msg, type = 'error') {
      uni.showToast({
        title: msg,
        icon: 'none'
      })
    },
    applyFood () {
      const self = this
      if (!this.validate(true)) {
        return
      }
      if (!this.foodPicture) {
        this.doApply()
        return
      }
      wx.showLoading()
      acs({
        filename: self.foodPicture.substring(self.foodPicture.lastIndexOf('/'))
      }).then(rs => {
        wx.uploadFile({
          url: rs.data.uploadUrl,
          filePath: self.foodPicture,
          name: 'file',
          formData: rs.data.formData,
          success (res) {
            self.form.foodPicture = rs.data.objectUrl
            self.doApply()
          }
        })
      }).catch(() => {
        wx.hideLoading()
        this.showMsg('文件上传失败')
      })
    },
    doApply () {
      applyFood(this.form).then(rs => {
        wx.showModal({
          title: '提示',
          content: '商品申请已经提交，客服会及时审核，请等待！',
          success (res) {
            wx.navigateBack()
          }
        })
      }).finally(() => {
        wx.hideLoading()
      })
    }
  },

  onPullDownRefresh () { },
  onLoad (options) {
    // wx.startPullDownRefresh()
    // this.loadData()
    if (options.foodId) {
      this.foodId = options.foodId
      foodApply({ foodId: options.foodId }).then(rs => {
        const data = rs.data
        this.form.foodPicture = data.foodPicture
        this.form.foodName = data.foodName
        this.form.price = data.foodPrice
      })
    }
  }
}
</script>
