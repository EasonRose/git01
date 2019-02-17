<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>充值结果</title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/recharge.css">
    <link rel="stylesheet" href="/css/user_siderbar.css">
    <script type="text/javascript" src="${ctx}/js/assets/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
    <script type="text/javascript">
        var ctx="${ctx}";
    </script>

</head>
<body>
<#include "include/header.ftl">
<div class="container clear">
<#include "include/user_siderbar.ftl">
    <div class="content fr">
        <div class="recharge-title">
            <div class="recharge-title-left">
            ${result!}
            </div>
        </div>
        页面2秒后即将执行跳转,如果没有跳转，请点击<a href="${ctx}/account/index">这里</a>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        setTimeout(function () {
            window.location.href=ctx+"/account/index";
        },2000)
    })
</script>

</body>
</html>