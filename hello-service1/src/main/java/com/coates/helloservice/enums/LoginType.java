package com.coates.helloservice.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @ClassName LoginType
 * @Description TODO
 * @Author mc
 * @Date 2019/7/2 11:33
 * @Version 1.0
 **/
@Getter
public enum LoginType {
    // 账户状态
    TYPE_DONTMATCH(0, "不匹配"),
    TYPE_MOBILE_PHONE(1, "手机"),
    TYPE_EMAIl(2, "邮件");

    @EnumValue
    private final int code;
    private final String descp;

    LoginType(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }
    /**
     * 获取名字
     * @param key
     * @return
     */
    public String getName(int key) {
        for (LoginType loginType : LoginType.values()) {
            if (loginType.getCode() == key) {
                return loginType.descp;
            }
        }
        return null;
    }
}
