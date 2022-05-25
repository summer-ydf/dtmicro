package com.cms.item.canal.processer;

import com.alibaba.fastjson.JSONObject;
import com.cms.item.canal.annotation.Canal;
import com.cms.item.canal.service.MessageProcess;

/**
 * @author DT辰白 Created by 2021/10/11 15:46
 */
@Canal(table = "t_school_test1")
public class AccountProcess implements MessageProcess {

    @Override
    public void insert(JSONObject after) {
        System.out.println("新增操作(t_school_test1)======");
        System.out.println(after);
    }

    @Override
    public void update(JSONObject before, JSONObject after) {
        System.out.println("更新操作(t_school_test1)======");
        System.out.println(before);
        System.out.println(after);
    }

    @Override
    public void delete(JSONObject before) {
        System.out.println("删除操作(t_school_test1)======");
        System.out.println(before);
    }
}
