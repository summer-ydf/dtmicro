package com.cms.gateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ydf Created by 2022/1/10 12:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;

    public String[] permitAllUrls(){
        return  getUrls().stream().map(url->{
            String[] sp=url.split("=");
            return "/"+sp[0].trim()+sp[1].trim();
        }).collect(Collectors.toList()).toArray(new String[urls.size()]);

    }

    public String[] filterAllUrls(){
        return  getUrls().stream().map(url->{
            String[] sp=url.split("=");
            return sp[1].trim();
        }).collect(Collectors.toList()).toArray(new String[urls.size()]);
    }

}
