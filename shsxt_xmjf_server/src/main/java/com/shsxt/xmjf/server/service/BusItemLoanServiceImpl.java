package com.shsxt.xmjf.server.service;

import com.shsxt.xmjf.api.po.BusItemLoan;
import com.shsxt.xmjf.api.service.IBusItemLoanService;
import com.shsxt.xmjf.server.db.dao.BusItemLoanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BusItemLoanServiceImpl implements IBusItemLoanService {
    @Resource
    private BusItemLoanMapper busItemLoanMapper;
    @Override
    public BusItemLoan queryBusItemLoanByItemId(Integer itemId) {
        return busItemLoanMapper.queryBusItemLoanByItemId(itemId);
    }
}
