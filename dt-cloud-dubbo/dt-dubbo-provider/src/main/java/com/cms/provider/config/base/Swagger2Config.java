package com.cms.provider.config.base;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2配置类 localhost:xxx/doc.html
 * @author DT辰白
 * @date 2021/6/3 21:41
 */
@Configuration
public class Swagger2Config {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("dt_api")
                .apiInfo(webApiInfo())
                .enable(true)
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Cloud微服务相关文档")
                .description("API接口")
                .termsOfServiceUrl("https://blog.csdn.net/qq_41107231?spm=1000.2115.3001.5343")
                .version("V1.0")
                .contact(new Contact("DT辰白", "https://blog.csdn.net/qq_41107231?spm=1000.2115.3001.5343", "1973984292@qq.com"))
                .build();
    }

}
