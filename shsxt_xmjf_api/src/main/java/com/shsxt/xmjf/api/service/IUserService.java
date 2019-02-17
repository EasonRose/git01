package com.shsxt.xmjf.api.service;


import com.shsxt.xmjf.api.model.UserModel;
import com.shsxt.xmjf.api.po.BasUser;
import com.shsxt.xmjf.api.po.User;

public interface IUserService {
    public User queryUserByUserId(Integer userId);


    public BasUser queryBasUserByPhone(String phone);


    /**
     * 添加用户记录
     * @param phone
     * @param password
     * @param code
     */
    public  void saveUser(String phone,String password,String code);


    public UserModel login(String phone,String password);
}
