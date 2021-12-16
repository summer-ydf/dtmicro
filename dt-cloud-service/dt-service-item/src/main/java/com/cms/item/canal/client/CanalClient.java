package com.cms.item.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.cms.item.canal.service.MessageConvert;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author ydf Created by 2021/10/11 15:03
 */
@CommonsLog
public class CanalClient implements ClientService {

    private volatile boolean running = false;

    private static final String desName = "destination";

    // Canal链接对象
    private CanalConnector connector;

    // 暴露接口调用
    private MessageConvert convert;

    private String destination;

    public void setConnector(CanalConnector connector) {
        this.connector = connector;
    }

    public CanalConnector getConnector() {
        return connector;
    }

    public MessageConvert getConvert() {
        return convert;
    }

    public void setConvert(MessageConvert convert) {
        this.convert = convert;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            process();
        }
    });

    private final Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("解析事件有一个错误 ", e);
        }
    };

    @Override
    public void start() {
        System.out.println("连接器准备就绪============");
        System.out.println(connector);
        Assert.notNull(connector, "连接器不能为空");
        log.info("Canal client starting....");
        thread.setName("main-CanalThread");
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    @Override
    public void stop() {
        log.info("Canal close ....");
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                log.warn("Canal客户端开始关闭 失败!", e);
            }
        }
    }

    protected void process() {
        int batchSize = 1000;
        while (running) {
            try {
                Thread.sleep(1000);
                connector.connect();
                log.info("Connector connecting!");
                connector.subscribe();
                log.info("Connector connecting complate!");
                while (running) {
                    // 获取指定数量的数据
                    Message message =  connector.getWithoutAck(batchSize, 2000L, TimeUnit.MILLISECONDS);
                    long batchId = message.getId();
                    if (batchId == -1 || message.getEntries().isEmpty()) {
                        connector.ack(batchId);
                        System.out.println("未拿到数据，暂停2000豪秒继续订阅 : " + batchId);
                        Thread.sleep(200);
                    } else {
                        try {
                            if (convert == null) {
                                log.warn("没有消息处理器，数据丢失 ....");
                            } else {
                                convert.convert(message);
                            }
                            connector.ack(batchId);
                        } catch (Exception e) {
                            log.error("处理失败, 回滚数据", e);
                            connector.rollback(batchId);
                            throw e;
                        }
                    }

                }
            } catch (InterruptedException e) {
                log.warn("线程中断...", e);
                running = false;
            } catch (Exception e) {
                log.error("线程错误!", e);
            } finally {
                log.info("connector关闭 ....");
                connector.disconnect();
            }
        }
    }
}
