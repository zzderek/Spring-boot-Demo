package com.y2t.akeso.pojo.po;

import lombok.Data;

/**
 * @author ZiTung
 * @description: 奖池
 * @date 2020/7/2 16:20
 */
@Data
public class LotteryPool {

    private  int id;
    private String name;
    private int amount;
    private int amountNow;
}
