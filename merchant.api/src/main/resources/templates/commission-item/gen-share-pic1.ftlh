<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/qrcode.js"></script>
    <script type="text/javascript" src="/js/save-svg.js"></script>
</head>
<style>
    body {
        margin: 0;
        padding: 0;
        background: #eff1f3;
    }

    .svg {
        max-width: 100%;
        max-height: 100%;
        display: none;
    }

    .container {
        position: absolute;
        top: 10px;
        right: 0;
        left: 0;
        bottom: 10px;
    }
    .canvas {
        max-width: 100%;
        max-height: 100%;
    }
</style>
<body>
<div class="container">
    <canvas id="canvas" class="canvas"></canvas>
    <svg viewBox="0 0 800 1080" id="svg" class="svg" xmlns="http://www.w3.org/2000/svg"
         xmlns:xlink="http://www.w3.org/1999/xlink">
        <!-- Created with Method Draw - http://github.com/duopixel/Method-Draw/ -->
        <style>
            text {
                /*font-weight: 300;*/
                /*font-family: "PingFang TC";*/
                font-family: "PingFang TC", Arial, sans-serif;
            }

            .main-pic {
            }

            .title-g {
            }

            .title {
                font-family: "Helvetica, Arial, sans-serif";
                font-size: 30px;
                text-anchor: start;
                line-height: 40px;
            }

            .sale-price {
                font-family: "PingFang TC", Arial, sans-serif;
                font-size: 36px;
                fill: #777;
            }

            .price-g {
            }

            .coupon-bg {
                fill: #fb4748;
            }

            .coupon-quan {
                font-size: 32px;
                color: #ffffff;
            }

            .coupon-val-bg {
                fill: #fff;
            }

            .coupon-val {
                text-anchor: middle;
                font-family: "PingFang TC", Arial, sans-serif;
                font-size: 32px;
                fill: #fb4748;
            }

            .price-title {
                font-size: 28px;
                fill: #777;
            }

            .price-val {
                font-family: "PingFang TC", Arial, sans-serif;
                font-size: 60px;
                font-weight: normal;
                fill: #fb4748;
            }

            .price-y {
                font-family: "PingFang TC", Arial, sans-serif;
                font-size: 40px;
                font-weight: normal;
                fill: #fb4748;
            }

            .fl-tip {
                font-size: 18px;
                fill: #777;
            }

            .final-price {
                font-size: 32px;
            }

            .fill-warning {
                fill: #fb8c47;
            }

            .fill-primary {
                fill: #fb4748;
            }

            .fill-muted {
                fill: #8e8e8e
            }

            .corner {
                width: 30px;
                height: 30px;
                fill: #e39334;
            }

            .qrcode-hide {
                display: none;
            }

            .label-econ {
                background: #fb4748;
                margin-right: 10px;
                padding: 5px 10px;
                border-radius: 5px;
                font-size: 22px;
                margin-bottom: 5px;
                color: #ffffff;
                line-height: 20px;
                height: 20px;
                vertical-align: middle;
            }
        </style>
        <g>
            <title>background</title>
            <rect fill="#fff" id="canvas_background" height="1100" width="800" y="0" x="0"/>
            <g display="none" id="canvasGrid">
                <rect fill="url(#gridpattern)" stroke-width="0" y="0" x="0" height="100%" width="100%"/>
            </g>
        </g>
        <image xlink:href="${svg.picUrl}" id="mainImg" x="0"
               y="0" class="main-pic" width="800" height="800"/>
        <g transform="translate(15, 800)">
            <g class="title-g" transform="translate(0, 20)">
                <foreignObject x="0" y="0" width="500" height="120">
                    <div class="title" xmlns="http://www.w3.org/1999/xhtml"><span class="label-econ">${svg.econPlatName}</span>${svg.title!''}</div>
                </foreignObject>
            </g>
            <g transform="translate(0,155)">
                <g transform="translate(0,0)">
                    <text class="price-title fill-muted" x="0" y="15">券后价</text>
                    <text class="price-y" x="90" y="19">¥</text>
                    <text class="price-val" x="115" y="20"><tspan id="price">${svg.price?c}</tspan></text>
                </g>
            </g>
            <g class="price-g" transform="translate(5, 205)">
                <g class="coupon">
                    <rect class="coupon-bg" rx="5" ry="5" width="138" height="55"/>
                    <text class="coupon-quan" x="10" y="37" fill="#fff">券</text>
                    <rect class="coupon-val-bg" x="50" y="3" ry="3" rx="3" width="85" height="49"/>
                    <text class="coupon-val" x="92" y="38">¥<tspan id="couponValue">${svg.couponValue?c}</tspan></text>
                </g>
                <g transform="translate(200,20)">
                    <text class="price-title fill-muted" x="5" y="18">现价</text>
                    <text class="sale-price fill-muted" x="70" y="20">¥<tspan id="originPrice">${svg.originPrice?c}</tspan></text>
                </g>
            </g>
            <g transform="translate(535, 25)">
                <rect class="corner corner-lt" x="0" y="0"></rect>
                <rect class="corner corner-rt" x="196" y="0"></rect>
                <rect class="corner corner-lb" x="0" y="196"></rect>
                <rect class="corner corner-rb" x="196" y="196"></rect>
                <rect class="corner-cover" fill="#fff" width="220" height="220" x="3" y="3"></rect>
                <image x="20" y="20" height="185" width="185"
                       id="urlQrcode"
                       xlink:href=""
                       id="svg_7"/>
                <text font-size="22" y="233" x="35" fill="#e39334" width="152">长按识别二维码</text>
            </g>
        </g>
    </svg>
    <div >
        <input type="button" value="下载" onclick="download()"/>
        <div id="qrcode" class="qrcode-hide"></div>
    </div>
</div>
<script>
    var qrcode = new QRCode(document.getElementById("qrcode"), {
        width: 100,
        height: 100
    });
    qrcode.makeCode('ahahahah', function () {
        $("#urlQrcode").attr('xlink:href', $('#qrcode img').attr('src'))
    });

    $(document).on('notify', function (e) {
        var action = e.detail.action;
        alert(action)
        switch (action) {
            case 'setParam':
                setParams(e.detail.params)
                break;
            case 'getImageData':
                notifyImageData()
                break;
        }
    })

    function setParams(params) {
        if (params.img) {

        }
    }

    function notifyImageData() {
        $notifyWeex({
            action: 'imageData',
            data: dataImage()
        })

    }

    function dataImage() {
        return $('#canvas')[0].toDataURL('image/png');
    }

    function drawImage() {
        var svgXml = $('#svg')[0].outerHTML;

        var image = new Image();
        image.onload = function() {
            var canvas = document.getElementById('canvas');  //准备空画布
            canvas.width = 800;
            canvas.height = 1080;
            var context = canvas.getContext('2d');  //取得画布的2d绘图上下文
            context.drawImage(image, 0, 0);
        }
        image.src = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svgXml))); //给图片对象写入base64编码的svg流
    }

    function getSvgForImg() {
        var svgXml = $('#svg')[0].outerHTML;
        return 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svgXml))); //给图片对象写入base64编码的svg流
    }

    $(function() {
        drawImage()
    })

    function download() {
        var svgXml = $('#svg')[0].outerHTML;

        var image = new Image();
        image.src = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svgXml))); //给图片对象写入base64编码的svg流

        var canvas = document.createElement('canvas');  //准备空画布
        canvas.width = 800;
        canvas.height = 1080;

        var context = canvas.getContext('2d');  //取得画布的2d绘图上下文
        context.drawImage(image, 0, 0);

        var a = document.createElement('a');
        a.download = 'aaa'
        a.href = canvas.toDataURL('image/png');  //将画布内的信息导出为png图片数据
        alert(a.href);
        a.click();

    }
</script>
</body>
</html>