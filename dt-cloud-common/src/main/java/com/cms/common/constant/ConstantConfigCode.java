package com.cms.common.constant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.cms.common.constant.ConstantCommonCode.*;

/**
 * 系统配置常量代码
 * @author ydf Created by 2021/11/25 11:14
 */
public class ConstantConfigCode {

    public static List<Map<String, String>> CONFIG_INFORMATION_LIST = new ArrayList<>();

    static {
        Map<String, String> configInformationMap = new LinkedHashMap<>();
        configInformationMap.put("name", BASE_CONFIG_THEME_TYPE);
        Map<String, String> configInformationMap2 = new LinkedHashMap<>();
        configInformationMap2.put("name", BASE_CONFIG_INDEX_TYPE);
        Map<String, String> configInformationMap3 = new LinkedHashMap<>();
        configInformationMap3.put("name", BASE_CONFIG_MSG_TYPE);
        Map<String, String> configInformationMap4 = new LinkedHashMap<>();
        configInformationMap4.put("name", BASE_CONFIG_LOGIN_TYPE);
        CONFIG_INFORMATION_LIST.add(configInformationMap);
        CONFIG_INFORMATION_LIST.add(configInformationMap2);
        CONFIG_INFORMATION_LIST.add(configInformationMap3);
        CONFIG_INFORMATION_LIST.add(configInformationMap4);
    }

    public static ConstantConfigCode constantConfigCode;

    public static ConstantConfigCode getInstance(){
        if(constantConfigCode == null){
            constantConfigCode = new ConstantConfigCode();
        }
        return constantConfigCode;
    }

    public List<Map<String, String>> getConfigData() {
        return CONFIG_INFORMATION_LIST;
    }

}
