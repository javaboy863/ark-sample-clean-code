package com.ark.weather.platform.app.common;

/**
 * 天气扩展点使用的常量信息
 *
 */
public final class WeatherExtPointConstant {

    ///// bizId 业务线使用的常量 ////////////
    /**
     * 默认业务线
     */
    public static final String BIZ_ID_DEFAULT = "defaultBizId";
    /**
     * 彩云天气业务线
     */
    public static final String BIZ_ID_CAI_YUN = "caiYunBizId";

    /**
     * 墨迹天气业务线
     */
    public static final String BIZ_ID_MO_JI = "moJiBizId";

    ///// useCase 用例使用的常量 ////////////
    /**
     * 默认用例
     */
    public static final String USE_CASE_DEFAULT = "defaultUseCase";
    /**
     * 获取天气用例
     */
    public static final String USE_CASE_WEATHER = "weatherUseCase";


    ///// scenario 场景使用的常量 ////////////
    /**
     * 默认场景
     */
    public static final String SCENARIO_DEFAULT = "defaultScenario";

    /**
     * 天气定时任务场景
     */
    public static final String SCENARIO_WEATHER_JOB = "weatherJobScenario";
    /**
     * 天粒度天气预报查询
     */
    public static final String SCENARIO_WEATHER_QUERY_DAILY = "dailyScenario";
    /**
     * 小时粒度天气预报查询
     */
    public static final String SCENARIO_WEATHER_QUERY_HOUR = "hourScenario";
    /**
     * 当前天气查询
     */
    public static final String SCENARIO_WEATHER_QUERY_CURRENT = "currentScenario";
}
