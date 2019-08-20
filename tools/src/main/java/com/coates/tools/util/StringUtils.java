package com.coates.tools.util;

import java.util.Map;

/**
 * 字符串工具类。
 * 
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public final class StringUtils {

	private StringUtils() {}

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     * 
     * @param value 待检查的字符串
     * @return true/false
     */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(Object str) {
		return (str == null || str.toString().isEmpty() || "null".equalsIgnoreCase(str.toString()));
	}

    /**
     * 检查对象是否为数字型字符串,包含负数开头的。
     */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if(length < 1) {
              return false;
          }
		
		int i = 0;
		if(length > 1 && chars[0] == '-') {
              i = 1;
          }
		
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isPositiveInteger(Object obj){
		if (obj == null) {
			return false;
		}
		return isNumeric(obj) && Integer.parseInt(obj.toString()) > 0 ;
	}

    /**
     * 检查指定的字符串列表是否不为空。
     */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

    /**
     * 过滤不可见字符
     */
	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input))) {
              return "";
          }
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF))) {
                   out.append(current);
               }
		}
		return out.toString();
	}

	/**
	 * 构建查询串
	 * @param param
	 * @return
	 */
	public static String buildQueryStr(Map<String,String> param){
		if(param == null || param.size() <= 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		param.forEach((k,v)->sb.append(k).append("=").append(v).append("&"));
		return sb.deleteCharAt(sb.length()-1).toString();
	}

	/**
	 * 判断字符串是否不为空
	 * @param str
	 * @return
	 */
	public static Boolean isNotEmpty(String str){
		return !(str == null || str.trim().isEmpty());
	}
}
