package com.why.transportsecurity_finally;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.why")
@MapperScan("com.why")
@EnableSwagger2
@EnableTransactionManagement
public class TransportsecurityFinallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportsecurityFinallyApplication.class, args);
    }

}
