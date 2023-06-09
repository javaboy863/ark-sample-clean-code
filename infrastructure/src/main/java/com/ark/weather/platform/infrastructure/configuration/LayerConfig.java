package com.ark.weather.platform.infrastructure.configuration;

import com.ark.layer.boot.Bootstrap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展点加载器配置
 */
@Configuration
public class LayerConfig {

    @Bean(initMethod = "init")
    public Bootstrap bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        List<String> packagesToScan = new ArrayList<>();
        packagesToScan.add("com.ark.weather");
        bootstrap.setPackages(packagesToScan);
        return bootstrap;
    }
}