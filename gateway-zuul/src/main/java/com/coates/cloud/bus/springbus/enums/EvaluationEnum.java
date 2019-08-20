package com.coates.cloud.bus.springbus.enums;

/**
 * @ClassName EvaluationEnum
 * @Description TODO
 * @Author mc
 * @Date 2019/6/11 14:28
 * @Version 1.0
 **/
public enum EvaluationEnum {
    /**
     * 用于取值判断
     */
    HEAD_STATUS(0, "head"),
    QUERY_STATUS(1, "query"),
    BODY_STATUS(2, "body");

    private int code;
    private String msg;

    EvaluationEnum(int code, String msg) {
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
        for (EvaluationEnum evaluationEnum : EvaluationEnum.values()) {
            if (evaluationEnum.getCode() == key) {
                return evaluationEnum.msg;
            }
        }
        return null;
    }
}