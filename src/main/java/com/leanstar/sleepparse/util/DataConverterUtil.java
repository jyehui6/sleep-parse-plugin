package com.leanstar.sleepparse.util;

import java.math.BigInteger;

public class DataConverterUtil {

    // 将字符串形式的二进制转成字符串形式的整数
    public static String binaryToStr(String binarySource) {
        BigInteger bi = new BigInteger(binarySource, 2); // 转换为BigInteger类型
        return String.valueOf(bi); //转换成十进制字符串形式
    }

    // 将字符串形式的二进制转成整型形式的整数
    public static int binaryToInt(String binarySource) {
        BigInteger bi = new BigInteger(binarySource, 2); // 转换为BigInteger类型
        return bi.intValue(); //转换成十进制形式
    }

}
