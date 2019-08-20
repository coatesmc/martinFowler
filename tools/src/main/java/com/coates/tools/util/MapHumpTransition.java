package com.coates.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName MapHumpTransition
 * @Description TODO
 * @Author mc
 * @Date 2019/5/30 14:23
 * @Version 1.0
 **/
public class MapHumpTransition {
    /**
     * 将Map中的key由下划线转换为驼峰
     *
     * @param map
     * @return
     */
    public static Map<String, String> formatHumpName(Map<String, String> map) {
        Map<String, String> newMap = new HashMap<String, String>();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String newKey = camel2Underline(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    /**
     * 下划线转驼峰
     * @param colName
     * @return
     */
    public static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 将List中map的key值命名方式格式化为驼峰
     *
     * @param
     * @return
     */
    public static List<Map<String, String>> formatHumpNameForList(List<Map<String,String>> list) {
        List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
        for (Map<String,String> o : list) {
            newList.add(formatHumpName(o));
        }
        return newList;
    }

    public static final char UNDERLINE_CHAR = '_';

    /**
     * 驼峰转下划线
     *
     * @param camelStr
     * @return
     */
    public static String camel2Underline(String camelStr) {

        if (StringUtils.isEmpty(camelStr)) {

            return StringUtils.EMPTY;
        }

        int len = camelStr.length();
        StringBuilder strb = new StringBuilder(len + len >> 1);
        for (int i = 0; i < len; i++) {

            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                strb.append(UNDERLINE_CHAR);
                strb.append(Character.toLowerCase(c));
            } else {
                strb.append(c);
            }
        }
        return strb.toString();
    }


    /**
     * Map转化成url拼接请求参数
     * @param map
     * @return
     */
    public static String buildMap(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        if (map.size() > 0) {
            for (String key : map.keySet()) {
                sb.append(key + "=");
                if (StringUtils.isEmpty(map.get(key))) {
                    sb.append("&");
                } else {
                    String value = map.get(key);
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    sb.append(value + "&");
                }
            }
        }
        return sb.toString();
    }
}
