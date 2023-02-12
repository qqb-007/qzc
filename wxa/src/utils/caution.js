module.exports = {
  filter(str) {
    var arr = str.split("【如遇缺货】")
    str = arr[0]
    arr = str.split("收餐人隐私号")
    return arr[0];
  }
}
