package com.coates.tools.excpetion;


import com.coates.tools.util.StringUtils;
import org.apache.http.util.TextUtils;

/**
 * 判断
 */
public class Asserts {

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }
    
    public static void bizCheck(final boolean expression, final String message) {
        if (!expression) {
            throw new AssetServiceException(ErrorCode.BUSINESS_ERROR,message);
        }
    }
    
    public static void check(final boolean expression,final String errCode , final String message) {
        if (!expression) {
            throw new AssetServiceException(errCode,message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    public static void check(final boolean expression, final String message, final Object arg) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, arg));
        }
    }

    public static void notNull(final Object object, final String name) {
        if (object == null) {
            throw new AssetServiceException(ErrorCode.BUSINESS_ERROR,name);
        }
    }
    
    public static void notNull(final Object object,final String errCode, final String name) {
        if (object == null) {
            throw new AssetServiceException(errCode,name);
        }
    }

    public static void notEmpty(final CharSequence s, final String name) {
        if (TextUtils.isEmpty(s)) {
            throw new IllegalStateException(name + " is empty");
        }
    }
    
    public static void notEmpty(final CharSequence s, final String errCode,final String name) {
        if (TextUtils.isEmpty(s)) {
        	 throw new AssetServiceException(errCode,name);
        }
    }

    public static void notBlank(final CharSequence s, final String name) {
        if (TextUtils.isBlank(s)) {
            throw new AssetServiceException(ErrorCode.BUSINESS_ERROR,name);
        }
    }

    /**
     * 返回值判断
     * @param result
     */
//    public static void notSuccess(final ResponseResult result) {
//        if (result.getCode() != 0) {
//            String errCode = String.valueOf(result.getCode());
//            throw new AssetServiceException(errCode,result.getMessage());
//        }
//    }

    /**
     * 判断字符串不为空
     * @param object
     * @param name
     */
    public static void stringNotNull(final String object, final String name) {
        if (StringUtils.isEmpty(object)) {
            throw new MyRuntimeException(name);
        }
    }

    public static void checkByOAI(final boolean expression, final String message) {
        if (!expression) {
            throw new MyRuntimeException(message);
        }
    }
}