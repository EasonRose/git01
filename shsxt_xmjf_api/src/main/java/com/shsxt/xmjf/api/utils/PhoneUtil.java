package com.shsxt.xmjf.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
    public  static  Boolean checkPhone(String phone){
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[0-9])|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
       return  m.matches();
    }
}
