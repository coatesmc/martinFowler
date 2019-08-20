package com.coates.tools.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 *作者：liyutang
 *功能描述：MD5加密工具
 *日期：2015年11月26日 下午2:04:50
 */
public class MD5Util {
    private static final String KEY_MD5 = "MD5";
    // 全局数组
    private static final String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private synchronized static byte[] encode(String origin) {
        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            hash = md.digest(origin.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hash;
    }
    public synchronized static String toMD5(String origin) {
        String result = "";
        byte[] hash = MD5Util.encode(origin);
        for (int i = 0; i < hash.length; i++) {
            int itemp = hash[i]&0xFF;
            if(itemp<16) {
                result += "0";
            }
            result += Integer.toString(itemp, 16);
        }

        return result.toUpperCase();
    }
    public synchronized static String toMD5LowerCase(String origin) {
        String result = "";
        byte[] hash = MD5Util.encode(origin);
        for (int i = 0; i < hash.length; i++) {
            int itemp = hash[i]&0xFF;
            if(itemp<16) {
                result += "0";
            }
            result += Integer.toString(itemp, 16);
        }

        return result.toLowerCase();
    }
    public synchronized static boolean isPassword(String origin, String result) {
        if (MD5Util.toMD5(origin).equals(result)) {
            return true;
        }
        return false;
    }
    public static Map<Integer, Integer> getCharMaps(String s) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int i = 0;
        int num = 0;
        while(s.indexOf('.')>0){
        	num = num + s.indexOf('.');
        	map.put(++i, num);
        	s = s.substring(s.indexOf('.')+1, s.length());
        }
        return map;
    }

    /**
     * MD5加密
     * @param strObj
     * @return
     * @throws Exception
     */
    public static String GetMD5Code(String strObj){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(KEY_MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        return byteToString(md.digest(strObj.getBytes()));
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }



}
