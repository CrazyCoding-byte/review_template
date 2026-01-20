package com.yzx.crazycodingbytewms;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ImportAutoConfiguration(RocketMQAutoConfiguration.class)
@ComponentScan({"com.yzx.crazycodingbytecommon","com.yzx.crazycodingbytewms"})
public class CrazycodingByteWmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrazycodingByteWmsApplication.class, args);
    }

}
