package com.coates.tools.util;

import java.util.Random;
import java.util.UUID;

/**
 * Created by huangyp on 2017/4/28.
 */
public final class IdentityUtil {

    private static final Random random = new Random();

    private static final String CHAR_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPGRSTUVWXYZ";

    /**
     * 获得指定前缀的id
     * @param prefix
     * @return
     */
    public static String getIdentity(String prefix){
       String str = getIdentity();
       if( prefix!=null&&!"".equalsIgnoreCase(prefix) ) {
           return prefix+str;
       }
        return str;
    }

    /**
     * 获得不带前缀的id
     * @return
     */
    public static String getIdentity(){
        StringBuilder s = new StringBuilder();
        s.append(System.currentTimeMillis());
        //int i = random.nextInt(s.length());
        //s.insert(i,random.nextInt(10));
        s.append(random.nextInt(10)).append(random.nextInt(10));
        s.append(random.nextInt(10)).append(random.nextInt(10));
        s.append(random.nextInt(10));
        return s.toString();
    }

    /**
     * 获得不带'-'符号的UUID
     * @return
     */
    public static String getUUID(){
        UUID u = UUID.randomUUID();
        return u.toString().replaceAll("-","").toUpperCase();
    }

    /**
     * 生成一个数字ID
     * @param maxLen 生成ID的长度，最小为一个毫秒数
     * @return
     */
    public static String getNumberIdentity(int maxLen){
        StringBuilder s = new StringBuilder();
        s.append(System.currentTimeMillis());
        int ia = random.nextInt(s.length());
        s.insert(ia,random.nextInt(10));
        for( int i = 0,slen = maxLen - s.length(); i < slen ; ++ i ) {
            int a = random.nextInt(10);
            s.append(a);
        }
        return s.toString();
    }

    /**
     * 获得指定长度的随机字符串
     * @param len
     * @return
     */
    public static String getSalt(int len){
        if( len <= 0 ) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for( int i = 0 ; i < len ; ++ i ) {
            sb.append(CHAR_STR.charAt(random.nextInt(CHAR_STR.length())));
        }
        return sb.toString();
    }

    /**
     * 生成id
     * @return
     */
    public static String generateStrId(){
        return  generateStrId(7);
    }

    /**
     * 生成id
     * @return
     */
    public static String generateStrId(int len){
        return  DateUtils.getDateUtils().getTodayDate1() + getNumberIdentity(len);
    }
    public static void main(String[] args) {
		System.out.println(generateStrId().length());
	}
}
