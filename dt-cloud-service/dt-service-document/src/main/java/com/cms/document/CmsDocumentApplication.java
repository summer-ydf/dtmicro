package com.cms.document;

import com.cms.common.core.service.FileProvider;
import com.cms.common.tool.utils.SysCmsUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

/**
 * @author DT辰白 Created by 2022/4/7 10:06
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.cms.document.mapper"})
@EnableFeignClients(basePackages ={"com.api.*.feign"})
public class CmsDocumentApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(CmsDocumentApplication.class,args);
        SysCmsUtils.log.info("============================================");
        SysCmsUtils.log.info("===============$文件服务已启动:===============");
        SysCmsUtils.log.info("============================================");
    }

    @Bean
    @RefreshScope
    public FileProvider fileProvider() {
        String uploadUrl = environment.getProperty("minio.client.url");
        String accessKey = environment.getProperty("minio.client.accessKey");
        String secretKey = environment.getProperty("minio.client.secretKey");
        return FileProvider.create(uploadUrl,accessKey,secretKey);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(1024));
        factory.setMaxRequestSize(DataSize.ofMegabytes(1024));
        return factory.createMultipartConfig();
    }
}
