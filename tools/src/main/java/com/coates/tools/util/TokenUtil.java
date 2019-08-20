package com.coates.tools.util;


/**
 * token 工具
 * Created by huangyp on 2017/10/27.
 */
public final class TokenUtil {

    private static long validTimeMills = -1L;

    private final static String SPLIT_CHAT = "#;#";

    /**
     * 设置有效时间毫秒
     * @param mills
     */
    public static void setValidTimeMills(long mills) {
        validTimeMills = mills;
    }

    /**
     * 生成token
     * @param privateKey 私有key
     * @param customerData 自定义数据
     * @return
     */
    public static String getToken( String privateKey , String customerData ){
       StringBuilder sb = new StringBuilder();
       sb.append(IdentityUtil.getUUID()).append(SPLIT_CHAT).append(privateKey).append(SPLIT_CHAT);
       sb.append(StringUtils.isEmpty(customerData) ? "null" : customerData).append(SPLIT_CHAT).append(System.currentTimeMillis());
       String str = AESUtil.encrypt(sb.toString(), privateKey);
       str = str.replaceAll("=","_");
       return str;
    }


    /**
     * token验证
     * @param token 必传
     * @param privateKey 私钥
     * @return
     */
    public static Boolean checkToken(String token, String privateKey){
        return StringUtils.isNotEmpty(checkAndGetToken(token, privateKey));
    }

    /**
     * token验证
     * @param token 必传
     * @param privateKey 私钥
     * @return
     */
    public static String checkAndGetToken(String token, String privateKey){
        token = token.replaceAll("_","=");
        String s = AESUtil.decrypt(token, privateKey);
        if( s != null && ! s.isEmpty() ) {
            String[] args = s.split(SPLIT_CHAT);
            if( validTimeMills > -1L ) {
                long l = System.currentTimeMillis() - Long.parseLong(args[3]);
                if( l > validTimeMills ) {
                    return null;
                }
            }
            if (! privateKey.equals(args[1])) {
                return null;
            } else {
                return args[2];
            }
        }
        return null;
    }

    /**
     * 刷新token
     * @param token
     * @param privateKey
     * @return
     */
    public static String refreshToken(String token, String privateKey) {
        token = token.replaceAll("_","=");
        String s = AESUtil.decrypt(token, privateKey);
        if( s != null && ! s.isEmpty() ) {
            String ns = s.substring(0, s.lastIndexOf(SPLIT_CHAT) + 1) + (System.currentTimeMillis());
            ns = AESUtil.encrypt(ns, privateKey);
            return ns.replaceAll("=","_");
        }
        return null;
    }

}
