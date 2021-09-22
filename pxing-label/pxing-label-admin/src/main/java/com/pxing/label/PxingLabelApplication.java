package com.pxing.label;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableTransactionManagement
public class PxingLabelApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(PxingLabelApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  芜湖！起飞！！！   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
