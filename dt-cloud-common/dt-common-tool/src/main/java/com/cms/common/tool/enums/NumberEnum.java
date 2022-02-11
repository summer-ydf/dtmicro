package com.cms.common.tool.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ydf Created by 2021/12/21 17:31
 */
public enum NumberEnum {

    /**
     * 枚举参数
     */
    C001("1","一"),
    C002("2","二"),
    C003("3","三"),
    C004("4","四"),
    C005("5","五"),
    C006("6","六");

    private final String code;

    private final String value;

    NumberEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据code,获取指定value
     * @param code 代码
     * @return 返回value
     */
    public static String getValue(String code){
        NumberEnum[] numberEnums = values();
        String value = null;
        for(NumberEnum n : numberEnums) {
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
        NumberEnum[] numberEnums = values();
        List<Map<String,String>> list = new ArrayList<>();
        for(NumberEnum s : numberEnums){
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
        List<Map<String, String>> mapList = NumberEnum.toCollateMap();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < mapList.size() - (mapList.size()-n) ; i++) {
            result.add(mapList.get(i));
        }
        return result;
    }

}
