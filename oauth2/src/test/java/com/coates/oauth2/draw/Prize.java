package com.coates.oauth2.draw;

import lombok.Data;

/**
 * @ClassName Prize
 * @Description TODO
 * @Author mc
 * @Date 2019/8/27 11:18
 * @Version 1.0
 **/
@Data
public class Prize {
        private int id;//奖品id
        private String prize_name;//奖品名称
        private int prize_amount;//奖品（剩余）数量
        private int prize_weight;//奖品权重


    public Prize(int id, String prize_name, int prize_amount, int prize_weight) {
        this.id = id;
        this.prize_name = prize_name;
        this.prize_amount = prize_amount;
        this.prize_weight = prize_weight;
    }

    public Prize(int id, String prize_name, int prize_weight) {
        this.id = id;
        this.prize_name = prize_name;
        this.prize_weight = prize_weight;
    }

    public Prize(String prize_name, int prize_amount, int prize_weight) {
        this.prize_name = prize_name;
        this.prize_amount = prize_amount;
        this.prize_weight = prize_weight;
    }
}
