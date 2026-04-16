package com.leanstar.sleepparse.iterator;

import com.leanstar.sleepparse.domain.MyBase64;

/**
 * MyBase64的迭代器实现，
 * 以四个字符为一组进行处理，提取出三个字节数据，
 * next()每次返回一个字节数据
 */
public class MyBase64Iterator implements Iterator<String> {

    private MyBase64 base64;

    private int currIndex = 0;

    private String availableBinaryStrs = null;

    public MyBase64Iterator(MyBase64 base64){
        this.base64 = base64;
    }

    public boolean hasNext(){
        return availableBinaryStrs != null || currIndex < base64.length();
    }

    public String next(){
        String tempStr;
        if(availableBinaryStrs != null){
            if(availableBinaryStrs.length() == 8){
                tempStr = availableBinaryStrs;
                availableBinaryStrs = null;
            }else{
                tempStr = availableBinaryStrs.substring(0, 8);
                availableBinaryStrs = availableBinaryStrs.substring(8);
            }
        }else{
            availableBinaryStrs = base64.decode(currIndex, currIndex + 4);
            currIndex += 4;
            tempStr = availableBinaryStrs.substring(0, 8);
            availableBinaryStrs = availableBinaryStrs.length() == 8 ? null : availableBinaryStrs.substring(8);
        }
        return tempStr;
    }

}
