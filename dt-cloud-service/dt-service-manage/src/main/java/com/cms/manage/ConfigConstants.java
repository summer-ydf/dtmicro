package com.cms.manage;

import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.builder.SettingModelBuilders;
import com.cms.manage.consumer.JobSettingCallBacker;

import java.util.Arrays;

/**
 * 系统配置
 * @author ydf Created by 2022/4/19 15:36
 */
public class ConfigConstants {

    public static void init() {

        SettingBuilders.addModels("s1","微信公众号配置", new SettingModelBuilders()
                .buildModel("wx.appid","appid","微信公众号用户唯一凭证","input",true)
                .buildModel("wx.secret","secret","微信公众号用户唯一凭证密钥","input",true)
                .buildModel("wx.templated","消息模板ID","微信公众号应用消息模板ID","input",true)
                .buildModel("wx.enabled","状态","是否开启消息发送","radio",false)
        );

        SettingBuilders.addModels("s2","企业微信配置", new SettingModelBuilders()
                .buildModel("qy.corpid","corpid","企业ID","input",true)
                .buildModel("qy.agentid","agentid","应用ID","input",true)
                .buildModel("qy.corpsecret","corpsecret","应用的凭证密钥","input",true)
                .buildModel("qy.enabled","状态","是否开启消息发送","radio",false)
        );

        SettingBuilders.addModels("s3","扩展配置", new SettingModelBuilders()
                .buildModel("ex.ttc","ttc","服务列表","select", Arrays.asList("a", "b", "c"))
        );

        SettingBuilders.addModels("s4","定时任务配置", new SettingModelBuilders()
                .buildModel("ti.cron","查询时间","查询频率:cron表达式",new JobSettingCallBacker())
        );
    }
}
