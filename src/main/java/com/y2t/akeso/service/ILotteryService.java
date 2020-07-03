package com.y2t.akeso.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/7/2 15:28
 */
public interface ILotteryService {

    /**
     * 查询红包池是否充足
     * @return
     */
    Boolean isAmple(int amount);

    /**
     * 用户增加红包量
     */
    Boolean increaseForUser(String userId,int amount);

    /**
     * 奖池减少红包量
     *
     */
    Boolean decrease(int amount);

    /**
     * 用户抢红包事务:
     * 包含两个动作：
     * 1-用户增加红包
     * 2-奖池减少红包
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    Boolean userLotteryTransaction(String userId,int amount);
}
