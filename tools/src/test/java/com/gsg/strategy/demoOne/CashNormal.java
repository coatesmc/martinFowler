package com.gsg.strategy.demoOne;

import java.math.BigDecimal;

/**
 * @ClassName CashNormal
 * @Description TODO 正常收费
 * @Author mc
 * @Date 2019/12/10 15:18
 * @Version 1.0
 **/
public class CashNormal extends CashSuper {
    @Override
    public BigDecimal acceptCash(BigDecimal money) {
        return money;
    }
}
