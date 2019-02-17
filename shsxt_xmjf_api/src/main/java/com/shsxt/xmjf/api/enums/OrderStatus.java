package com.shsxt.xmjf.api.enums;

public enum OrderStatus {
    SUCCESS(1),
    FAILED(0),
    CHECKING(2);
    private Integer type;

    OrderStatus(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
