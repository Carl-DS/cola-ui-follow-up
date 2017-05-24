package com.colaui.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @项目名称:cola-ui-follow-up
 * @类名:SwaggerConfig
 * @类的描述: swagger2 的基本配置
 * @作者:carl.li
 * @创建时间:2017/5/23
 * @公司:www.bstek.com
 * @邮箱:ldscode@163.com
 * @使用方法：Restful API 访问路径: http://localhost:8080/cola-ui-follow-up/service/swagger-ui.html
 */
@EnableWebMvc // 必须
@EnableSwagger2 // 启用Swagger2 必须
@Configuration // 让Spring来加载该类配置 必须
@ComponentScan(basePackages = {"com.colaui.example.controller"
        ,"com.colaui.system.controller"}) // 必须 扫描的API
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.colaui.example.controller"))
                //过滤生成链接
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    // api接口作者相关信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("cola-ui-follow-up RESTful APIs")
                .termsOfServiceUrl("https://github.com/carl-ds")
                .contact(new Contact("Carl.Li", "https://github.com/carl-ds", "ldscode@163.com"))
                .version("1.0.0")
                .build();
    }
}