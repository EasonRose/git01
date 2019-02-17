package com.shsxt.xmjf.api.querys;

import java.io.Serializable;

public class BusAccountRechargeQuery implements Serializable {
    private static final long serialVersionUID = 7346066413853557661L;
    private Integer userId;
    private Integer pageNum=1;
    private Integer pageSize=10;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
