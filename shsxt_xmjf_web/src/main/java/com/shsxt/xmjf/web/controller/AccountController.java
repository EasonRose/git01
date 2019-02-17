package com.shsxt.xmjf.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.shsxt.xmjf.api.constants.AlipayConfig;
import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.model.ResultInfo;
import com.shsxt.xmjf.api.model.UserModel;
import com.shsxt.xmjf.api.po.BasUserSecurity;
import com.shsxt.xmjf.api.service.IBasUserSecurityService;
import com.shsxt.xmjf.api.service.IBusAccountRechargeService;
import com.shsxt.xmjf.api.utils.AssertUtil;
import com.shsxt.xmjf.web.annotations.RequireLogin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("account")
public class AccountController {

    @Resource
    private IBasUserSecurityService basUserSecurityService;

    @Resource
    private IBusAccountRechargeService busAccountRechargeService;

    @RequestMapping("index")
    @RequireLogin
    public String index(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());
        return "account";
    }


    @RequestMapping("auth")
    @RequireLogin
    public String auth(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());
        return "auth";
    }

    @RequestMapping("setting")
    @RequireLogin
    public String setting(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());
        UserModel userModel = (UserModel) request.getSession().getAttribute(XmjfConstant.SESSION_USER_INFO);
        BasUserSecurity basUserSecurity = basUserSecurityService.queryBasUserSecurityByUserId(userModel.getUserId());
        request.setAttribute("security", basUserSecurity);
        return "setting";
    }


    @RequireLogin
    @RequestMapping("recharge")
    public String recharge(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());
        return "recharge";
    }


    /*@RequireLogin
    @RequestMapping("doRecharge")
    public String doRecharge(BigDecimal amount, String imageCode, String busiPwd,HttpServletRequest request, HttpSession session) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no ="xmjf_"+System.currentTimeMillis()+"";
        //订单名称，必填
        String subject = "用户充值";
        //商品描述，可空
        String body = "用户充值";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            request.setAttribute("result",result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "pay";
    }*/


    @RequireLogin
    @RequestMapping("doRecharge")
    public String doRecharge(BigDecimal amount, String imageCode, String busiPwd, HttpServletRequest request, HttpSession session) {
        request.setAttribute("ctx", request.getContextPath());
        if (StringUtils.isBlank(imageCode)) {
            request.setAttribute("msg", "图片验证码不能为空!");
            return "recharge";
        }
        String sessionImageCode = (String) session.getAttribute(XmjfConstant.IMAGE);
        if (StringUtils.isBlank(sessionImageCode)) {
            request.setAttribute("msg", "图片验证码已失效,请刷新页面!");
            return "recharge";
        }
        if (!(imageCode.equals(sessionImageCode))) {
            request.setAttribute("msg", "图片验证码不正确!");
            return "recharge";
        }
        session.removeAttribute(XmjfConstant.IMAGE);
        UserModel userModel = (UserModel) session.getAttribute(XmjfConstant.SESSION_USER_INFO);
        ResultInfo<String> resultInfo = busAccountRechargeService.addBusAccountRecharge(userModel.getUserId(), amount, busiPwd);
        if (resultInfo.getCode().equals(XmjfConstant.OPS_SUCCESS_CODE)) {
            request.setAttribute("result", resultInfo.getResult());
            return "pay";
        } else {
            request.setAttribute("msg", resultInfo.getMsg());
            return "recharge";
        }
    }


    /**
     * 支付回调表结构
     * bus_account_recharge
     * bus_account
     * bus_account_log
     * bus_user_stat
     * bus_user_integral
     * bus_integral_log
     */

    @RequestMapping("returnCallBack")
    public String  returnCallBack(
            @RequestParam(name = "out_trade_no") String orderNo,
            @RequestParam(name = "total_amount") BigDecimal totalAmount,
            @RequestParam(name = "app_id") String appId,
            @RequestParam(name = "seller_id") String sellerId,
            @RequestParam(name = "trade_no") String tradeNo,
            HttpServletRequest request) {
        System.out.println("同步通知。。。");
        try {
            if (checkSign(request)) {
                busAccountRechargeService.updateBusAccountRechargeInfo(orderNo,totalAmount,appId,sellerId,tradeNo);
            }
            request.setAttribute("result","账户充值成功!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result",XmjfConstant.PAY_FAILED_MSG);
        }
        request.setAttribute("ctx",request.getContextPath());
        return "pay_result";
    }

    @RequestMapping("notifyCallBack")
    public String notifyCallBack(
            @RequestParam(name = "out_trade_no") String orderNo,
            @RequestParam(name = "total_amount") BigDecimal totalAmount,
            @RequestParam(name = "app_id") String appId,
            @RequestParam(name = "seller_id") String sellerId,
            @RequestParam(name = "trade_no") String tradeNo,
            @RequestParam(name = "trade_status") String tradeStatus,
            HttpServletRequest request) {
        System.out.println("异步通知。。。");
        try {
            if (!(checkSign(request))) {
                request.setAttribute("result","fail");
                return "notify_result";
            }

            if(!(tradeStatus.equals(XmjfConstant.TRADE_RESULT))){
                request.setAttribute("result","fail");
                return "notify_result";
            }
            //  更新订单信息
            busAccountRechargeService.updateBusAccountRechargeInfo(orderNo,totalAmount,appId,sellerId,tradeNo);
            request.setAttribute("result","success");
            return "notify_result";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result","fail");
            return "notify_result";
        }
    }


    public Boolean checkSign(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
    }

}
