package com.cms.item.canal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.item.canal.annotation.Canal;
import com.cms.item.canal.service.MessageConvert;
import com.cms.item.canal.service.MessageProcess;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ydf Created by 2021/10/11 14:17
 */
public class DefaultMessageConvert implements MessageConvert {

    protected String database;

    protected static final String process_line = "-";

    private static final Map<String, MessageProcess> PROCESS_MAP = new ConcurrentHashMap<>();

    public DefaultMessageConvert(String database) {
        SysCmsUtils.log.warn("canal初始化处理数据库："+database);
        this.database = database;
    }
    
    @Override
    public void convert(Message message) throws Exception {
        List<CanalEntry.Entry> entrys = message.getEntries();
        for (CanalEntry.Entry entry : entrys) {
            printEntry(entry);
        }
    }

    @Override
    public void register(Class<? extends MessageProcess> process) {
        Canal canal = process.getAnnotation(Canal.class);
        if(canal != null) {
            String key = this.database + process_line + canal.table();
            if(!PROCESS_MAP.containsKey(key)) {
                // BeanUtils.instantiateClass：初始化对象
                PROCESS_MAP.put(key, BeanUtils.instantiateClass(process));
            }
        }
    }

    protected static void printEntry(CanalEntry.Entry entry) throws Exception {
        switch (entry.getEntryType()) {
            // 二进制数据事件
            case ROWDATA:
                rowChange(entry);
                break;
            // 心跳数据事件
            case HEARTBEAT:
                heartbeat(entry);
                break;
            // 事务开始事件
            case TRANSACTIONBEGIN:
                transactionBegin(entry);
                break;
            // 事务结束事件
            case TRANSACTIONEND:
                transactionEnd(entry);
                break;
            default:
                break;
        }
    }

    private static void rowChange(CanalEntry.Entry entry) throws Exception {
        String dataBaseName = entry.getHeader().getSchemaName();
        String tableName = entry.getHeader().getTableName();
        String key = dataBaseName + process_line + tableName;
        SysCmsUtils.log.info("canal处理数据库表名称："+tableName);
        if(PROCESS_MAP.containsKey(key)) {
            MessageProcess process = PROCESS_MAP.get(key);
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            CanalEntry.EventType eventType = rowChange.getEventType();
            for (RowData rowData : rowChange.getRowDatasList()) {
                process(rowData, eventType,process);
            }
        }
    }

    private static void process(RowData rowData, CanalEntry.EventType eventType, MessageProcess process) {
        switch (eventType) {
            // 插入数据
            case INSERT:
                process.insert(toJSONObject(rowData.getAfterColumnsList()));
                break;
            // 更新数据
            case UPDATE:
                process.update(toJSONObject(rowData.getBeforeColumnsList()),toJSONObject(rowData.getAfterColumnsList()));
                break;
            // 删除数据
            case DELETE:
                process.delete(toJSONObject(rowData.getBeforeColumnsList()));
                break;
            default:
                break;
        }
    }

    private static void heartbeat(CanalEntry.Entry entry) {
        SysCmsUtils.log.info("canal心跳数据...");
    }

    private static void transactionBegin(CanalEntry.Entry entry) {
        SysCmsUtils.log.info("canal事务开始处理数据库表...");
    }

    private static void transactionEnd(CanalEntry.Entry entry) {
        SysCmsUtils.log.info("canal事务处理结束...");
    }

    protected static JSONObject toJSONObject(List<Column> columns) {
        JSONObject value = new JSONObject();
        for (Column column : columns) {
            value.put(column.getName(), column.getValue());
        }
        return value;
    }
}
