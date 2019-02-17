package com.shsxt.xmjf.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.exceptions.BusiException;
import com.shsxt.xmjf.api.model.ResultInfo;
import com.shsxt.xmjf.api.po.BasUserSecurity;
import com.shsxt.xmjf.api.service.IBasUserSecurityService;
import com.shsxt.xmjf.api.utils.AssertUtil;
import com.shsxt.xmjf.api.utils.MD5;
import com.shsxt.xmjf.server.db.dao.BasUserSecurityMapper;
import com.shsxt.xmjf.server.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BasUserSecurityServiceImpl implements IBasUserSecurityService {
    @Resource
    private BasUserSecurityMapper basUserSecurityMapper;
    @Override
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId) {
        return basUserSecurityMapper.queryBasUserSecurityByUserId(userId);
    }

    @Override
    public void checkUserIsRealName(Integer userId) {
        BasUserSecurity basUserSecurity= queryBasUserSecurityByUserId(userId);
        AssertUtil.isTrue(basUserSecurity.getRealnameStatus()!=1,"用户未实名!");
    }

    @Override
    public ResultInfo updateBasUserSecurityInfo(String realName, String cardNum, String busiPwd, String confirmBusiPwd, Integer userId) {
        ResultInfo resultInfo=new ResultInfo();
        try {
            checkParams(realName,cardNum,busiPwd,confirmBusiPwd,userId);
            /**
             * 调用第三方api  接口实现认证
             */
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + XmjfConstant.AUTH_APP_CODE);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("idCard", cardNum);
            querys.put("name",realName);
            HttpResponse response = HttpUtils.doGet(XmjfConstant.AUTH_HOST, XmjfConstant.AUTH_PATH, XmjfConstant.AUTH_METHOD, headers, querys);
            String result= null;
            result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject= JSON.parseObject(result);
            String status= (String) jsonObject.get("status");
            AssertUtil.isTrue(!(status.equals("01")),jsonObject.get("msg").toString());


            /**
             * 实名信息更新
             */
            BasUserSecurity basUserSecurity=queryBasUserSecurityByUserId(userId);
            basUserSecurity.setRealnameStatus(1);
            basUserSecurity.setRealname(realName);
            basUserSecurity.setIdentifyCard(cardNum);
            basUserSecurity.setPaymentPassword(MD5.toMD5(busiPwd));
            basUserSecurity.setVerifyTime(new Date());
            AssertUtil.isTrue(basUserSecurityMapper.update(basUserSecurity)<1,XmjfConstant.OPS_FAILED_MSG);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(XmjfConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("认证异常!");
            if(e instanceof BusiException){
                BusiException busiException= (BusiException) e;
                resultInfo.setCode(busiException.getCode());
                resultInfo.setMsg(busiException.getMsg());
            }



        }
        return resultInfo;
    }

    private void checkParams(String realName, String cardNum, String busiPwd, String confirmBusiPwd, Integer userId) {
        BasUserSecurity basUserSecurity= queryBasUserSecurityByUserId(userId);
        AssertUtil.isTrue(basUserSecurity.getRealnameStatus()==1,"用户已实名!");
        AssertUtil.isTrue(StringUtils.isBlank(realName),"请输入真实名称!");
        AssertUtil.isTrue(StringUtils.isBlank(cardNum),"请输入身份证号码，号码长度18位!");
        AssertUtil.isTrue(cardNum.length()!=18,"身份证长度为18位!");
        AssertUtil.isTrue(null !=basUserSecurityMapper.queryBasUserSecurityByCardNum(cardNum),"该身份证已被暂用!");
        AssertUtil.isTrue(StringUtils.isBlank(busiPwd)||StringUtils.isBlank(confirmBusiPwd),"交易密码不能为空!");
        AssertUtil.isTrue(!(busiPwd.equals(confirmBusiPwd)),"交易密码不一致!");
    }
}
