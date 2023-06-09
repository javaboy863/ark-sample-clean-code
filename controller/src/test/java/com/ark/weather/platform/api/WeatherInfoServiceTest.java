package com.ark.weather.platform.api;

import com.alibaba.fastjson.JSON;
import com.ark.weather.platform.dto.cmd.QueryWeatherInfoCmd;
import com.ark.weather.platform.dto.domainmodel.response.WeatherInfoDTO;
import com.ark.layer.dto.Result;
import com.ark.layer.extension.BizScenario;
import com.ark.weather.platform.SpringBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对外API服务测试类
 *
 */
@Slf4j
public class WeatherInfoServiceTest extends SpringBaseTest {
    @Value("${project.name}")
    private String appCode;
    @Resource(name = "weatherInfoImplService")
    private IWeatherInfoService weatherInfoService;


    /**
     * 测试对外彩云天气查询服务
     */
    @Test
    public void testQueryCaiYunWeatherInfo() {
        QueryWeatherInfoCmd queryWeatherInfoCmd = new QueryWeatherInfoCmd();
        queryWeatherInfoCmd.setBizScenario(BizScenario.valueOf("caiYunBizId", "weatherUseCase", "currentScenario"));
        queryWeatherInfoCmd.setAppCode(appCode);
//        queryWeatherInfoCmd.setCityId("120000");
        queryWeatherInfoCmd.setCityName("北京市");
        queryWeatherInfoCmd.setLat(39.97738);
        queryWeatherInfoCmd.setLng(116.48095);
        queryWeatherInfoCmd.setStart(0);
        queryWeatherInfoCmd.setEnd(9);
        Result<List<WeatherInfoDTO>> weatherInfoDTOResult = weatherInfoService.queryWeatherInfo(queryWeatherInfoCmd);
        Assert.assertTrue(weatherInfoDTOResult.isSuccess());
        log.info("彩云天气返回内容:{}", JSON.toJSONString(weatherInfoDTOResult));
    }
}
