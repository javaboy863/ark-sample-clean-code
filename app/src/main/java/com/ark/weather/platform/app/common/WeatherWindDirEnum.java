package com.ark.weather.platform.app.common;

/**
 * 天气风向枚举
 *
 */
public enum WeatherWindDirEnum {
    /**
     * 无风
     */
    CALM("无风", -1, -1),
    /**
     * 北风
     */
    N01("北⻛", 0F, 11.26F),
    /**
     * 北东北⻛
     */
    NNE("北东北⻛", 11.26F, 33.76F),
    /**
     * 东北⻛
     */
    NE("东北⻛", 33.76F, 56.26F),
    /**
     * 东东北⻛
     */
    ENE("东东北⻛", 56.26F, 78.76F),
    /**
     * 东⻛
     */
    E("东风", 78.76F, 101.26F),
    /**
     * 东东南⻛
     */
    ESE("东东南⻛", 101.26F, 123.76F),
    /**
     * 东南⻛
     */
    SE("东南⻛", 123.76F, 146.26F),
    /**
     * 南东南⻛
     */
    SSE("南东南⻛", 146.26F, 168.76F),
    /**
     * 南⻛
     */
    S("南⻛", 168.76F, 191.26F),
    /**
     * 南西南⻛
     */
    SSW("南西南⻛", 191.26F, 213.76F),
    /**
     * ⻄南⻛
     */
    SW("⻄南⻛", 213.76F, 236.26F),
    /**
     * 西西南⻛
     */
    WSW("西西南⻛", 236.26F, 258.76F),
    /**
     * ⻄⻛
     */
    W("⻄⻛", 258.76F, 281.26F),
    /**
     * 西西北⻛
     */
    WNW("西西北⻛", 281.26F, 303.76F),
    /**
     * ⻄北⻛
     */
    NW("⻄北⻛", 303.76F, 326.26F),
    /**
     * 北西北⻛
     */
    NNW("北西北⻛", 326.26F, 348.76F),
    /**
     * 北⻛
     */
    N2("北⻛", 348.76F, 360);

    /**
     * 风向名称
     */
    private String name;
    /**
     * 最小角度
     */
    private float minDegrees;
    /**
     * 最大角度
     */
    private float maxDegrees;

    WeatherWindDirEnum(String name, float minDegrees, float maxDegrees) {
        this.name = name;
        this.minDegrees = minDegrees;
        this.maxDegrees = maxDegrees;
    }

    public String getName() {
        return name;
    }

    public float getMinDegrees() {
        return minDegrees;
    }

    public float getMaxDegrees() {
        return maxDegrees;
    }

    public static WeatherWindDirEnum valueOfDegrees(Float degrees) {
        if (degrees == null) {
            return CALM;
        }
        for (WeatherWindDirEnum windDirEnum : WeatherWindDirEnum.values()) {
            if (degrees >= windDirEnum.getMinDegrees() && degrees < windDirEnum.getMaxDegrees()) {
                return windDirEnum;
            }
        }
        return CALM;
    }
}
