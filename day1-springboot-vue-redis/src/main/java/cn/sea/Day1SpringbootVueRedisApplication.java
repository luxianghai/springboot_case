package cn.sea;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.sea.dao")
public class Day1SpringbootVueRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Day1SpringbootVueRedisApplication.class, args);
    }

}
