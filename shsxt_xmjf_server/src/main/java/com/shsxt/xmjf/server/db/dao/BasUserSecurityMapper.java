package com.shsxt.xmjf.server.db.dao;

import com.shsxt.xmjf.api.po.BasUserSecurity;
import com.shsxt.xmjf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface BasUserSecurityMapper extends BaseMapper<BasUserSecurity> {
    public  BasUserSecurity queryBasUserSecurityByUserId(@Param("userId") Integer userId);


    public  BasUserSecurity queryBasUserSecurityByCardNum(@Param("cardNum") String cardNum);



}