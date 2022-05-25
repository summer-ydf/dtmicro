package com.cms.item.canal.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author DT辰白 Created by 2021/10/11 14:04
 */
public interface MessageProcess {

    /**
     * 插入数据
     * @param after 变更后的数据
     */
    void insert(JSONObject after);

    /**
     * 更新数据
     * @param before 变更前
     * @param after  变更后
     */
    void update(JSONObject before,JSONObject after);

    /**
     * 删除数据
     * @param before 变更前
     */
    void delete(JSONObject before);
}
