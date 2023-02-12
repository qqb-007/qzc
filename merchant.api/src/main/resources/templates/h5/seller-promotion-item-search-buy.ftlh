<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>天购优选 - 优惠券 - ${item.title!''}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <style>
        body {
            font-size: 16px;
            /*font-weight: 300;*/
        }
        .screenshot-pic {
            max-width: 100%;
        }
    </style>
</head>
<body>
<div>
    <div class="alert alert-warning" style="padding: 10px;">去搜索前，请确保您已经安装有手机${item.ecomPlatTitle}</div>
    <ol>
        <li>点击去下单，会自动跳转到手机${item.ecomPlatTitle}，我们会自动帮您输入关键词</li>
        <li>点击按照"综合"排序</li>
        <li>找到<span class="text-danger">主图和天购优选相同</span>的宝贝，核对商家店铺名：<span class="text-danger">${shopTitle}</span>，
            完整店铺名有<span class="text-danger">${shopTitleLength}个字</span>
        </li>
    </ol>
    <div>
        <#list item.screenshotList as screenshot>
            <img src="${screenshot.mediumUrl}" class="screenshot-pic" />
        </#list>
    </div>
    <div class="alert alert-warning">
        <p>注意：</p>
        <p>下单成功后，记得回天购优选填写订单号哦！</p>
        <p>请核对好商家和商品图片是否对应才进行下单哦！</p>
        <p>请核对好下单价是否对应天购优选的下单价才进行下单哦！</p>
    </div>
</div>
</body>
</html>