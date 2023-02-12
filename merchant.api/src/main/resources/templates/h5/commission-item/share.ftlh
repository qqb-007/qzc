<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>天购优选 - 优惠券 - ${item.title!''}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="/css/spop.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_565950_f19ft1tffiv.css"/>
    <script type="text/javascript" src="/js/swiper.js"></script>
    <script type="text/javascript" src="/js/spop.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/clipboard.js"></script>
    <style>
        body {
            font-size: 14px;
            font-weight: 300;
        }
        .swiper-container {
            width: 100%;
            height: calc(100vw);
        }
        .swiper-slide img {
            max-width: 100%;
        }
        .ecom-plat {
            background-color: #fb4748;
            color: #ffffff;
            padding: 0 5px;
            border-radius: 5px;
            font-size: 12px;
        }
        .title {
            margin-bottom: 10px;
            line-height: 18px;
        }
        .panel {
            padding: 10px;
            background-color: #ffffff;
            margin-bottom: 10px;
        }

        .flex-row {
            flex-direction: row;
            justify-content: space-between;
            align-items: flex-end;
            display: flex;
        }
        .coupon {
            background-image: linear-gradient(to right, #fc7317, #f43119);
            display: flex;
            height: 24px;
            line-height: 24px;
            padding: 0 12px;
            border-radius: 3px;
            position: relative;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            color: #ffffff;
        }
        .coupon:after, .coupon:before {
            content: " ";
            width: 6px;
            height: 6px;
            border-radius: 6px;
            background-color: #ffffff;
            position: absolute;
            top: 50%;
            margin-top: -3px;
            left: -3px;
        }
        .coupon:after {
            left: auto;
            right: -3px;
        }
        .price {
            font-size: 24px;
            color: #fb4748;
        }
        .price-y {
            font-size: 12px;
        }
        .sales {
            color: #666;
        }
        .shop-title {
            background-color: #f5f5f5;
            padding: 10px 0;
        }
        .action-bar {
            display: flex;
            flex-direction: row;
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
        }

        .action-bar-btn {
            flex: 1;
            height: 50px;
            line-height: 50px;
            text-align: center;
            font-size: 18px;
            border: none;

        }
        .action-bar-btn:active {
            opacity: .8;
        }

        .buy-btn {
            background: #fc7317;
            color: #ffffff;
        }

        .copy-kl-btn {
            background: #FD9F28;
            color: #ffffff;
        }
        .share-text-area {
            width: 100%;
            font-size: 14px;
            font-weight: 300;
            background-color: #ffffff;
            padding: 10px;
            height: 60px;
            border: none;
        }
        .share-btn {
            width: 50px;
            height: 50px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            border-top: 1px solid #fefefe;
            background-color: #ffffff;
            text-align: center;
        }
        .share-btn i {
            font-size: 26px;
            line-height: 26px;
        }
        body {
            padding-bottom: 60px;
        }
    </style>
    <script type="text/javascript">
        $(function() {
            var mySwiper = new Swiper ('.swiper-container', {
                // 如果需要分页器
                pagination: {
                    el: '.swiper-pagination',
                }

            })
            $('#shareText').focus(function() {
                $(this).select();
            })
            var clipboard = new ClipboardJS('.copy-kl-btn');
            clipboard.on('success', function(e) {
                $('.copy-kl-btn').text('口令已复制')
                spop({
                    template: '口令已复制，请打开手机淘宝进行购买！',
                    position: 'top-center',
                    style: 'success',
                    autoclose: 2000
                });
            });

            clipboard.on('error', function(e) {
                alert('口令复制错误，请手动复制！')
            });

        })
        $(function () {
            $('[data-toggle="popover"]').popover()
        })
    </script>
</head>
<body>
<#include "../download-bar.ftlh" />
<div class="swiper-container">
    <div class="swiper-wrapper">
        <#list item.imgList as img>
            <div class="swiper-slide">
                <img src="${img}" />
            </div>
        </#list>
    </div>
    <!-- 如果需要分页器 -->
    <div class="swiper-pagination"></div>
</div>
<div class="panel">
    <div class="title">
        <label class="ecom-plat">${item.ecomPlatTitle}</label> ${item.title}
    </div>
    <div class="flex-row">
        <div class="price"><span class="price-y">¥</span>${item.price}</div>
        <div class="sales">销量:${item.sales}</div>
        <div class="coupon">券 ¥${item.couponValue!0}</div>
    </div>
</div>
<div id="shareTextWapper">
    <div class="share-text-area" id="shareText">复制这条信息，${shareInfo.kl!''}打开手机淘宝即可购买！</div>
</div>
<div class="action-bar">
    <a href="item-share/${item.id}" class="share-btn">
        <i class="iconfont icon-fenxiang2-copy"></i>
    </a>
    <button class="action-bar-btn copy-kl-btn" data-clipboard-action="copy" data-clipboard-target="#shareText">复制淘口令</button>
    <button data-toggle="popover" data-placement="top"
            data-content="请点击微信右上角，选择在浏览器中打开，即可跳转购买！" class="action-bar-btn buy-btn">打开淘宝购买</button>
</div>
<#include "../footer.ftlh" />
</body>
</html>