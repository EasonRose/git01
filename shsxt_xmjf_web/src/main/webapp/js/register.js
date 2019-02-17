$(function () {
   $(".validImg").click(function () {
      /* $.ajax({
           type:"post",
           url:"image",
           dataType:"json"
       })*/
        $(this).attr("src",ctx+"/image?time="+new Date());
   });

    $("#clickMes").click(function () {
        var phone=$("#phone").val();
        var imageCode=$("#code").val();

        if(isEmpty(phone)){
            alert("手机号不能为空!");
            return;
        }

        if(isEmpty(imageCode)){
            alert("请输入图片验证码!");
            return;
        }

        $.ajax({
            type:"post",
            url:ctx+"/sendSms",
            data:{
                phone:phone,
                type:2,
                imageCode:imageCode
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){

                    var _this=$("#clickMes");
                    var time=3;
                    var obj= setInterval(function () {
                        if(time>=2){
                            time=time-1;
                            _this.attr("disabled",true);
                            _this.val(""+time+"s");
                            _this.css("background","grey");
                        }else{
                            clearInterval(obj);
                            _this.removeAttr("disabled");
                            _this.val("获取验证码");
                            _this.css("background","#fcb22f");
                        }
                    },1000);

                }else{
                    alert(data.msg);
                    $(".validImg").attr("src",ctx+"/image?time="+new Date());
                }
            }
        });















})




    $("#register").click(function () {
        var phone=$("#phone").val();
        var code=$("#verification").val();
        var password=$("#password").val();
        if(isEmpty(phone)){
            layer.tips("请输入手机号码!","#phone");
            return;
        }
        if(isEmpty(code)){
            layer.tips("请输入手机短信验证码!","#verification");
            return;
        }

        if(isEmpty(password)){
            layer.tips("请输入密码!","#password");
            return;
        }

        $.ajax({
            type:"post",
            url:ctx+"/user/saveUser",
            data:{
                phone:phone,
                code:code,
                password:password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    layer.msg('注册成功');
                    setTimeout(function () {
                        window.location.href=ctx+"/login";
                    },2000)
                }else{
                    layer.tips(data.msg,"#register");
                }
            }
        })




    })



});
