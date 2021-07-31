package com.why.transportsecurity_finally;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.why")
@MapperScan("com.why")
@EnableSwagger2
public class TransportsecurityFinallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportsecurityFinallyApplication.class, args);
    }

}
