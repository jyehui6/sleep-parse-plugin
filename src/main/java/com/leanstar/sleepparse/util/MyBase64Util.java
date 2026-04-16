package com.leanstar.sleepparse.util;

import java.io.UnsupportedEncodingException;

/**
 * 自定义Base64的编解码工具类
 */
public class MyBase64Util {

    /**
     * 编码
     */
    public static String encode(String s, String charset) throws UnsupportedEncodingException {
        byte[] arr = s.getBytes(charset);
        int a = arr.length % 3;
        int b = arr.length / 3;
        StringBuilder base64Str = new StringBuilder();
        for(int i = 0; i < 3 * b; i = i + 3){
            String bits1 = byteToBinaryString(arr[i]).substring(0, 6);
            String bits2 = byteToBinaryString(arr[i]).substring(6, 8) + byteToBinaryString(arr[i + 1]).substring(0, 4);
            String bits3 = byteToBinaryString(arr[i + 1]).substring(4, 8) + byteToBinaryString(arr[i + 2]).substring(0 ,2);
            String bits4 = byteToBinaryString(arr[i + 2]).substring(2, 8);
            int w = compute(bits1);
            int x = compute(bits2);
            int y = compute(bits3);
            int z = compute(bits4);
            base64Str.append(convert(w)).append(convert(x)).append(convert(y)).append(convert(z));
        }
        if(a == 1){
            String bits1 = byteToBinaryString(arr[3 * b]).substring(0, 6);
            String bits2 = byteToBinaryString(arr[3 * b]).substring(6, 8) + "0000";
            int w = compute(bits1);
            int x = compute(bits2);
            base64Str.append(convert(w)).append(convert(x)).append('=').append('=');
        }else if(a == 2){
            String bits1 = byteToBinaryString(arr[3 * b]).substring(0, 6);
            String bits2 = byteToBinaryString(arr[3 * b]).substring(6, 8) + byteToBinaryString(arr[3 * b + 1]).substring(0, 4);
            String bits3 = byteToBinaryString(arr[3 * b + 1]).substring(4, 8) + "00";
            int w = compute(bits1);
            int x = compute(bits2);
            int y = compute(bits3);
            base64Str.append(convert(w)).append(convert(x)).append(y).append('=');
        }
        return base64Str.toString();
    }

    // base64提供的领域模型 — 定义 重载解析方法
    // 解析所有的，
    // 解析某一组的。

    // 迭代器：
    // 维护一个下标，额外维护一个数组，存储查出来的超出的数据
    // 维护对base64对象的引用，调用其解析某一组的方法
    // 返回字节数即可。
    // 如果是解析所有，则：startIndex = 0；endIndex = s.length
    /**
     * 解码
     */
    public static String decode(String s, int startIndex, int endIndex) {
        StringBuilder sb = new StringBuilder();
        // 以四个字节为一组进行解析
        // i + 2 == '=' 代表最后两个字节为 = ； base64最多也是最后两个字节是 =
        // i + 3 == '=' 代表最后一个字节为 =
        for(int i = startIndex; i < endIndex; i = i + 4){
            if(s.charAt(i + 2) == '='){
                byte w = convert(s.charAt(i));
                byte x = convert(s.charAt(i + 1));
                String bits = byteToBinaryString(w).substring(2, 8) + byteToBinaryString(x).substring(2, 4);
                sb.append(bits);
            }else if(s.charAt(i + 3) == '='){
                byte w = convert(s.charAt(i));
                byte x = convert(s.charAt(i + 1));
                byte y = convert(s.charAt(i + 2));
                String bits = byteToBinaryString(w).substring(2, 8) + byteToBinaryString(x).substring(2, 8) + byteToBinaryString(y).substring(2, 6);
                sb.append(bits);
            }else{
                byte w = convert(s.charAt(i));
                byte x = convert(s.charAt(i + 1));
                byte y = convert(s.charAt(i + 2));
                byte z = convert(s.charAt(i + 3));
                String bits = byteToBinaryString(w).substring(2, 8) + byteToBinaryString(x).substring(2, 8) +
                        byteToBinaryString(y).substring(2, 8) + byteToBinaryString(z).substring(2, 8);
                sb.append(bits);
            }
        }
        return sb.toString();
    }

    /**
     * ASCII编码数字转字符
     */
    public static char convert(int w){
        if(w >= 0 && w <= 25){
            return (char)(w + 65);
        }else if(w >= 26 && w <= 51){
            return (char)(w + 71);
        }else if(w >= 52 && w <= 61){
            return (char)(w - 4);
        }else if(w == 62){
            return '+';
        }else if(w == 63){
            return '/';
        }
        throw new RuntimeException("converter error");
    }

    /**
     * 将字符串对应的ASCII编码，然后再转为对应的Base64编码
     */
    private static byte convert(char w){
        if(w >= 65 && w <= 90){
            return (byte)(w - 65);
        }else if(w >= 97 && w <= 122){
            return (byte)(w - 71);
        }else if(w >= 48 && w <= 57){
            return (byte)(w + 4);
        }else if(w == 43){
            return  62;
        }else if(w == 47){
            return 63;
        }
        throw new RuntimeException("convert error");
    }

    /**
     * 计算任意位数二进制比特串的十进制的值
     */
    private static int compute(String bits){
        int sum = 0;
        for(int j = 0; j < bits.length(); j++){
            if(bits.charAt(j) == '1'){
                sum += (int)Math.pow(2, bits.length() - 1 - j);
            }
        }
        return sum;
    }

    /**
     * byte转8位比特串
     */
    private static String byteToBinaryString(byte b){
        if(b == -128){
            return "10000000";
        }
        byte tmp = b;
        if(tmp < 0){
            tmp = (byte)(tmp & 127);
        }

        int index = 7;
        byte[] bits = new byte[8];
        byte x = tmp;
        do{
            byte y = (byte)(x % 2);
            x = (byte)(x >> 1);
            bits[index--] = y;
        }while(x != 0);
        if(b < 0){
            bits[0] = 1;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bits.length; i++){
            sb.append(bits[i]);
        }
        return sb.toString();
    }

    /**
     * 8位比特串转byte
     */
    private static byte binaryStringToByte(String bits){
        if(bits.length() != 8){
            throw new RuntimeException("binaryStringToByte error");
        }
        if(bits.charAt(0) == '0'){
            return (byte)compute(bits);
        }else if(bits.charAt(0) == '1'){
            if(bits.equals("10000000")){
                return -128;
            }else{
                byte v1 = (byte)compute(bits.substring(1));
                byte v2 = (byte)(~(v1 - 1));
                int v3 = compute(byteToBinaryString(v2).substring(1));
                return (byte)-v3;
            }
        }
        throw new RuntimeException("binaryStrigToByte error");
    }

    /**
     * 剔除字符串中的非法字符（不是BASE64编码中涉及的字符都是非法字符）
     * @param sourceStr
     * @return
     */
    public static String removeIllegalChar(String sourceStr){
        int[]  illegalCharArray = null;
        int illegalCharNum = 0;
        for(int i = 0; i < sourceStr.length(); i++){
            if(!isValidChar(sourceStr.charAt(i))){
                if(illegalCharNum != 0 && illegalCharNum < illegalCharArray.length){
                    illegalCharArray[illegalCharNum++] = i;
                }else if (illegalCharNum == 0){
                    illegalCharArray = new int[12];
                    illegalCharArray[illegalCharNum++] = i;
                }else{
                    int[]  tempIllegalCharArray = new int[illegalCharArray.length * 2];
                    for(int j = 0; j < illegalCharArray.length; j++){
                        tempIllegalCharArray[j] = illegalCharArray[j];
                    }
                    illegalCharArray = tempIllegalCharArray;
                    illegalCharArray[illegalCharNum++] = i;
                }
            }
        }
        if(illegalCharNum > 0){
            StringBuilder sb = new StringBuilder(sourceStr);
            for(int i = illegalCharNum - 1; i >= 0; i--){
                sb.deleteCharAt(illegalCharArray[i]);
            }
            sourceStr = sb.toString();
        }
        return sourceStr;
    }

    public static boolean isValidChar(char w){
        if((w >= 65 && w <= 90) || (w >= 97 && w <= 122) || (w >= 48 && w <= 57) || w == 43 || w == 47 || w == '='){
            return true;
        }
        return false;
    }

}
