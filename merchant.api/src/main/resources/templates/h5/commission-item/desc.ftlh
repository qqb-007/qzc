<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>详情页</title>
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

        }
        img {
            max-width: 100% !important;
        }
    </style>
</head>
<body>
${desc}
<script>
    $('img').each(function() {
        $notifyWeex({
            action: 'img',
            src: $(this).attr('src'),
            height: $(this)[0].naturalHeight,
            width: $(this)[0].naturalWidth,
        })
    })
</script>
</body>
</html>