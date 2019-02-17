package com.shsxt.xmjf.web;

import com.alibaba.fastjson.JSON;
import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.exceptions.BusiException;
import com.shsxt.xmjf.api.exceptions.NoLoginException;
import com.shsxt.xmjf.api.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv=getDefaultModelAndView(request,ex);
        if(ex instanceof NoLoginException){
            mv.setViewName("redirect:/login");
        }


        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            Method method= handlerMethod.getMethod();
            ResponseBody responseBody= method.getAnnotation(ResponseBody.class);
            if(null!=responseBody){
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(XmjfConstant.OPS_FAILED_CODE);
                resultInfo.setMsg(XmjfConstant.OPS_FAILED_MSG);
                if(ex instanceof BusiException){
                    BusiException pe= (BusiException) ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter pw =null;
                try {
                    pw=response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(null!=pw){
                        pw.close();
                    }
                }
                return null;
            }else{
                if(ex instanceof  BusiException){
                    BusiException pe= (BusiException) ex;
                    mv.addObject("errorMsg",pe.getMsg());
                    mv.addObject("errorCode",pe.getCode());
                }
                return mv;
            }
        }else{
            return mv;
        }

    }


    private ModelAndView getDefaultModelAndView(HttpServletRequest request, Exception ex) {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("error");
        mv.addObject("ctx",request.getContextPath());
        mv.addObject("errorMsg", XmjfConstant.OPS_FAILED_MSG);
        mv.addObject("errorCode",XmjfConstant.OPS_FAILED_CODE);
        mv.addObject("uri",request.getRequestURI());
        return mv;
    }
}
