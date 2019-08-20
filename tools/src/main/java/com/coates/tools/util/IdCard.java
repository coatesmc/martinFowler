package com.coates.tools.util;

import java.util.Calendar;
import java.util.regex.Pattern;

public class IdCard {

    /** 中国公民身份证号码最小长度。 */
    public final int CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    public final int CHINA_ID_MAX_LENGTH = 18;
    private static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 根据身份证编码获取年龄
     *
     * @param idCard
     *        身份证编码
     * @return  年龄
     */
    public static int getAgeByIdCard(String idCard) {

        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);

        return Integer.parseInt(year);
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard
     *            身份编号
     * @return 生日(MM)
     */
    public static String getMonthByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(10, 12)).toString();
    }



    /**
     * 根据身份编号获取生日天
     *
     * @param idCard
     *            身份编号
     * @return 生天(dd)
     */
    public static Short getDateByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(12, 14));
    }


    /**
     * 根据身份编号获取性别
     *
     * @param idCard
     *            身份编号
     * @return 性别
     */
    public static String  getGenderByIdCard(String idCard) {

        String sGender= "未知";
        String sCardNum = idCard.substring(16, 17);
        if(isInteger(sCardNum)==false){
            return sGender;
        }
        if(Integer.parseInt(sCardNum)%2!=0) {
            sGender="男"; //男
        }else {
            sGender="女"; //女
        }

        return sGender;
    }

    public static boolean isInteger(String str) {
        return pattern.matcher(str).matches();
    }

}
