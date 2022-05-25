package com.cms.item.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.cms.common.tool.utils.SysCmsUtils;
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
 * @author DT辰白 Created by 2021/12/16 16:24
 */
@CommonsLog
public class CanalClientInstance {

    private CanalClient client;

    /**
     * 使用volatile关键字修饰共享变量
     */
    private static volatile CanalClientInstance instance;

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

    private CanalClientInstance(Environment environment) {
        try{
            String ip = environment.getProperty(CanalConstants.CANAL_IP,"localhost");
            String user = environment.getProperty(CanalConstants.CANAL_USER);
            String password = environment.getProperty(CanalConstants.CANAL_PASSWORD);
            int port = Integer.parseInt(Objects.requireNonNull(environment.getProperty(CanalConstants.CANAL_PORT)));
            String databases = environment.getProperty(CanalConstants.CANAL_DATABASE);
            String destination = environment.getProperty(CanalConstants.CANAL_DESTINATION);
            SocketAddress address = new InetSocketAddress(ip, port);
            SysCmsUtils.log.warn("canal链接："+address);
            // 创建Canal链接,ip直连模式
            CanalConnector connector= CanalConnectors.newSingleConnector(address, destination, user, password);
            DefaultMessageConvert convert = new DefaultMessageConvert(databases);
            Set<Class<?>> classSet = ScanClassUtils.scanClass(CanalConstants.CANAL_SCAN_CLASS_PATH, Canal.class);
            for (Class<?> aClass : classSet) {
                convert.register((Class<? extends MessageProcess>) aClass);
            }
            // 初始化Canal
            this.client = new CanalClient();
            this.client.setConnector(connector);
            this.client.setConvert(convert);
//            this.client.setDestination(destination);
            SysCmsUtils.log.warn("canal初始化成功...");
        }catch (Exception e){
            SysCmsUtils.log.error("canal初始化错误..."+e.getMessage());
        }
    }

    public void start() {
        if(instance.client != null) {
            instance.client.start();
        }
    }

    public void stop() {
        if(instance.client != null) {
            instance.client.stop();
        }
    }
}
