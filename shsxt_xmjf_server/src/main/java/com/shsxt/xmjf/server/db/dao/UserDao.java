package com.shsxt.xmjf.server.db.dao;

import com.shsxt.xmjf.api.po.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    public User queryUserByUserId(@Param("userId") Integer userId);
}
