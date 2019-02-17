package com.shsxt.xmjf.api.model;

import com.shsxt.xmjf.api.constants.XmjfConstant;

import java.io.Serializable;

public class ResultInfo<T>  implements Serializable{
    private static final long serialVersionUID = -366131481271535063L;
    private Integer code= XmjfConstant.OPS_SUCCESS_CODE;
    private String msg=XmjfConstant.OPS_SUCCESS_MSG;
    private T result;


    public ResultInfo() {
    }

    public ResultInfo(Integer code) {
        this.code = code;
    }

    public ResultInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
