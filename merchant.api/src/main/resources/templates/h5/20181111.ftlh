<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>辣小客 - 优惠券 - 双11超级红包来袭</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="/css/swiper.css"/>
	<link rel="stylesheet" type="text/css" href="/css/spop.css"/>
	<link rel="stylesheet" type="text/css" href="/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_565950_f19ft1tffiv.css"/>
	<script type="text/javascript" src="/js/spop.js"></script>
	<script type="text/javascript" src="/js/jquery-1.12.2.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/clipboard.js"></script>
	<style>
		body {
			font-size: 14px;
			font-weight: 300;
			background: #2b2b2b;
			text-align: center;
			padding-top: 20px;
		}

		.top {
			max-width: 60%;
		}

        .hb {
            background: url("/images/hb.png") no-repeat;
            background-size: contain;
            width: 100%;
            height: 480px;
            margin: -140px auto 0;
        }
        .tkl {
            padding-top: 200px;
            width: 60%;
            margin: auto;
            color: #fff;
            font-weight: bold;
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
			height: 40px;
            border-radius: 40px;
			line-height: 40px;
			text-align: center;
			font-size: 18px;
			border: none;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.78);
		}
		.action-bar-btn:active {
			opacity: .8;
		}

		.buy-btn {
			width: 246px;
            height: 87px;
            margin-top: 10px;
		}

		.copy-kl-btn {
			background: #fb4444;

			color: #ffffff;
            margin-top: 20px;
		}
	</style>
	<script type="text/javascript">
		$(function () {
			$('#shareText').focus(function () {
				$(this).select();
			})
			var clipboard = new ClipboardJS('.copy-kl-btn');
			clipboard.on('success', function (e) {
				$('.copy-kl-btn').text('口令已复制')
				spop({
					template: '口令已复制，请打开手机淘宝进行购买！',
					position: 'top-center',
					style: 'success',
					autoclose: 2000
				});
			});

			clipboard.on('error', function (e) {
				alert('口令复制错误，请手动复制！')
			});

		})
		$(function () {
			$('[data-toggle="popover"]').popover()
		})
	</script>
</head>
<body>
<img src="/images/top.png" class="top"/>
<div class="hb">
    <div class="tkl" id="shareText">${taobao1111Setting.superRedPacketText!''}</div>
	<button class="action-bar-btn copy-kl-btn" data-clipboard-action="copy" data-clipboard-target="#shareText">复制淘口令</button>
    <div>
		<img src="/images/go.png" data-toggle="popover" data-placement="top"
			 data-content="请点击微信右上角，选择在浏览器中打开，即可跳转购买！" class="buy-btn"/>
    </div>
</div>
<#--<#include "download-bar.ftlh" />-->
<div id="shareTextWapper">
<#--<div class="share-text-area" id="shareText">复制这条信息，${taobao1111Setting.token!''}打开手机淘宝即可购买！</div>-->
</div>
<#include "footer.ftlh" />
</body>
</html>