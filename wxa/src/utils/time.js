module.exports = {
    formatNumber(n) {
        n = n.toString()
        return n[1] ? n : '0' + n
    },
    formatTime(number, format) {

        var formateArr = ['Y', 'M', 'D', 'h', 'm', 's'];
        var returnArr = [];

        var date = new Date(number * 1000);
        returnArr.push(date.getFullYear());
        returnArr.push(this.formatNumber(date.getMonth() + 1));
        returnArr.push(this.formatNumber(date.getDate()));

        returnArr.push(this.formatNumber(date.getHours()));
        returnArr.push(this.formatNumber(date.getMinutes()));
        returnArr.push(this.formatNumber(date.getSeconds()));

        for (var i in returnArr) {
          format = format.replace(formateArr[i], returnArr[i]);
        }
        return format;
    },
    judgeTime(date){
            var today = new Date()
                today.setHours(0);
                today.setMinutes(0);
                today.setSeconds(0);
                today.setMilliseconds(0);
            var otime = today.getTime()
                //给出时间 - 今天0点
            var offset= date*1000-otime
            var isToday = offset/1000/60/60
            if(isToday > 0 && isToday<= 24){
                return "今日配菜"
            }else if(isToday >= 24){
                return "明日配菜"
            }else{
                return "其他时间"
            }
        }
}
