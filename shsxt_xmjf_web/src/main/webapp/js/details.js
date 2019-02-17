$(function () {
    $("#rate").radialIndicator({
        barColor: 'orange',
        barWidth: 5,
        roundCorner : true,
        percentage: true,
        radius:30
    });

    var radialObj = $("#rate").data('radialIndicator');
    radialObj.animate($("#rate").attr("data-val"));



    $("#tabs div").click(function () {
        $("#tabs div").removeClass("tab_active");
        $(this).addClass("tab_active");
        var show= $("#contents .tab_content").eq($(this).index());
        show.show();
        $("#contents .tab_content").not(show).hide();
        if($(this).index()==2){
            /**
             * 查询投资记录
             */
            loadInvestListData();
        }


    })


});



function picTab(ele,allNum,currentNum) {
    var ele=$('#imgLarge');
    var allNum=$('#slider').find('li');
    var  currentNum=0;
    allNum.click(function () {
        currentNum = $(this).index();
        ele.show(300);
        var ImgSrc = $(this).attr('data-url');

        ele.css('background-image', 'url('+ImgSrc+')');
    });
    $('.close').click(function () {
        ele.hide(300);
    });
    $('.left').click(function () {
        currentNum--;
        if (currentNum < 0) {
            currentNum = allNum.length - 1;
        }
        var ImgSrc = allNum.eq(currentNum).attr('data-url');
        ele.css('background-image', 'url('+ImgSrc+')');
    });

    $('.right').click(function () {
        currentNum++;
        if (currentNum > allNum.length - 1) {
            currentNum = 0;
        }
        var ImgSrc = allNum.eq(currentNum).attr('data-url');
        ele.css('background-image', 'url('+ImgSrc+')');
    })
}


function loadInvestListData(pageNum) {
    var p=1;
    if(!isEmpty(pageNum)){
        p=pageNum;
    }
    var itemId=$("#itemId").val();
    $.ajax({
        type:"post",
        url:ctx+"/invest/list",
        data:{
            itemId:itemId,
            pageNum:p
        },
        dataType:"json",
        success:function (data) {
            var pageArray=data.navigatepageNums;
            var list=data.list;
            if(data.total>0){
                /**
                 * tr
                 *   td
                 *     手机号   金额   时间
                 */
                initTrHtml(list);
                initPageHtml(pageArray,data.pageNum);
            }else{
                /*alert("暂无投资记录!");*/
                $("#pages").html("<img style='margin-left: -70px;padding:40px;' " +                     "src='/img/zanwushuju.png'>");
                $("#recordList").html("");
            }

        }
    })

}


function initTrHtml(list) {
    if(list.length>0){
        var trs="";
        for(var i=0;i<list.length;i++){
            var temp=list[i];
            trs=trs+"<tr><td>"+temp.phone+"</td><td>"+temp.amount+"</td><td>"+temp.time+"</td></tr>";
        }
        $("#recordList").html(trs);
    }
}


function initPageHtml(pageArray,currentPage) {
    /**
     *   <li class="active"><a title="第一页" >1</a></li>
     <li><a title="第二页">2</a></li>
     <li><a title="第三页">3</a></li>
     */
    var lis="";
    for(var j=0;j<pageArray.length;j++){
        var p=pageArray[j];
        var href="javascript:toPageData("+p+")";
        if(currentPage==p){
            lis=lis+"<li class='active'><a href='"+href+"' title='第"+p+"页' >"+p+"</a></li>";
        }else{
            lis=lis+"<li ><a href='"+href+"' title='第"+p+"页' >"+p+"</a></li>";
        }
    }
    $("#pages").html(lis);
}



function toPageData(pageNum) {
    loadInvestListData(pageNum);
}


function toRecharge() {
    $.ajax({
        type:"post",
        url:ctx+"/user/checkUserIsRealName",
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                window.location.href=ctx+"/account/recharge";
            }else{
                layer.confirm("尊敬的用户,您还未进行实名认证操作,是否执行实名认证?", {
                    btn: ['前往认证','稍后认证'] //按钮
                }, function(){
                    window.location.href=ctx+"/account/auth";
                }, function(){

                });
            }
        }
    })
}


function doInvest() {
    var amount=$("#usableMoney").val();
    var itemId=$("#itemId").val();
    var singleMinInvestment=$("#minInvestMoney").attr("data-value");
    var singleMaxInvestment=$("#maxInvestMoney").attr("data-value");
    var usableMoney=parseFloat($("#ye").attr("data-value"));
    if(isEmpty(amount)){
        layer.tips("请输入投资金额!","#usableMoney");
        return;
    }
    amount=parseFloat(amount);
    if(amount>usableMoney){
        layer.tips("投资金额不能大于账户可用金额!","#usableMoney");
        return;
    }

    var syAmount=$("#syAmount").attr("data-value");
    if(amount>syAmount){
        layer.tips("投资金额不能大于项目剩余金额!","#usableMoney");
        return;
    }


    if(!isEmpty(singleMinInvestment)){
        singleMinInvestment=parseFloat(singleMinInvestment);
        if(syAmount<singleMinInvestment){
            layer.tips("项目即将进行截标,暂不支持投资!","#doInvest");
            return;
        }
        if(amount<singleMinInvestment){
            layer.tips("投资金额不能小于最低投资!","#usableMoney");
            return;
        }
    }
    if(!isEmpty(singleMaxInvestment)){
        singleMaxInvestment=parseFloat(singleMaxInvestment);
        if(amount>singleMaxInvestment){
            layer.tips("投资金额不能大于最高投资!","#usableMoney");
            return;
        }
    }


    layer.confirm('是否使用体验金?', {
        btn: ['立即使用','不使用'] //按钮
    }, function(abc){
        layer.close(abc);
        layer.prompt({title: '请输入交易密码', formType: 1}, function(pass, index01){
            layer.close(index01);
            invest(itemId,amount,1,pass);
        });
    }, function(){
        layer.prompt({title: '请输入交易密码', formType: 1}, function(pass, index02){
            layer.close(index02);
            invest(itemId,amount,0,pass);
        });
    });




function invest(itemId,amount,isUseExpGold,pass) {
    $.ajax({
        type:"post",
        url:ctx+"/invest/doInvest",
        data:{
            itemId:itemId,
            amount:amount,
            busiPwd:pass,
            isUseExpGold:isUseExpGold
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                layer.msg("投资成功");
                setTimeout(function () {
                    window.location.href=ctx+"/account/index";
                },2000);

            }else{
                layer.tips(data.msg,"#doInvest");
            }
        }
    })
}











};
