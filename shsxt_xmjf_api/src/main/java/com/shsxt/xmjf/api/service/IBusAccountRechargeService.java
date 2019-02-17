package com.shsxt.xmjf.api.service;

import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.model.ResultInfo;
import com.shsxt.xmjf.api.querys.BusAccountRechargeQuery;

import java.math.BigDecimal;
import java.util.Map;

public interface IBusAccountRechargeService {
    public ResultInfo<String> addBusAccountRecharge(Integer userId, BigDecimal amount, String busiPwd);

    public  void updateBusAccountRechargeInfo(String orderNo,BigDecimal amount,String appId,String sellerId,String busiNo);


    public PageInfo<Map<String,Object>>  queryBusAccountRechargesByUserId(BusAccountRechargeQuery busAccountRechargeQuery);

}
