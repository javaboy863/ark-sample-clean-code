package com.ark.weather.platform.app.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 预警级别枚举
 *
 */
public enum WeatherAlertLevelEnum {
    /**
     * 蓝色
     */
    BLUE("01", "蓝色"),
    /**
     * 黄色
     */
    YELLOW("02", "黄色"),
    /**
     * 橙色
     */
    ORANGE("03", "橙色"),
    /**
     * 红色
     */
    RED("04", "红色"),
    ;

    private String code;
    private String name;

    WeatherAlertLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static WeatherAlertLevelEnum valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (WeatherAlertLevelEnum alertLevel : WeatherAlertLevelEnum.values()) {
            if (alertLevel.getCode().equals(code)) {
                return alertLevel;
            }
        }
        return null;
    }
}
