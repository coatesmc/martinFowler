package com.coates.tools.excpetion;




public class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(Throwable cause) {
        super(cause);
    }
    private static final long serialVersionUID = -6925278824391495117L;

    private Integer code;
    private String message;

    public MyRuntimeException(String message) {
        this.code = -1;
        this.message = message;
    }

    public MyRuntimeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
