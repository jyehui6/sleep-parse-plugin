package com.leanstar.sleepparse.domain;

import com.leanstar.sleepparse.exception.NonFormatBusinessException;
import com.leanstar.sleepparse.iterator.Iterator;
import com.leanstar.sleepparse.iterator.MyBase64Iterator;
import com.leanstar.sleepparse.util.MyBase64Util;

/**
 * Base64处理的领域模型
 * 主要提供了迭代器、解码功能
 */
public class MyBase64 {

    private String sourceStr = null;

    private String key = null;

    public MyBase64(String source, String key){
        sourceStr = source;
        this.key = key;
    }

    // 解码
    public String decode(){
        return MyBase64Util.decode(sourceStr, 0, sourceStr.length());
    }

    // 解码某个区间字符串
    public String decode(int startIndex, int endIndex){
        return MyBase64Util.decode(sourceStr, startIndex, endIndex);
    }

    public int length(){
        return sourceStr.length();
    }

    // 获取当前对象的迭代器
    public Iterator iterator(){
        if(this.sourceStr == null || this.sourceStr.length() % 4 != 0){
            throw new NonFormatBusinessException("MyBase64源数据不符合格式！", this.sourceStr);
        }
        return new MyBase64Iterator(this);
    }

    public String getSourceStr(){
        return sourceStr;
    }

    public String getKey(){return key;}

}
