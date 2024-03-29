package com.y2t.akeso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.y2t.akeso.dao")
public class AkesoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AkesoApplication.class, args);
        System.out.println("系统启动成功");
    }

}
