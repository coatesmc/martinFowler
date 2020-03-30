package com.gsg.strategy.demoOne;

import java.math.BigDecimal;

/**
 * @ClassName CashReturn
 * @Description TODO 返利收费子类
 * @Author mc
 * @Date 2019/12/10 15:25
 * @Version 1.0
 **/
public class CashReturn extends CashSuper {

    private BigDecimal moneyConditation = new BigDecimal("0"); //返利条件
    private BigDecimal moneyReturn = new BigDecimal("0"); //返利值

    public CashReturn(BigDecimal moneyConditation, BigDecimal moneyReturn) {
        this.moneyConditation = moneyConditation;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public BigDecimal acceptCash(BigDecimal money) {
        BigDecimal result = money;
        if (money.compareTo(moneyConditation) > 0) {
            result = money.subtract(new BigDecimal(Math.floor(money.divide(moneyConditation,2,BigDecimal.ROUND_UP).floatValue())).multiply(moneyReturn));
        }
        return result;
    }
}
