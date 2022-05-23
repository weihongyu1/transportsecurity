package com.why.transportsecurity_finally.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description TODO swagger2结构管理文档配置
 * @Author why
 * @Date 2021/8/15 12:22
 * Version 1.0
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(aipInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.why")) //扫描哪个包下的接口
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo aipInfo(){
        return new ApiInfoBuilder()
                .title("Transport Security接口API文档") //标题
                .description("接口api文档") //接口描述
                .contact(new Contact("why","http://www.tsn.cn","why_enterprise@163.com")) //联系人相关信息
                .termsOfServiceUrl("http://localhost:80/") //设置文档License信息
                .version("1.0") //接口版本
                .build();
    }
}
