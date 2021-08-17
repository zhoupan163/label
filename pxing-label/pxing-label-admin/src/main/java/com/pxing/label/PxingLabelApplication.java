package com.pxing.label;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class PxingLabelApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(PxingLabelApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  吉想启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
