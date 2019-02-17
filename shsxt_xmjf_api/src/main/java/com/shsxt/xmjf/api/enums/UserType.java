package com.shsxt.xmjf.api.enums;

public enum  UserType {
    INVEST(1),// 投资
    LOAN(2),    // 贷款用户
    COMPANY(3);  // 借款企业

    private Integer type;

    UserType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
