package com.coates.oauth2.draw;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MathRandom
 * @Description TODO
 * @Author mc
 * @Date 2019/8/27 11:19
 * @Version 1.0
 **/
public class PrizeMathRandom {
    /**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     *
     * @param prizes
     * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
     */
    public int getPrizeIndex(List<Prize> prizes) {
        DecimalFormat df = new DecimalFormat("######0.00");
        int random = -1;
        try {
            //计算总权重
            double sumWeight = 0;
            for (Prize p : prizes) {
                sumWeight += p.getPrize_weight();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for (int i = 0; i < prizes.size(); i++) {
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getPrize_weight())) / sumWeight;
                if (i == 0) {
                    d1 = 0;
                } else {
                    d1 += Double.parseDouble(String.valueOf(prizes.get(i - 1).getPrize_weight())) / sumWeight;
                }
                if (randomNumber >= d1 && randomNumber <= d2) {
                    random = i;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("生成抽奖随机数出错，出错原因：" + e.getMessage());
        }
        return random;
    }

    public static void main(String[] agrs) {
        int i = 0;
        PrizeMathRandom a = new PrizeMathRandom();
        int[] result = new int[5];
        List<Prize> prizes = new ArrayList<Prize>();

        prizes.add(new Prize("范冰冰海报", 1, 1));//奖品的权重设置成1
        prizes.add(new Prize("上海紫园1号别墅", -1, 1));//奖品的权重设置成1
        prizes.add(new Prize("奥迪a9", 1, 2));//奖品的权重设置成1
        prizes.add(new Prize("双色球彩票", 5, 60));//奖品的权重设置成1
        prizes.add(new Prize("谢谢惠顾", 10, 95));//奖品的权重设置成1


        System.out.println("抽奖开始");
        Boolean find = false; //定义一个布尔型的变量，初始值为false
        // 打印100个测试概率的准确性
        for (i = 0; i < 10000; i++) {
            int selected = a.getPrizeIndex(prizes);

            Prize prize = prizes.get(selected);
            if (prize != null) {
                int amount = prize.getPrize_amount();
                if (amount != 0) {
                    prize.setPrize_amount(prize.getPrize_amount() - 1);
                    prizes.add(prize);
                    System.out.println("第" + i + "次抽中的奖品为：" + prizes.get(selected).getPrize_name());
                    System.out.println("抽奖结束");
                    System.out.println("每种奖品抽到的数量为：" + selected);
                    System.out.println("--------------------------------------------------------------------");
                } else {
                    System.out.println("-----------------------------未中奖---------------------------------------");
                    selected=4;
                    System.out.println("第" + i + "次抽中的奖品为：" + prizes.get(selected).getPrize_name());
                    System.out.println("抽奖结束");
                    System.out.println("每种奖品抽到的数量为：" + selected);
                    System.out.println("--------------------------------------------------------------------");
                }
            }
        }


        /*
        System.out.println("一等奖："+result[0]);
        System.out.println("二等奖：" + result[1]);
        System.out.println("三等奖：" + result[2]);
        System.out.println("四等奖：" + result[3]);
        System.out.println("五等奖：" + result[4]);*/
    }
}