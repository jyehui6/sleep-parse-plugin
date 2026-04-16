package com.leanstar.sleepparse.exception;

public class BusinessException extends RuntimeException {

    private Integer code;

    private String msg;

    private String desc;

    public BusinessException(Integer code, String msg, String desc){
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public BusinessException(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg){
        this.msg = msg;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
