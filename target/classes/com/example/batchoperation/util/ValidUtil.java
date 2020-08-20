package com.example.batchoperation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供一些对象有效性校验的方法
 */
@SuppressWarnings("rawtypes")
public final class ValidUtil {

    /**
     * 判断字符串是否是符合指定格式的时间
     * @param date 时间字符串
     * @param format 时间格式
     * @return 是否符合
     */
    public final static boolean isDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断字符串有效性
     */
    public final static boolean valid(String src) {
        return !(src == null || "".equals(src.trim()));
    }

    /**
     * 判断一组字符串是否有效
     * @param src
     * @return
     */
    public final static boolean valid(String... src) {
        if (null == src) {
            return false;
        }
        for (String s : src) {
            if (!valid(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个对象是否为空
     */
    public final static boolean valid(Object obj) {
        if (obj == null || "null".equals(obj)) {
            return false;
        }
        if (obj instanceof String) {
            if (obj.toString().trim().length() == 0) {
                return false;
            }
        } else if (obj.getClass().isArray()) {
            if (obj.toString().length() < 3) {
                return false;
            }
            return true;
        } else if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一组对象是否有效
     * @param objs
     * @return
     */
    public final static boolean valid(Object... objs) {
        if (objs != null && objs.length != 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合的有效性
     */
    public final static boolean valid(Collection col) {
        return !(col == null || col.isEmpty());
    }

    /**
     * 判断一组集合是否有效
     * @param cols
     * @return
     */
    public final static boolean valid(Collection... cols) {
        for (Collection c : cols) {
            if (!valid(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断map是否有效
     * @param map
     * @return
     */
    public final static boolean valid(Map map) {
        return !(map == null || map.isEmpty());
    }

    /**
     * 判断一组map是否有效
     * @param maps 需要判断map
     * @return 是否全部有效
     */
    public final static boolean valid(Map... maps) {
        for (Map m : maps) {
            if (!valid(m)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判定字符串数组中所有值都必须在JSONObject中同时的存在
     * @param verifyStrings
     * @param params
     * @return
     */
    public static Boolean isAllExist(Map<String, Object> params, String... verifyStrings) {
        if (valid(verifyStrings) || valid(params)) {
            return false;
        }

        for (String verifyString : verifyStrings) {

            if (valid(params.get(verifyString))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否全为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }
}
