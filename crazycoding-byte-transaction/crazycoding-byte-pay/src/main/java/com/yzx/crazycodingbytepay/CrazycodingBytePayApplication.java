package com.yzx.crazycodingbytepay;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RocketMQAutoConfiguration.class)
@ComponentScan(basePackages = {"com.yzx.crazycodingbytepay","com.yzx.crazycodingbytecommon"})
public class CrazycodingBytePayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrazycodingBytePayApplication.class, args);
    }

}
