package com.ark.weather.platform.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 */
@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.cipher}")
    private String dataSourceCipher;
    @Value("${spring.datasource.env}")
    private String dataSourceCipherEnv;

    /**
     * 数据源
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceUtil.createDataSource(dataSourceCipher, dataSourceCipherEnv);
    }
}
