package com.shsxt.xmjf.api.enums;

public enum RechargeType {
    APP(1),
    ADMIN(2),
    PC(3),
    WE_CHAT(4);

    private Integer type;

    RechargeType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
