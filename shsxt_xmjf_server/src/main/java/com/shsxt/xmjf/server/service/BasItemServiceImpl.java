package com.shsxt.xmjf.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.constants.ItemStatus;
import com.shsxt.xmjf.api.po.BasItem;
import com.shsxt.xmjf.api.querys.BasItemQuery;
import com.shsxt.xmjf.api.service.IBasItemService;
import com.shsxt.xmjf.server.db.dao.BasItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BasItemServiceImpl implements IBasItemService {
    @Resource
    private BasItemMapper basItemMapper;
    @Override
    public PageInfo<Map<String, Object>> queryBasItemsByParams(BasItemQuery basItemQuery) {
        PageHelper.startPage(basItemQuery.getPageNum(),basItemQuery.getPageSize());
        List<Map<String,Object>> vals= basItemMapper.queryItemsByParams(basItemQuery);
        if(!CollectionUtils.isEmpty(vals)){
            for(Map<String,Object> map:vals){
                BigDecimal itemAccount= (BigDecimal) map.get("item_account");
                BigDecimal onGoAccount= (BigDecimal) map.get("item_ongoing_account");
                map.put("syAmount",itemAccount.subtract(onGoAccount));

                Integer itemStatus= (Integer) map.get("item_status");
                if(itemStatus== ItemStatus.WAITOPEN){
                    // 计算开放剩余时间
                    Date relaseTime= (Date) map.get("release_time");
                    Long syTime=(relaseTime.getTime()-System.currentTimeMillis())/1000;
                    if(syTime>0){
                        map.put("syTime",syTime);
                    }
                }
            }








        }

        return new PageInfo<Map<String,Object>>(vals);
    }

    @Override
    public BasItem queryBasItemByItemId(Integer itemId) {
        return basItemMapper.queryById(itemId);
    }

    public static void main(String[] args) {
        BigDecimal a=BigDecimal.valueOf(20.01);
        BigDecimal b=BigDecimal.valueOf(10.87);
        System.out.println(a.subtract(b));
    }
}
