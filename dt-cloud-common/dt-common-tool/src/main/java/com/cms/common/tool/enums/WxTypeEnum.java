package com.cms.common.tool.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DT
 * @date 2022/4/17 13:59
 */
public enum WxTypeEnum {

    /**
     * 枚举参数
     */
    T001("1","微信公众号"),
    T002("2","企业微信");

    private final String code;

    private final String value;

    WxTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据code,获取指定value
     * @param code 代码
     * @return 返回value
     */
    public static String getValue(String code){
        WxTypeEnum[] enums = values();
        String value = null;
        for(WxTypeEnum n : enums) {
            if(n.code.equals(code)) {
                value = n.value;
                break;
            }
        }
        return value;
    }

    /**
     * 转换为map集合
     * @return 返回map集合
     */
    public static List<Map<String,String>> toCollateMap() {
        WxTypeEnum[] enums = values();
        List<Map<String,String>> list = new ArrayList<>();
        for(WxTypeEnum s : enums){
            Map<String,String> map =new HashMap<>();
            map.put("key",s.code);
            map.put("value",s.value);
            list.add(map);
        }
        return list;
    }

    /**
     * 输出前n个map
     * @param n 参数值
     * @return 返回map集合
     */
    public static List<Map<String, String>> calculationResultMap(Integer n) {
        List<Map<String, String>> mapList = WxTypeEnum.toCollateMap();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < mapList.size() - (mapList.size()-n) ; i++) {
            result.add(mapList.get(i));
        }
        return result;
    }

}
