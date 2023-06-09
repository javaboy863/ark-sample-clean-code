package com.ark.weather.platform.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 天气状况枚举类
 *
 */
public enum SkyconEnum {
    /**
     * 晴（白天）
     */
    CLEAR_DAY("CLEAR_DAY", "晴（白天）"),
    /**
     * 晴（夜间）
     */
    CLEAR_NIGHT("CLEAR_NIGHT", "晴（夜间）"),
    /**
     * 多云（白天）
     */
    PARTLY_CLOUDY_DAY("PARTLY_CLOUDY_DAY", "多云（白天）"),
    /**
     * 多云（夜间）
     */
    PARTLY_CLOUDY_NIGHT("PARTLY_CLOUDY_NIGHT", "多云（夜间）"),
    /**
     * 阴
     */
    CLOUDY("CLOUDY", "阴"),
    /**
     * 轻度雾霾
     */
    LIGHT_HAZE("LIGHT_HAZE", "轻度雾霾"),
    /**
     * 中度雾霾
     */
    MODERATE_HAZE("MODERATE_HAZE", "中度雾霾"),
    /**
     * 重度雾霾
     */
    HEAVY_HAZE("HEAVY_HAZE", "重度雾霾"),
    /**
     * 小雨
     */
    LIGHT_RAIN("LIGHT_RAIN", "小雨"),
    /**
     * 中雨
     */
    MODERATE_RAIN("MODERATE_RAIN", "中雨"),
    /**
     * 大雨
     */
    HEAVY_RAIN("HEAVY_RAIN", "大雨"),
    /**
     * 暴雨
     */
    STORM_RAIN("STORM_RAIN", "暴雨"),
    /**
     * 雾
     */
    FOG("FOG", "雾"),
    /**
     * 小雪
     */
    LIGHT_SNOW("LIGHT_SNOW", "小雪"),
    /**
     * 中雪
     */
    MODERATE_SNOW("MODERATE_SNOW", "中雪"),
    /**
     * 大雪
     */
    HEAVY_SNOW("HEAVY_SNOW", "大雪"),
    /**
     * 暴雪
     */
    STORM_SNOW("STORM_SNOW", "暴雪"),
    /**
     * 浮尘
     */
    DUST("DUST", "浮尘"),
    /**
     * 沙尘
     */
    SAND("SAND", "沙尘"),
    /**
     * 大风
     */
    WIND("WIND", "大风"),
    /**
     * 未知
     */
    UNKNOWN("UNKNOWN", "未知"),
    ;

    /**
     * 天气代码
     */
    private String code;

    /**
     * 天气现象名称
     */
    private String name;

    SkyconEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过天气代码获取对应的天气状况枚举
     *
     * @param code 天气代码
     * @return
     */
    public static SkyconEnum valueByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (SkyconEnum skyconEnum : SkyconEnum.values()) {
            if (StringUtils.equals(skyconEnum.code, code)) {
                return skyconEnum;
            }
        }
        return null;
    }
}
