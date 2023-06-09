package com.ark.weather.platform.infrastructure.common;


import com.ark.layer.dto.IErrorCode;

/**
 * 业务编码信息
 * 参数异常  P_XX_XX     P_PRODUCT_NameNotNull: 商品名称不能为空
 * 业务异常  B_XX_XX     B_ORDER_IdAlreadyExist: 订单记录已存在
 * 系统异常  S_XX_ERROR  S_DATABASE_ERROR: 数据库错误
 *
 */
public enum ErrorCodeEnum implements IErrorCode {

    B_WEATHER_INFO_QUERY_FAIL("B_WEATHER_INFO_QUERY_FAIL", "天气查询失败！"),
    B_WEATHER_INFO_IS_EMPTY("B_WEATHER_INFO_IS_EMPTY", "天气数据为空！"),
    P_ERR_APP_CODE("P_ERR_APP_CODE", "appCode参数错误！"),
    P_ERR_CITY_ERROR("P_ERR_CITY_ERROR", "城市参数错误！"),
    P_ERR_LAT("P_ERR_LAT", "纬度参数错误！"),
    P_ERR_LNG("P_ERR_LNG", "经度参数错误！"),
    P_ERR_START("P_ERR_START", "日期查询开始参数错误！"),
    P_ERR_END("P_ERR_END", "日期查询结束参数错误！"),


    ;
    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getErrCode() {
        return code;
    }

    @Override
    public String getErrDesc() {
        return message;
    }
}
