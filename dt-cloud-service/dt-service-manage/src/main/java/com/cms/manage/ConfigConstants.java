package com.cms.manage;

import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.builder.SettingModelBuilders;
import com.cms.manage.consumer.JobSettingCallBacker;

import java.util.Arrays;

/**
 * 系统初始化配置
 * @author DT辰白 Created by 2022/4/19 15:36
 */
public class ConfigConstants {

    public static void init() {

        SettingBuilders.addModels("s1","微信公众号配置", new SettingModelBuilders()
                .buildModel("wx.appid","appid","微信公众号用户唯一凭证","input",true)
                .buildModel("wx.secret","secret","微信公众号用户唯一凭证密钥","input",true)
                .buildModel("wx.templateid","消息模板ID","微信公众号应用消息模板ID","input",true)
                .buildModel("wx.enabled","启用状态","是否开启消息发送","switch",false)
        );

        SettingBuilders.addModels("s2","企业微信配置", new SettingModelBuilders()
                .buildModel("qy.corpid","corpid","企业ID","input",true)
                .buildModel("qy.agentid","agentid","应用ID","input",true)
                .buildModel("qy.corpsecret","corpsecret","应用的凭证密钥","input",true)
                .buildModel("qy.enabled","启用状态","是否开启消息发送","switch",false)
        );

        SettingBuilders.addModels("s3","扩展配置", new SettingModelBuilders()
                .buildModel("ex.ttc","ttc","服务列表","select", Arrays.asList("a", "b", "c"))
        );

        SettingBuilders.addModels("s4","定时任务配置", new SettingModelBuilders()
                .buildModel("ti.cron","查询时间","查询频率:cron表达式", new JobSettingCallBacker())
        );
    }
}
