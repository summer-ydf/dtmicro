package com.cms.item.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.cms.item.canal.CanalConstants;
import com.cms.item.canal.ScanClassUtils;
import com.cms.item.canal.annotation.Canal;
import com.cms.item.canal.service.MessageProcess;
import com.cms.item.canal.service.impl.DefaultMessageConvert;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.env.Environment;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Set;

/**
 * @author ydf Created by 2021/12/16 16:24
 */
@CommonsLog
public class CanalClientInstance {

    private static final String destination = "example";

    private CanalClient client;

    private static CanalClientInstance instance;

    public synchronized static CanalClientInstance getInstance(Environment environment) {
        if (instance == null) {
            synchronized (CanalClientInstance.class) {
                if (instance == null) {
                    instance = new CanalClientInstance(environment);
                }
            }
        }
        return instance;
    }

    private CanalClientInstance(Environment environment){
        System.out.println("构造方法开始加载========================");
        try{
            String ip = environment.getProperty(CanalConstants.CANAL_IP,"localhost");
            String user = environment.getProperty(CanalConstants.CANAL_USER);
            String password = environment.getProperty(CanalConstants.CANAL_PASSWORD);
            int port = Integer.parseInt(Objects.requireNonNull(environment.getProperty(CanalConstants.CANAL_PORT)));
            String databases = environment.getProperty(CanalConstants.CANAL_DATABASE);
            System.out.println(ip);
            System.out.println(user);
            System.out.println(password);
            System.out.println(port);
            System.out.println(databases);
            SocketAddress address = new InetSocketAddress(ip, port);
            System.out.println("address-》》》"+address);
            // 创建Canal链接,ip直连模式
            CanalConnector connector= CanalConnectors.newSingleConnector(address, destination, user, password);
            DefaultMessageConvert convert = new DefaultMessageConvert(databases);
            Set<Class<?>> classSet = ScanClassUtils.scanClass("com.example.dtcanal.canal.processer", Canal.class);
            System.out.println("类==========");
            System.out.println(classSet);
            for (Class<?> aClass : classSet) {
                System.out.println("===============");
                System.out.println(aClass.getName());
                convert.register((Class<? extends MessageProcess>) aClass);
            }
            // 初始化Canal
            this.client = new CanalClient();
            this.client.setConnector(connector);
            this.client.setConvert(convert);
            this.client.setDestination(destination);
            log.warn("init canal success..");
        }catch (Exception e){
            log.warn("init canal error",e);
        }
    }

    public void start(){
        if(instance.client!=null){
            instance.client.start();
        }
    }

    public void stop(){
        if(instance.client!=null){
            instance.client.stop();
        }
    }
}
