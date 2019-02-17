package com.shsxt.xmjf.server.db.dao;


import com.shsxt.xmjf.api.po.BasItem;
import com.shsxt.xmjf.api.querys.BasItemQuery;
import com.shsxt.xmjf.server.base.BaseMapper;

import java.util.List;
import java.util.Map;

public interface BasItemMapper extends BaseMapper<BasItem>{

    public List<Map<String,Object>>  queryItemsByParams(BasItemQuery basItemQuery);


}