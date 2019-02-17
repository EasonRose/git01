$(function () {
   $("#identityNext").click(function () {
       var realName=$("#realName").val();
       var cardNum=$("#card").val();
       var busiPwd=$("#_ocx_password").val();
       var confirmBusiPwd=$("#_ocx_password1").val();
       if(isEmpty(realName)){
            layer.tips("请输入真实姓名!","#realName");
            return;
       }
       if(isEmpty(cardNum)){
           layer.tips("请输入身份证号!","#card");
           return;
       }



       if(isEmpty(busiPwd)){
           layer.tips("请输入交易密码!","#_ocx_password");
           return;
       }

       if(isEmpty(confirmBusiPwd)){
           layer.tips("请输入确认交易密码!","#_ocx_password1");
           return;
       }

       $.ajax({
           type:"post",
           url:ctx+"/user/doAuth",
           data:{
                realName:realName,
               cardNum:cardNum,
               busiPwd:busiPwd,
               confirmBusiPwd:confirmBusiPwd
           },
           dataType:"json",
           success:function (data) {
               if(data.code==200){

               }else{
                   layer.tips(data.msg,"#identityNext");
               }
           }
       })





   })
});
