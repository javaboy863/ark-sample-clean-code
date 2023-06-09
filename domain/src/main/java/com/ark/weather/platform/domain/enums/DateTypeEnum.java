package com.ark.weather.platform.domain.enums;

/**
 * 预报天气的日期粒度
 *
 */
public enum DateTypeEnum {
    /**
     * 天
     */
    DAILY(1, "天"),
    /**
     * 小时
     */
    HOURLY(2, "小时"),
    /**
     * 分钟
     */
    MINUTELY(3, "分钟"),
    ;

    /**
     * 编码
     */
    private Integer code;
    
    /**
     * 名称
     */
    private String name;

    DateTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
