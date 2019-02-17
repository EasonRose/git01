package com.shsxt.xmjf.api.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private static final long serialVersionUID = 7766287363830533697L;
    private Integer userId;
    private String phone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
