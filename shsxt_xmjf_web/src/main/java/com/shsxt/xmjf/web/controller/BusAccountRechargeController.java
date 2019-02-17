package com.shsxt.xmjf.web.controller;

import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.model.UserModel;
import com.shsxt.xmjf.api.querys.BusAccountRechargeQuery;
import com.shsxt.xmjf.api.service.IBusAccountRechargeService;
import com.shsxt.xmjf.web.annotations.RequireLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("recharge")
public class BusAccountRechargeController {

    @Resource
    private IBusAccountRechargeService busAccountRechargeService;

    @RequestMapping("index")
    @RequireLogin
    public String index(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "recharge_record";
    }


    @RequestMapping("list")
    @ResponseBody
    public PageInfo<Map<String,Object>> queryBusAccountRechargesByUserId(BusAccountRechargeQuery busAccountRechargeQuery, HttpSession session){
        UserModel userModel= (UserModel) session.getAttribute(XmjfConstant.SESSION_USER_INFO);
        busAccountRechargeQuery.setUserId(userModel.getUserId());
        return busAccountRechargeService.queryBusAccountRechargesByUserId(busAccountRechargeQuery);
    }
}
