import Vue from 'vue'
import App from './App'
import store from './store'

import basics from './pages/basics/home.vue'

import components from './pages/component/home.vue'

import plugin from './pages/plugin/home.vue'

import cuCustom from './colorui/components/cu-custom.vue'
Vue.component('basics', basics)
Vue.component('components', components)
Vue.component('plugin', plugin)
Vue.component('cu-custom', cuCustom)

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
  ...App,
  store
})
app.$mount()
