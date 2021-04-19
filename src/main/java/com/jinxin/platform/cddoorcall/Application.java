package com.jinxin.platform.cddoorcall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangjie
 *  水表数据管理模块
 */
@SpringBootApplication(scanBasePackages = {"com.jinxin.platform"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
