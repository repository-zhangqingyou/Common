package com.zqy.common;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.TimeZone;

/**
 * DruidSpringAopConfiguration
 * src/main/java下的程序入口：SpringBootDemoApplication
 * DynamicDataSourceAutoConfiguration.class  DataSourceAutoConfiguration.class
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})// mybatisplus配置动态数据源时，切记需要关闭自带的自动数据源配置
public class MangeApplication extends SpringBootServletInitializer {
//    private final static String FORMAL = "formal";//正式环境配置
//    private final static String TEST = "test";//测试环境配置
//    private final static String DEFAULT_CONFIG = TEST;//默认环境配置
//    @Autowired
//    private LogUtil log;
//    @Autowired
//    private JsonUtils jsonUtils;//

    public static void main(String[] args) {
        //必须设置时区，否则放到服务器上会以世界时间为准，有8个小时时差问题
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication springApplication = new SpringApplication(MangeApplication.class);
//
//        springApplication.setAdditionalProfiles(DEFAULT_CONFIG);//并且特定profile文件会覆盖默认的配置
//        ConfigurableApplicationContext context = springApplication.run(args);


    }

    /**
     * 重写configure方法，加载启动类
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //initLogUtil();
        return builder.sources(MangeApplication.class);
    }




}
