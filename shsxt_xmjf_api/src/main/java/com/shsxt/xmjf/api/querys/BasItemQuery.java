package com.shsxt.xmjf.api.querys;

import java.io.Serializable;

public class BasItemQuery implements Serializable{
    private static final long serialVersionUID = -7826088859401808602L;
    private Integer itemType;
    private Integer itemCycle;// 1-0,30  2-30,90  3-90
    private Integer isHistory;// 是否为历史项目 1-历史项目  0-可投项目
    private Integer pageNum=1;
    private Integer pageSize=10;

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getItemCycle() {
        return itemCycle;
    }

    public void setItemCycle(Integer itemCycle) {
        this.itemCycle = itemCycle;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
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
