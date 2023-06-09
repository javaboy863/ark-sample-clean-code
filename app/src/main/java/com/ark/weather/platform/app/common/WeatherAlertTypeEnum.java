package com.ark.weather.platform.app.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 预警类型枚举
 *
 */
public enum WeatherAlertTypeEnum {
    /**
     * 台风
     */
    TYPHOON("01", "台风"),
    /**
     * 暴雨
     */
    RAINSTORM("02", "暴雨"),
    /**
     * 暴雪
     */
    BLIZZARD("03", "暴雪"),
    /**
     * 寒潮
     */
    COLD_WAVE("04", "寒潮"),
    /**
     * 大风
     */
    GALE("05", "大风"),
    /**
     * 沙尘暴
     */
    SAND_STORM("06", "沙尘暴"),
    /**
     * 高温
     */
    HIGH_TEMPERATURE("07", "高温"),
    /**
     * 干旱
     */
    DROUGHT("08", "干旱"),
    /**
     * 雷电
     */
    THUNDER("09", "雷电"),
    /**
     * 冰雹
     */
    HAIL("10", "冰雹"),
    /**
     * 霜冻
     */
    FROST("11", "霜冻"),
    /**
     * 大雾
     */
    DENSE_FOG("12", "大雾"),
    /**
     * 霾
     */
    HAZE("13", "霾"),
    /**
     * 道路结冰
     */
    ICY_ROAD("14", "道路结冰"),
    /**
     * 森林火灾
     */
    FOREST_FIRE("15", "森林火灾"),
    /**
     * 雷雨大风
     */
    THUNDERSTORM_GALE("16", "雷雨大风"),
    ;

    private String code;
    private String name;

    WeatherAlertTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static WeatherAlertTypeEnum valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (WeatherAlertTypeEnum alertLevel : WeatherAlertTypeEnum.values()) {
            if (alertLevel.getCode().equals(code)) {
                return alertLevel;
            }
        }
        return null;
    }
}
