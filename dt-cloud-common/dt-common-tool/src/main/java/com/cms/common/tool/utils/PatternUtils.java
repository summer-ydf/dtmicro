package com.cms.common.tool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author ydf Created by 2022/3/30 14:30
 */
public class PatternUtils {

    public static final String NUMBER_REGX = "^[0-9]*$";
    public static final String USER_NAME_REGX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,8}$";
    public static final String USER_PHONE_REGX = "^1[0-9]{10}$";

    public static boolean regxUserName(String username) {
        Pattern r = Pattern.compile(USER_NAME_REGX);
        Matcher m = r.matcher(username);
        return m.matches();
    }

    public static boolean regxPhone(String phone) {
        Pattern r = Pattern.compile(USER_PHONE_REGX);
        Matcher m = r.matcher(phone);
        return m.matches();
    }

    public static boolean regxCode(String code) {
        Pattern r = Pattern.compile(NUMBER_REGX);
        Matcher m = r.matcher(code);
        return m.matches();
    }
}
