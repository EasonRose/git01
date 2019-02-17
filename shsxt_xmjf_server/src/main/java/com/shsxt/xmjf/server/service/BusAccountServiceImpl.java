package com.shsxt.xmjf.server.service;

import com.shsxt.xmjf.api.po.BusAccount;
import com.shsxt.xmjf.api.service.IBusAccountService;
import com.shsxt.xmjf.server.db.dao.BusAccountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BusAccountServiceImpl implements IBusAccountService {
    @Resource
    private BusAccountMapper busAccountMapper;
    @Override
    public BusAccount queryBusAccountByUserId(Integer userId) {
        return busAccountMapper.queryBusAccountByUserId(userId);
    }
}
