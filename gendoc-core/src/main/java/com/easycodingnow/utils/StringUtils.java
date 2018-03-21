package com.easycodingnow.utils;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class StringUtils {

    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
