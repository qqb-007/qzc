<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>淘宝拉新有奖励</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="/css/spop.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/js/swiper.js"></script>
    <script type="text/javascript" src="/js/spop.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/clipboard.js"></script>
    <style>
        body {
            padding-bottom: 60px;
            background-color: #ff7a1a;
        }
        .top-banner {
            text-align: center;
            margin: auto;
            max-width: 750px;
        }
        .top-banner .top-banner-img {
            width: 100%;
            height: 100%;
            display: block;
            margin: auto;
        }
        .vetas-wrap {
            max-width: 750px;
            position: relative;
            background: url(//gw.alicdn.com/tfs/TB1is6jbAyWBuNjy0FpXXassXXa-750-555.png) #fafafa;
            background-size: 100% 100%;
            margin: auto;
            text-align: center;
            height: 400px;
        }
        .vetas-title {
            font-size: 18px;
            color: #8b572a;
            text-align: center;
            padding-top: 20px;
            position: relative;
        }
        textarea {
            background-color: transparent;
            margin: auto;
            width: 100%;
            max-width: 300px;
            font-size: 16px;
            height: 130px;
            margin-top: 20px;
        }
        .action-bar {
            display: flex;
            flex-direction: row;
            width: 300px;
            margin: auto;
            margin-top: 20px;
            border-radius: 10px;
            overflow: hidden;
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
            background: #fb4748;
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
            border:
    </style>
    <script type="text/javascript">
        $(function() {
            $('#shareText').focus(function() {
                $(this).select();
            })
            var clipboard = new ClipboardJS('.copy-kl-btn');
            clipboard.on('success', function(e) {
                $('.copy-kl-btn').text('口令已复制')
                spop({
                    template: '口令已复制，请打开手机淘宝进行领取礼包！',
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
<#include "download-bar.ftlh" />
<div class="top-banner">
    <img class="top-banner-img" src="//gw.alicdn.com/tfs/TB133ZVceuSBuNjy1XcXXcYjFXa-750-240.png" />
</div>
<div class="vetas-wrap">
    <div class="vetas-title">
        淘宝新用户领最高<span style="color:#e74431;">188元大礼包</span>
        <div>其中包含<span style="color:#e74431;">10</span>元无门槛红包</div>
    </div>
    <div id="shareText" class="tkl-text">${shareInfo.text!''}</div>
    <div class="action-bar">
        <button class="action-bar-btn copy-kl-btn" data-clipboard-action="copy" data-clipboard-target="#shareText">复制淘口令</button>
        <button data-toggle="popover" data-placement="top"
                data-content="请点击微信右上角，选择在浏览器中打开，即可跳转领取！" class="action-bar-btn buy-btn">现在去领取</button>
    </div>
</div>
</body>
</html>