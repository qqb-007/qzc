import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import session from './modules/session'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    session
  },
  getters
})

export default store
