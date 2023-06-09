package com.ark.weather.platform.controller.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 健康检查
 *
 */
@Slf4j
@RestController
@RequestMapping("/index/api/")
public class IndexController {

    @RequestMapping("/healthCheck")
    public String healthCheck() {
        log.info("healthCheck!!!");
        return "ok";
    }

}