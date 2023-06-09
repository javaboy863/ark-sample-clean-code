package com.ark.weather.platform.app.common;

/**
 * 天气供应商类型枚举类
 *
 */
public enum SupplerTypeEnum {
    /**
     * 墨迹天气
     */
    MOJI_TYPE(1, "墨迹天气"),
    /**
     * 彩云天气
     */
    CAIYUN_TYPE(2, "彩云天气");


    private Integer code;
    private String message;

    SupplerTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
