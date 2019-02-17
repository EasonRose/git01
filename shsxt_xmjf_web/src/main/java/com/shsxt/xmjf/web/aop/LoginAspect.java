package com.shsxt.xmjf.web.aop;

import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.exceptions.NoLoginException;
import com.shsxt.xmjf.api.model.UserModel;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class LoginAspect {

    @Autowired
    private HttpSession session;

    @Pointcut("@annotation(com.shsxt.xmjf.web.annotations.RequireLogin)")
    public  void cut(){}


    @Before(value = "cut()")
    public  void before(){
       UserModel userModel= (UserModel) session.getAttribute(XmjfConstant.SESSION_USER_INFO);
       if(null == userModel){
           throw  new NoLoginException();
       }
    }
}
