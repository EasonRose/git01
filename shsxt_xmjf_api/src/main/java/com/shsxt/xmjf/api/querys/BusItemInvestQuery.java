package com.shsxt.xmjf.api.querys;

import java.io.Serializable;

public class BusItemInvestQuery implements Serializable {
    private static final long serialVersionUID = 6387987681472767198L;
    private Integer itemId;
    private Integer pageNum=1;
    private Integer pageSize=10;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
