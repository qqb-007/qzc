<style lang="less">
	.page-head {
		margin-bottom: 40rpx;
		padding: 20rpx;
		font-size: 40rpx;
	}

	.container {
		padding: 20rpx;
	}

	.bottom {
		padding-top: 50rpx;
	}
</style>
<template>
	<view class="container">
		<view class="page-head">
			<text class="page-title text-title">欢迎登录王小菜报价版</text>
		</view>
		<view class="form-container">
			<view class="cu-form-group">
				<view class="title">手机号</view>
				<input placeholder="请输入手机号" v-model="form.phone" maxlength="11" name="input" autofocus/>
			</view>
			<view class="cu-form-group">
				<view class="title">验证码</view>
				<input placeholder="请输入验证码" v-model="form.verifyCode" type="password" maxlength="11" name="input" autofocus/>
				<text :class="{'verify-code-btn': true, disabled: !validatePhoneOk}" @click="sendPhoneCode()">{{cooldownSeconds ? '剩余' + cooldownSeconds + 's' : '获取验证码'}}</text>
			</view>
		</view>
		<view class="bottom flex flex-direction">
			<button class='cu-btn bg-green shadow lg' @click="login()" :disabled="!validateOk">登 录</button>
		</view>
	</view>
</template>

<style lang="less">
	.form-container {
		background-color: #fff;
	}

	.verify-code-wrapper {
		flex-direction: row;
		display: flex;
		align-items: center;
	}

	.cu-form-group .title {
		min-width: calc(4em + 15px);
	}

	.verify-code {
		flex: 1;
		&-btn {
			width: 100px;
			font-size: 12px;
			line-height: 30px;
			height: 30px;
			color: #ffffff;
			text-align: center;
			border-radius: 30px;
			background-color: #19be6b;
			&.disabled {
				background-color: #d4d4d4;
			}
		}
	}
</style>

<script>
export default {
  data () {
    return {
      form: {
        phone: '',
        verifyCode: ''
      },
      phone: '',
      loginDoing: false,
      cooldownSeconds: 0
    }
  },
  watch: {},
  computed: {
    validatePhoneOk () {
      return this.validatePhone()
    },
    validateOk () {
      return this.validate()
    }
  },
  created () {
    this.$store.dispatch('session/checkLogin')
  },
  methods: {
    login () {
      if (!this.validateOk || this.loginDoing) {
        return
      }
      uni.showLoading({
        title: '登录中'
      })
      this.$store.dispatch('session/login', this.form).then((rs) => {
        this.loginDoing = false
      }).catch(() => {
        this.loginDoing = false
      }).finally(() => {
        this.loginDoing = false
        uni.hideLoading()
      })
    },
    sendPhoneCode () {
      if (this.validatePhone()) {
        api.sms.phoneLogin({ phone: this.form.phone }).done(rs => {
          const data = rs.data
          if (data.success) {
            this.cooldownSeconds = data.cooldown
            this.$apply()
            this.cooldown()
          } else {
            uni.showModal({
              title: '温馨提示',
              content: '短信登录验证码发送失败，原因：' + data.errMsg,
              success (res) {

              }
            })
          }
        })
      } else {
        uni.showToast({
          title: '手机号输入错误',
          icon: 'none',
          duration: 1000
        })
      }
    },
    cooldown () {
      if (this.cooldownSeconds) {
        this.cooldownSeconds--
        this.$apply()
        setTimeout(() => {
          this.cooldown()
        }, 1000)
      }
    },

    validate () {
      if (this.form.phone.trim().length !== 11) {
        return false
      }
      if (this.form.verifyCode.length < 3) {
        return false
      }
      return true
    },

    validatePhone () {
      if (this.form.phone.trim().length !== 11) {
        return false
      }
      return true
    }
  }
}
</script>
