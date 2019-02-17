package com.shsxt.xmjf.api.service;

import com.shsxt.xmjf.api.model.ResultInfo;
import com.shsxt.xmjf.api.po.BasUserSecurity;

public interface IBasUserSecurityService {
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);

    public  void checkUserIsRealName(Integer userId);

    public ResultInfo updateBasUserSecurityInfo(String realName, String cardNum, String busiPwd, String confirmBusiPwd, Integer userId);
}
