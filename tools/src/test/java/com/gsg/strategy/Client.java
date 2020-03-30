package com.gsg.strategy;

import com.gsg.strategy.demoOne.CashContext;
import com.gsg.strategy.demoOne.CashNormal;
import com.gsg.strategy.demoOne.CashRebate;
import com.gsg.strategy.demoOne.CashReturn;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @ClassName Client
 * @Description TODO
 * @Author mc
 * @Date 2019/12/10 15:33
 * @Version 1.0
 **/
public class Client {
    public static void main(String[] args) {
        CashContext cashContext = null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入打折方式（1/2/3）：");
        int in = scanner.nextInt();
        String type = "";

        switch (in) {
            case 1:
                cashContext = new CashContext(new CashNormal());
                type += "正常收费";
                break;

            case 2:
                cashContext = new CashContext(new CashReturn(new BigDecimal("300"), new BigDecimal("100")));
                type += "满300返100";
                break;

            case 3:
                cashContext = new CashContext(new CashRebate(new BigDecimal("0.8")));
                type += "打8折";
                break;

            default:
                System.out.println("请输入1/2/3");
                break;
        }

        BigDecimal totalPrices = new BigDecimal("0");

        System.out.print("请输入单价：");
        double price = scanner.nextDouble();
        System.out.print("请输入数量：");
        double num = scanner.nextDouble();
        totalPrices = cashContext.getResult(new BigDecimal(price).multiply(new BigDecimal(num)));

        System.out.println("单价：" + price + "，数量：" + num + "，类型：" + type + "，合计：" + totalPrices);

        scanner.close();
    }
}
