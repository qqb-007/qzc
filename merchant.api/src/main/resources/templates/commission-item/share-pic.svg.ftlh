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
    <image xlink:href="${svg.picUrl!''}" id="mainImg" x="0"
           y="0" class="main-pic" width="800" height="800"/>
    <g transform="translate(15, 800)">
        <g class="title-g" transform="translate(0, 20)">
            <foreignObject x="0" y="0" width="500" height="120">
                <div class="title" xmlns="http://www.w3.org/1999/xhtml"><span
                        class="label-econ">${svg.econPlatName}</span>${svg.title!''}</div>
            </foreignObject>
        </g>
        <g transform="translate(0,160)">
            <g transform="translate(0,0)">
                <text class="price-title fill-muted" x="0" y="15">券后价</text>
                <text class="price-y" x="90" y="19">¥</text>
                <text class="price-val" x="115" y="20">
                    <tspan id="price">${svg.price?string("#.##")}</tspan>
                </text>
            </g>
        </g>
        <g class="price-g" transform="translate(5, 205)">
            <g class="coupon">
                <rect class="coupon-bg" rx="5" ry="5" width="138" height="55"/>
                <text class="coupon-quan" x="10" y="37" fill="#fff">券</text>
                <rect class="coupon-val-bg" x="50" y="3" ry="3" rx="3" width="85" height="49"/>
                <text class="coupon-val" x="92" y="38">¥
                    <tspan id="couponValue">${svg.couponValue?string("#.##")}</tspan>
                </text>
            </g>
            <g transform="translate(200,20)">
                <text class="price-title fill-muted" x="5" y="18">现价</text>
                <text class="sale-price fill-muted" x="70" y="20">¥
                    <tspan id="originPrice">${svg.originPrice?string("#.##")}</tspan>
                </text>
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
                   xlink:href="https://gqrcode.alicdn.com/img?type=hv&text=${url}&h=300&w=300"
                   id="svg_7"/>
            <text font-size="22" y="233" x="35" fill="#e39334" width="152">长按识别二维码</text>
        </g>
    </g>
</svg>