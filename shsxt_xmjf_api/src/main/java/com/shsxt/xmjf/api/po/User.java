package com.shsxt.xmjf.api.po;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = -4288664367878465042L;

    private Integer id;
    private String userName;
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
