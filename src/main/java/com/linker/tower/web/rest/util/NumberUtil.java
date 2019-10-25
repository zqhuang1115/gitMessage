package com.linker.tower.web.rest.util;

import java.util.regex.Pattern;

public class NumberUtil {

    private static Pattern IS_INTEGER = Pattern.compile("^[-\\+]?[\\d]*$");

    private static Pattern IS_NUMBER = Pattern.compile("[0-9]*");

    /**
     * 是否是正数
     *
     * @param param
     * @return
     */
    public static boolean isPositive(Long param) {
        return param != null && param > 0;
    }

    /**
     * 是否是正数
     *
     * @param param
     * @return
     */
    public static boolean isPositive(Integer param) {
        return param != null && param > 0;
    }

    /**
     * 是否整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return IS_INTEGER.matcher(str).matches();
    }


    /**
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return IS_NUMBER.matcher(str).matches();
    }
}
