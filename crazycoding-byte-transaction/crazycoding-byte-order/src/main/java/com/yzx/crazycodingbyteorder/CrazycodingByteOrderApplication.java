package com.yzx.crazycodingbyteorder;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.yzx.crazycodingbyteorder.mapper","com.yzx.crazycodingbytecommon.mapper"})
@ComponentScan({"com.yzx.crazycodingbytecommon","com.yzx.crazycodingbyteorder"})
public class CrazycodingByteOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrazycodingByteOrderApplication.class, args);
    }

}
