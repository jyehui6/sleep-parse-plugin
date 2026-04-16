package com.leanstar.sleepparse.exception;

public class NonFormatBusinessException extends BusinessException {

    private static final int UN_FORMAT_CODE = 5501;

    public NonFormatBusinessException(String msg, String desc){
        super(UN_FORMAT_CODE, msg, desc);
    }

    public NonFormatBusinessException(String msg){
        super(UN_FORMAT_CODE, msg);
    }

}
