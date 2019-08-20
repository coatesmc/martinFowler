package com.coates.tools.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    //编码方式
    public static final String CHARSET = "UTF-8";


    /**
     * 加密
     * @param str 代价密字符串
     * @param privateKey 私钥
     * @return
     */
    public static String encrypt(String str, String privateKey) {
        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：aabbccddeeffgghh
        if( StringUtils.isEmpty(privateKey) || privateKey.length() < 16 ) {
            return "";
        }
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(privateKey.substring(privateKey.length()-16).getBytes());
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            SecretKeySpec key = new SecretKeySpec(privateKey.substring(privateKey.length()-16).getBytes(), "AES");
            //实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
            //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式
            byte[] encryptedData = cipher.doFinal(str.getBytes(CHARSET));
            //BASE64Encoder be = new BASE64Encoder();
           // String s2 = be.encode(encryptedData);
           // return be.encode(s2.getBytes());
            String s2 =  Base64.encode(encryptedData);
            return Base64.encode(s2.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解密
     *
     * @param str
     * @param privateKey
     * @return
     */
    public static String decrypt(String str, String privateKey) {
        if( StringUtils.isEmpty(privateKey) || privateKey.length() < 16 ) {
            return "";
        }
        try {
            byte[] byteMi = Base64.decode(str);
            byteMi =  Base64.decode(new String(byteMi));
            IvParameterSpec zeroIv = new IvParameterSpec(privateKey.substring(privateKey.length()-16).getBytes());
            SecretKeySpec key = new SecretKeySpec(
                    privateKey.substring(privateKey.length()-16).getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //与加密时不同MODE:Cipher.DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*public static void main(String[] args) {
       *//* String content = "union-S5354A215D5S2E1D4D8D63E2F1G45EER-"+String.valueOf(System.currentTimeMillis());
        // 加密
        System.out.println("加密前：" + content);
        String uu = UUID.randomUUID().toString().replace("-","");
        String encryptResult = encrypt(content, uu);

        System.out.println("加密后：" + new String(encryptResult));
        // 解密
        String decryptResult = decrypt(encryptResult , uu);
        System.out.println("解密后：" + new String(decryptResult));*//*
       String ident = IdentityUtil.getUUID();
       TokenUtil.setValidTimeMills(1500);
       String token  = TokenUtil.getToken(ident,"userFixBug");
       System.out.println(token);
       String v = TokenUtil.checkAndGetToken(token, ident);
        System.out.println(v);
       String  s = TokenUtil.refreshToken(token, ident);
        System.out.println(s);
    }*/
}
