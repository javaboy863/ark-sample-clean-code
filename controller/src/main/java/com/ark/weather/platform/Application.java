package com.ark.weather.platform;

import com.ark.monitor.adapter.SpringMonitorInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 启动类
 *
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.ark.weather.platform","com.ark.layer"}, exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.ark.weather.platform.infrastructure")
@ImportResource({"classpath:applicationContext.xml"})
@EnableTransactionManagement
@ImportAutoConfiguration()
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("start success");
    }

    /**
     * 监控大盘配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SpringMonitorInterceptor()).addPathPatterns("/**");
    }



}
