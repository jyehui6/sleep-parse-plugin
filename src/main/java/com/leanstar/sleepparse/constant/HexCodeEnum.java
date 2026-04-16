package com.leanstar.sleepparse.constant;

/**
 * @author feng
 * @date 2020/7/22 9:26
 */
public enum HexCodeEnum {
    T_0x5E(94, "0x5E"),

    T_0x5F(95, "0x5F"),

    T_0x60(96, "0x60"),

    T_0x61(97, "0x61"),
    T_0x62(98, "0x62"),

    T_0x66(102, "0x66");

    private int code;
    private String hexcode;
    HexCodeEnum(int code, String hexcode) {
        this.code = code;
        this.hexcode = hexcode;
    }

    public int getCode() {
        return code;
    }

    public String getHexcode() {
        return hexcode;
    }

    public static HexCodeEnum getEnumByCode(int code) {
        for (HexCodeEnum hexCodeEnum : HexCodeEnum.values()) {
            if (hexCodeEnum.getCode() == code) {
                return hexCodeEnum;
            }
        }
        return null;
    }
}
