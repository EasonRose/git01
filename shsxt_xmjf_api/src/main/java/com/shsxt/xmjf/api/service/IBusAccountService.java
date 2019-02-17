package com.shsxt.xmjf.api.service;

import com.shsxt.xmjf.api.po.BusAccount;

public interface IBusAccountService {
    public BusAccount queryBusAccountByUserId(Integer userId);
}
