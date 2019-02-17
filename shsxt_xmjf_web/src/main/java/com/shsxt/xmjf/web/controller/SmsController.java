package com.shsxt.xmjf.web.controller;

import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.exceptions.BusiException;
import com.shsxt.xmjf.api.model.ResultInfo;
import com.shsxt.xmjf.api.service.ISmsService;
import com.shsxt.xmjf.api.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class SmsController {
    @Resource
    private ISmsService smsService;


    @RequestMapping("sendSms")
    @ResponseBody
    public ResultInfo sendSms(String phone, Integer type, String imageCode, HttpSession session){
        ResultInfo resultInfo=new ResultInfo();
        try {
            AssertUtil.isTrue(StringUtils.isBlank(imageCode),"验证码不能为空!");
            String sessionImageCode= (String) session.getAttribute(XmjfConstant.IMAGE);
            AssertUtil.isTrue(StringUtils.isBlank(sessionImageCode),"页面失效，请刷新页面!");
            AssertUtil.isTrue(!(sessionImageCode.equals(imageCode)),"图片验证码不正确!");
            session.removeAttribute(XmjfConstant.IMAGE);
            smsService.sendSms(phone,type);
            resultInfo.setMsg("短信发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(XmjfConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(XmjfConstant.OPS_FAILED_MSG);
            if(e instanceof BusiException){
                BusiException be= (BusiException) e;
                resultInfo.setCode(be.getCode());
                resultInfo.setMsg(be.getMsg());
            }
        }
        return resultInfo;
    }




}
