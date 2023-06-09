package com.ark.weather.platform.infrastructure.common;

import com.ark.missconf.client.common.annotations.DisconfFile;
import com.ark.missconf.client.common.annotations.DisconfFileItem;
import com.ark.missconf.client.common.annotations.DisconfUpdateService;
import com.ark.missconf.client.common.update.IDisconfUpdate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "common-config.properties")
@DisconfUpdateService(classes = {CommonConfig.class})
public class CommonConfig implements IDisconfUpdate {
    /**
     * 省份分隔符
     */
    private static final String DELIMITER = ",";
    /**
     * 是否开启查询所有门店开关 true:是  false:否
     */
    private boolean allStationSwitch;
    /**
     * 按照城市过滤，只查询配置的城市门店天气信息（allStationSwitch=false时才生效）
     */
    private String provinceIds;

    /**
     * 墨迹天气请求的URL
     */
    private String moJiUrl;
    /**
     * 墨迹天气分配的token
     */
    private String moJiToken;
    /**
     * 墨迹天气分配的password
     */
    private String moJiPassword;
    /**
     * 彩云天气请求通用接口的URL格式字符串
     * https://api.caiyunapp.com/v2.5/{token}/{lng},{lat}/weather.json?unit=metric:v2&alert=true
     */
    private String caiYunUrlFormat;

    /**
     * 彩云天气请求天级预报接口的URL格式字符串
     * https://api.caiyunapp.com/v2.5/{token}/{lng},{lat}/daily.json?unit=metric:v2&alert=true
     */
    private String caiYunDailyUrlFormat;
    /**
     * 彩云天气分配的token
     */
    private String caiYunToken;
    /**
     * 门店缓存过期时间
     */
    private Integer stationCacheExpireSeconds;
    /**
     * 天气缓存过期时间
     */
    private Integer weatherCacheExpireSeconds;


    @DisconfFileItem(name = "weather.job.allStationSwitch", associateField = "allStationSwitch")
    public boolean isAllStationSwitch() {
        return allStationSwitch;
    }

    public void setAllStationSwitch(boolean allStationSwitch) {
        this.allStationSwitch = allStationSwitch;
    }

    @DisconfFileItem(name = "weather.job.provinceIds", associateField = "provinceIds")
    public String getProvinceIds() {
        return provinceIds;
    }

    public void setProvinceIds(String provinceIds) {
        this.provinceIds = provinceIds;
    }

    public String[] getProvinceIdArr() {
        String provinceIds = getProvinceIds();
        if (StringUtils.isBlank(provinceIds)) {
            return new String[0];
        }
        return StringUtils.split(provinceIds, DELIMITER);
    }

    @DisconfFileItem(name = "weather.moji.url", associateField = "moJiUrl")
    public String getMoJiUrl() {
        return moJiUrl;
    }

    public void setMoJiUrl(String moJiUrl) {
        this.moJiUrl = moJiUrl;
    }

    @DisconfFileItem(name = "weather.moji.token", associateField = "moJiToken")
    public String getMoJiToken() {
        return moJiToken;
    }

    public void setMoJiToken(String moJiToken) {
        this.moJiToken = moJiToken;
    }

    @DisconfFileItem(name = "weather.moji.password", associateField = "moJiPassword")
    public String getMoJiPassword() {
        return moJiPassword;
    }

    public void setMoJiPassword(String moJiPassword) {
        this.moJiPassword = moJiPassword;
    }

    @DisconfFileItem(name = "weather.caiyun.urlFormat", associateField = "caiYunUrlFormat")
    public String getCaiYunUrlFormat() {
        return caiYunUrlFormat;
    }

    public void setCaiYunUrlFormat(String caiYunUrlFormat) {
        this.caiYunUrlFormat = caiYunUrlFormat;
    }

    @DisconfFileItem(name = "weather.caiyun.daily.urlFormat", associateField = "caiYunDailyUrlFormat")
    public String getCaiYunDailyUrlFormat() {
        return caiYunDailyUrlFormat;
    }

    public void setCaiYunDailyUrlFormat(String caiYunDailyUrlFormat) {
        this.caiYunDailyUrlFormat = caiYunDailyUrlFormat;
    }

    @DisconfFileItem(name = "weather.caiyun.token", associateField = "caiYunToken")
    public String getCaiYunToken() {
        return caiYunToken;
    }

    public void setCaiYunToken(String caiYunToken) {
        this.caiYunToken = caiYunToken;
    }

    @DisconfFileItem(name = "cache.expire.second.station", associateField = "stationCacheExpireSeconds")
    public Integer getStationCacheExpireSeconds() {
        return stationCacheExpireSeconds;
    }

    public void setStationCacheExpireSeconds(Integer stationCacheExpireSeconds) {
        this.stationCacheExpireSeconds = stationCacheExpireSeconds;
    }
    @DisconfFileItem(name = "cache.expire.second.weather", associateField = "weatherCacheExpireSeconds")
    public Integer getWeatherCacheExpireSeconds() {
        return weatherCacheExpireSeconds;
    }

    public void setWeatherCacheExpireSeconds(Integer weatherCacheExpireSeconds) {
        this.weatherCacheExpireSeconds = weatherCacheExpireSeconds;
    }

    @Override
    public void reload() throws Exception {

    }
}
