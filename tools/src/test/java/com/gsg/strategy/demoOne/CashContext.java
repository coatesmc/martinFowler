package com.gsg.strategy.demoOne;

import java.math.BigDecimal;

/**
 * @ClassName CashContext
 * @Description TODO
 * @Author mc
 * @Date 2019/12/10 15:15
 * @Version 1.0
 **/
public class CashContext {
    private CashSuper cashSuper;

    public CashContext(CashSuper cashSuper) {
        this.cashSuper = cashSuper;
    }

    public BigDecimal getResult(BigDecimal money) {
        return cashSuper.acceptCash(money);
    }
}
