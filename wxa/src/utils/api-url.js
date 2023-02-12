var mode = 'dev';
module.exports =  function (path){
    var base;
    switch (mode) {
        case 'pro': base = 'https://api.merchant.wangxiaocai.cn/'; break;
        case 'dev': base = 'http://192.168.31.174:8080/'; break;
    }
    return base + path;
}
