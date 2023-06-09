package com.ark.weather.platform;

import com.ark.layer.test.annotion.LayerMockConfig;
import org.apache.catalina.mapper.Mapper;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
 * layer test框架的基类，集成测试用例都继承自该类
 * @ContextConfiguration 指定spring 配置文件
 * @LayerMockConfig 要mock掉的类，这里一般放一些读取db数据，调用dubbo服务，读取redis等一些和三方交互的类
 */
@ContextConfiguration("classpath:mock-test.xml")
@LayerMockConfig( annotationMocks = {})
//加载指定的配置文件
//@TestPropertySource(locations={"classpath:*.yml","classpath:*.properties"})
@ActiveProfiles(profiles = {"dev"})
@ComponentScan({"com.ark.weather.platform","com.ark.layer"})
@TestPropertySource("classpath:ark-common-config.properties")
public class LayerBaseMockTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

}