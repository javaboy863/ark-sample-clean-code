package com.ark.weather.platform.infrastructure.rpc;

import com.alibaba.fastjson.JSON;
import com.ark.basic.station.model.response.MidBaseStationDTO;
import com.ark.weather.platform.SpringBaseTest;
import com.ark.weather.platform.infrastructure.repository.rpc.WarehouseProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * 调用门店数据测试
 *
 */
@Slf4j
public class WarehouseProxyTest extends SpringBaseTest {
    @Resource
    private WarehouseProxy warehouseProxy;


    @Test
    public void testListAllOpenStation() {
        List<MidBaseStationDTO> midBaseStationDTOS = warehouseProxy.listAllOpenStation();
        log.info("门店数量：{}", midBaseStationDTOS.size());
        for (int i = 0; i < midBaseStationDTOS.size(); i++) {
            MidBaseStationDTO midBaseStationDTO = midBaseStationDTOS.get(i);
            String cityId = midBaseStationDTO.getCityId();
            if (StringUtils.isNotBlank(cityId) && cityId.endsWith("1")) {
                log.info("门店信息：{}", JSON.toJSONString(midBaseStationDTO));
            }
        }

    }
}
