package com.ark.weather.platform.app.common;

/**
 * 风力等级类型枚举
 *
 */
public enum WeatherWindLevelEnum {
    /**
     * 0级风力
     */
    ZERO(0, 0F, 1F),
    /**
     * 1级风力
     */
    ONE(1, 1F, 6F),
    /**
     * 2级风力
     */
    TWO(2, 6F, 12F),
    /**
     * 3级风力
     */
    THREE(3, 12F, 20F),
    /**
     * 4级风力
     */
    FOUR(4, 20F, 29F),
    /**
     * 5级风力
     */
    FIVE(5, 29F, 39F),
    /**
     * 6级风力
     */
    SIX(6, 39F, 50F),
    /**
     * 7级风力
     */
    SEVEN(7, 50F, 62F),
    /**
     * 8级风力
     */
    EIGHT(8, 62F, 75F),
    /**
     * 9级风力
     */
    NINE(9, 75F, 89F),
    /**
     * 10级风力
     */
    TEN(10, 89F, 103F),
    /**
     * 11级风力
     */
    ELEVEN(11, 103F, 118F),
    /**
     * 12级风力
     */
    TWELVE(12, 118F, 134F),
    /**
     * 13级风力
     */
    THIRTEEN(13, 134F, 150F),
    /**
     * 14级风力
     */
    FOURTEEN(14, 150F, 167F),
    /**
     * 15级风力
     */
    FIFTEEN(15, 167F, 184F),
    /**
     * 16级风力
     */
    SIXTEEN(16, 184F, 202F),
    /**
     * 17级风力
     */
    SEVENTEEN(17, 202F, 220F);
    /**
     * 最小风速
     */
    private static final float MIN_SPEED = 0F;
    /**
     * 最大风速
     */
    private static final float MAX_SPEED = 220F;
    /**
     * 风级
     */
    private int level;
    /**
     * 最大风速 m/s
     */
    private float maxSpeed;
    /**
     * 最小风速 m/s
     */
    private float minSpeed;

    WeatherWindLevelEnum(int level, float minSpeed, float maxSpeed) {
        this.level = level;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    public int getLevel() {
        return level;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getMinSpeed() {
        return minSpeed;
    }

    public static WeatherWindLevelEnum valueOfSpeed(Float windSpeed) {
        if (windSpeed == null) {
            return ZERO;
        }
        if (windSpeed < MIN_SPEED) {
            return ZERO;
        }
        if (windSpeed > MAX_SPEED) {
            return SEVENTEEN;
        }

        for (WeatherWindLevelEnum windLevel : WeatherWindLevelEnum.values()) {
            if (windSpeed >= windLevel.getMinSpeed() && windSpeed < windLevel.getMaxSpeed()) {
                return windLevel;
            }
        }
        return ZERO;
    }
}
