package com.cms.common.tool.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cms.common.tool.domain.SysDataScopeVoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据权限Map数据结构转换工具类
 * @author DT
 * @date 2022/3/22 20:39
 */
public class DataScopeUtils {

    public static List<SysDataScopeVoEntity> mapToList(String data) {
        String json = JSONObject.toJSONString(data);
        Object parse = JSON.parse(json);
        String jsonStr = parse.toString();
        JSONArray jsArr = JSONObject.parseArray(jsonStr);
        List<SysDataScopeVoEntity> scopeVoEntities = new ArrayList<>();
        for (int i = 0; i < jsArr.size(); i++) {
            JSONObject jsonObject = jsArr.getJSONObject(i);
            Object roleId = jsonObject.get("roleId");
            Object dataScope = jsonObject.get("dataScope");
            scopeVoEntities.add(SysDataScopeVoEntity.builder().roleId(Long.parseLong(String.valueOf(roleId))).dataScope(String.valueOf(dataScope)).build());
        }
        return scopeVoEntities;
    }
}
