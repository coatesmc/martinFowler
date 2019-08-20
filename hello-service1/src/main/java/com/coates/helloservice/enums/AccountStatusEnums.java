package com.coates.helloservice.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @ClassName AccountStatusEnums
 * @Description 用户账号状态
 * @Author mc
 * @Date 2019/4/29 17:37
 * @Version 1.0
 **/
public enum AccountStatusEnums {

    // 账户为锁定状态
    STATUS_ZERO(0, "正常"),
    STATUS_ONE(1, "冻结"),
    STATUS_TWO(2, "锁定");

    @EnumValue
    private final int code;
    private final String descp;

     AccountStatusEnums(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }

    /**
     * 获取名字
     * @param key
     * @return
     */
    public String getName(int key) {
        for (AccountStatusEnums accountStatusEnums : AccountStatusEnums.values()) {
            if (accountStatusEnums.getCode() == key) {
                return accountStatusEnums.descp;
            }
        }
        return null;
    }
}
