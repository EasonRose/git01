package com.shsxt.xmjf.server.service;

import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.querys.BasItemQuery;
import com.shsxt.xmjf.api.service.IBasItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestBasItemService {
    @Resource
    private IBasItemService basItemService;

    @Test
    public  void test(){
        BasItemQuery basItemQuery=new BasItemQuery();
        basItemQuery.setIsHistory(0);
        basItemQuery.setItemCycle(2);
        basItemQuery.setItemType(3);
        PageInfo<Map<String,Object>> pageInfo=basItemService.queryBasItemsByParams(basItemQuery);
        System.out.println(pageInfo.getTotal()+"--"+pageInfo.getPages());
        List<Map<String,Object>> vals=pageInfo.getList();
        if(!(CollectionUtils.isEmpty(vals))){
            for(Map<String,Object> map:vals){
                for(Map.Entry<String,Object> entry:map.entrySet()){
                    System.out.println("key:"+entry.getKey()+":val:"+entry.getValue());
                }
                System.out.println("------------------------");
            }
        }
    }

}
