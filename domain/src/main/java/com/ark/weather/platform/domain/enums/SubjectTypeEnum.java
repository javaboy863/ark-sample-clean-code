package com.ark.weather.platform.domain.enums;

/**
 * 预报天气的主体类型
 *
 */
public enum SubjectTypeEnum {
    /**
     * 城市
     */
    CITY(1, "城市"),
    /**
     * 门店
     */
    STATION(2, "门店"),
    ;

    /**
     * 主体编码
     */
    private Integer code;
    /**
     * 主体名称
     */
    private String name;

    SubjectTypeEnum(Integer code, String name) {
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
