package me.step4.SearchPlace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger config
 * @author Sihun
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("me.step4.SearchPlace.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false); // 200,401,403,404 생략
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("장소 검색 API Swagger")
                .license("sihun").licenseUrl("http://step4.me").version("1").build();
    }
}