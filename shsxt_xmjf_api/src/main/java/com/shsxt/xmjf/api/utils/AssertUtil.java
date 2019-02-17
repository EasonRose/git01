package com.shsxt.xmjf.api.utils;

import com.shsxt.xmjf.api.exceptions.BusiException;

public class AssertUtil {
    public static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new BusiException(msg);
        }
    }
}
