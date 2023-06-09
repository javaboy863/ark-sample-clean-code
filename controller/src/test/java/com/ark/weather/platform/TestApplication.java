package com.ark.weather.platform;

import com.ark.layer.test.container.LayerMockContainer;
import com.ark.layer.test.core.LayerTestRecordController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class TestApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        LayerMockContainer.start(context);
    }

    /**
     * 配置录制controller
     */
    @Bean
    public LayerTestRecordController layerRecordController(){
        LayerTestRecordController recordController = new LayerTestRecordController("com.ark.weather.platform");
        //自动生成代码的Test基类
        recordController.setTemplateSuperClass(LayerBaseMockTest.class);
        return recordController;
    }

}
