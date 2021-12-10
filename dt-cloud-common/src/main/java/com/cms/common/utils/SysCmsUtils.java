package com.cms.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ydf Created by 2021/11/25 11:17
 */
public class SysCmsUtils {

    public static final Map<String, String> STUDENT_INFORMATION_MAP;

    public static final Snowflake SNOW_FLAKE = IdUtil.createSnowflake(1L, 1L);

    static {
        Map<String, String> studentInformationMap = new LinkedHashMap<String, String>();
        studentInformationMap.put("chinese_name", "中文名");
        studentInformationMap.put("first_name", "姓");
        studentInformationMap.put("last_name", "名");
        studentInformationMap.put("sex", "性别");
        studentInformationMap.put("document_type", "证件类型");
        studentInformationMap.put("document_code", "证件号");
        studentInformationMap.put("birthdate", "生日");
        studentInformationMap.put("birthplace", "出生地");
        studentInformationMap.put("nationality", "国籍");
        studentInformationMap.put("contact_address", "联系地址");
        studentInformationMap.put("phone", "电话");
        studentInformationMap.put("overseas_phone", "海外电话");
        studentInformationMap.put("email", "邮箱");
        studentInformationMap.put("spare_email", "备用邮箱");
        studentInformationMap.put("graduate_credits", "毕业学分");
        studentInformationMap.put("have_credits", "已修学分");
        studentInformationMap.put("major_main", "就读专业");
        studentInformationMap.put("gpa", "目前GPA");
        studentInformationMap.put("degree", "毕业学位");
        STUDENT_INFORMATION_MAP = Collections.unmodifiableMap(studentInformationMap);
    }

    public static String generateKeyId() {
        return SNOW_FLAKE.nextIdStr();
    }

    public static final Log log = LogFactory.get("cms");

    public static void main(String[] args) {
        String keyId = generateKeyId();
        System.out.println("主键ID->>>"+keyId);
        System.out.println(STUDENT_INFORMATION_MAP.get("degree"));
    }
}
