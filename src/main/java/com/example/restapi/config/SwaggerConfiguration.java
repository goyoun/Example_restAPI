package com.example.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerApi() {
        return new Docket (DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.restapi.controller"))
                .paths(PathSelectors.any()) // com.rest.api.controller하단의 Controller내용을 읽어 mapping 된 resource들을 문서화 시킵니다.
                                             // 아래와 같이 작성해서 v1으로 시작하는 resource들만 문서화 시킬 수도 있습니다.
                                             // PathSelectors.ant("/v1/**") 사용시 swaggerInfo를 세팅하면 문서에 대한 설명과 작성자 정보를 노출 시킬 수 있다.
                .build()
                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200, 401, 403, 404 메세지를 표시하지 않음
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("앱 개발시 사용되는 서버 API에 대한 연동 문서 입니다.")
                .license("goyounsungGitHup").licenseUrl("https://goyoun.github.io/").version("1").build();
    }
}
