package com.gsg.strategy.demoOne;

import java.math.BigDecimal;

/**
 * @ClassName CashSuper
 * @Description TODO
 * @Author mc
 * @Date 2019/12/10 15:17
 * @Version 1.0
 **/
public abstract class CashSuper {
    public abstract BigDecimal acceptCash(BigDecimal money);
}
