package com.cms.item.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.item.canal.service.MessageConvert;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author DT辰白 Created by 2021/10/11 15:03
 */
@CommonsLog
public class CanalClient implements ClientService {

    private volatile boolean running = false;

    // Canal链接对象
    private CanalConnector connector;

    // 暴露接口调用
    private MessageConvert convert;

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

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            process();
        }
    });

    private final Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            SysCmsUtils.log.warn("解析事件有一个错误...",e);
        }
    };

    @Override
    public void start() {
        Assert.notNull(connector, "连接器不能为空");
        SysCmsUtils.log.warn("canal连接成功...");
        thread.setName("cms-canal-thread");
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    @Override
    public void stop() {
        SysCmsUtils.log.warn("canal关闭...");
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                SysCmsUtils.log.warn("客户端开始关闭 失败!...",e);
            }
        }
    }

    protected void process() {
        int batchSize = 1000;
        while (running) {
            try {
                Thread.sleep(1000);
                connector.connect();
                SysCmsUtils.log.info("Connector connecting!");
                connector.subscribe();
                SysCmsUtils.log.info("Connector connecting complate!");
                while (running) {
                    // 获取指定数量的数据
                    Message message =  connector.getWithoutAck(batchSize, 2000L, TimeUnit.MILLISECONDS);
                    long batchId = message.getId();
                    if (batchId == -1 || message.getEntries().isEmpty()) {
                        connector.ack(batchId);
                        SysCmsUtils.log.info("未拿到数据，暂停2000豪秒继续订阅 : "+ batchId);
                        Thread.sleep(200);
                    } else {
                        try {
                            if (convert == null) {
                                SysCmsUtils.log.warn("没有消息处理器，数据丢失 ....");
                            } else {
                                convert.convert(message);
                            }
                            connector.ack(batchId);
                        } catch (Exception e) {
                            SysCmsUtils.log.error("处理失败, 回滚数据", e);
                            connector.rollback(batchId);
                            throw e;
                        }
                    }

                }
            } catch (InterruptedException e) {
                SysCmsUtils.log.warn("线程中断...", e);
                running = false;
            } catch (Exception e) {
                SysCmsUtils.log.error("线程错误!", e);
            } finally {
                SysCmsUtils.log.warn("connector关闭 ....");
                connector.disconnect();
            }
        }
    }
}
