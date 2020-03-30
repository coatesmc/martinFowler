package com.gsg.strategy.demoOne;

import java.math.BigDecimal;

/**
 * @ClassName CashRebate
 * @Description TODO 打折收费子类
 * @Author mc
 * @Date 2019/12/10 15:19
 * @Version 1.0
 **/
public class CashRebate extends CashSuper {
    private BigDecimal moneyRebate = new BigDecimal("1");

    public CashRebate(BigDecimal moneyRebate) {
        this.moneyRebate = moneyRebate;
    }

    @Override
    public BigDecimal acceptCash(BigDecimal money) {
        return money.multiply(moneyRebate);
    }
}
