package com.coates.tools.enums;

/**
 * @ClassName HttpStatusCode
 * @Description TODO
 * @Author mc
 * @Date 2019/5/8 16:36
 * @Version 1.0
 **/
public enum HttpStatusCode {
    //
    MISSING_PARAMETERS(2001, "验签缺少参数"),
    ILLEGAL_REQUEST(4001,"非法请求"),
    VERIFICATION_FAILED(203,"验签失败"),
    TOKEN_EXPIRED(202,"令牌过期"),
    MISSING_PARAMETER(2002,"缺少必填参数"),
    INVALID(2002,"无效的token");

    private int code;
    private String msg;

    HttpStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName(int key) {
        for (HttpStatusCode httpStatusCode : HttpStatusCode.values()) {
            if (httpStatusCode.getCode() == key) {
                return httpStatusCode.msg;
            }
        }
        return null;
    }
}
