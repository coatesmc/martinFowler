package com.coates.oauth2.draw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName Award
 * @Description TODO
 * @Author mc
 * @Date 2019/8/27 10:57
 * @Version 1.0
 **/
public class Award {
    /**
     * 编号
     */
    public String id;
    /**
     * 概率（0.1代表10%，最多3位小数，即千分之一级）
     */
    public float probability;
    /**
     * 数量（该类奖品剩余数量）
     */
    public int count;

    public Award(String id, float probability, int count) {
        super();
        this.id = id;
        this.probability = probability;
        this.count = count;
    }

    public Award() {

    }

    public static Award lottery(List<Award> awards) {
        //总的概率区间
        float totalPro = 0f;
        //存储每个奖品新的概率区间
        List<Float> proSection = new ArrayList<Float>();
        proSection.add(0f);
        //遍历每个奖品，设置概率区间，总的概率区间为每个概率区间的总和
        for (Award award : awards) {
            //每个概率区间为奖品概率乘以1000（把三位小数转换为整）再乘以剩余奖品数量
            totalPro += award.probability * 10 * award.count;
            proSection.add(totalPro);
        }
        //获取总的概率区间中的随机数
        Random random = new Random();
        float randomPro = (float) random.nextInt((int) totalPro);
        //判断取到的随机数在哪个奖品的概率区间中
        for (int i = 0, size = proSection.size(); i < size; i++) {
            if (randomPro >= proSection.get(i)
                    && randomPro < proSection.get(i + 1)) {
                return awards.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int i = 0;
        List<Award> awards = new ArrayList<>();
        awards.add(new Award("特等奖", 0.001f, 2));
        awards.add(new Award("一等奖", 0.03f, 2));
        awards.add(new Award("二等奖", 0.2f, 3));
        awards.add(new Award("三等奖", 0.3f, 5));
        awards.add(new Award("四等奖", 0.5f, 9));
        awards.add(new Award("五等奖", 0.7f, 10));
        awards.add(new Award("谢谢惠顾", 0.9f, 1000));

        while (true) {
            i++;
            String id = lottery(awards).id;
            if (id.equals("特等奖")) { System.out.println("一共抽取了：" + i);
                System.out.println("恭喜您，抽到了：" + id);
                break;
            }
        }
      /*  for (int i = 0; i < 1000; i++) {


        }*/
    }
}
