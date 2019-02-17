package com.shsxt.xmjf.server.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public  static  String getReplaceStr(String str){
        StringBuffer stringBuffer=new StringBuffer();
        if(StringUtils.isNotBlank(str)){
            for(int i=0;i<str.length();i++){
                stringBuffer.append("*");
            }
        }
        return stringBuffer.toString();
    }

}
