package com.shsxt.xmjf.api.service;

import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.querys.BusItemInvestQuery;

import java.math.BigDecimal;
import java.util.Map;

public interface IBusItemInvestService {
    public PageInfo<Map<String,Object>> queryInvestsByParams(BusItemInvestQuery busItemInvestQuery);


    /**
     *  用户投资
     * @param itemId  项目id
     * @param userId  投资用户
     *@param busiPwd  交易密码
     * @param amount   投资金额
     * @param isUseExpGold  是否使用体验金
     */
    public  void addBusItemInvest(Integer itemId, Integer userId,String busiPwd,BigDecimal amount,Integer isUseExpGold);
}
