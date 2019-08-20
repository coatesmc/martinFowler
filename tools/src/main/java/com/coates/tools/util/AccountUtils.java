package com.coates.tools.util;

/**
 * 登录号码验证
 */
public class AccountUtils {
    //判断是邮箱还是手机号的正则表达式
    public static final String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";/*注：js和java用正则表达式不一样*/
    /*String em ="/^\\w+@\\w+\\.[A-Za-z]{2,3}(\\.[A-Za-z]{2,3})?$/";*/     /*js用正则表达式*/
    public static final String ph = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$";  /*java用验证手机号*/

    public static boolean verifyMobile(String phone) {
        if (phone.matches(ph)) {
            return true;
        }
        return false;
    }

    public static boolean verifyEmail(String eamil) {
        if (eamil.matches(em)) {
            return true;
        }
        return false;
    }
}
