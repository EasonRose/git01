package com.shsxt.xmjf.web.controller;

import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.model.ResultInfo;
import com.shsxt.xmjf.api.model.UserModel;
import com.shsxt.xmjf.api.querys.BusItemInvestQuery;
import com.shsxt.xmjf.api.service.IBusItemInvestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("invest")
public class BusItemInvestController {
    @Resource
    private IBusItemInvestService busItemInvestService;


    @RequestMapping("list")
    @ResponseBody
    public PageInfo<Map<String,Object>> queryInvestsByParams(BusItemInvestQuery busItemInvestQuery){
        return busItemInvestService.queryInvestsByParams(busItemInvestQuery);
    }


    @RequestMapping("doInvest")
    @ResponseBody
    public ResultInfo doInvest(Integer itemId, BigDecimal amount, String busiPwd, @RequestParam(defaultValue = "0") Integer isUseExpGold, HttpSession session){
        UserModel userModel= (UserModel) session.getAttribute(XmjfConstant.SESSION_USER_INFO);
        busItemInvestService.addBusItemInvest(itemId,userModel.getUserId(),busiPwd,amount,isUseExpGold);
        return new ResultInfo();
    }
}
