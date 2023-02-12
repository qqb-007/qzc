import wepy from 'wepy'

export default class bindMixin extends wepy.mixin {
  methods = {
    bindData(e) {
      var dataset = e.currentTarget.dataset
      let segs = dataset.key.split(".")
      var currentPath = this.data
      let val = e.detail.detail ? e.detail.detail.value : e.detail.value
      for(var i = 0; i < segs.length; i++) {
        if(i == segs.length - 1) {
          currentPath[segs[i]] = val
        } else {
          if (!this.data[segs[i]]) {
            this.data[segs[i]] = {}
          }
          currentPath = this.data[segs[i]]
        }
      }
      this.$apply()
    }
  }
}
