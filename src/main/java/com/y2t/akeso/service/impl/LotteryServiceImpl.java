package com.y2t.akeso.service.impl;

import com.y2t.akeso.dao.ILotteryDao;
import com.y2t.akeso.pojo.po.UserLottery;
import com.y2t.akeso.service.ILotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/7/2 15:35
 */
@Service
public class LotteryServiceImpl implements ILotteryService {

    private Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);
    @Autowired
    private ILotteryDao lotteryDao;
    /**
     * 查询红包池是否充足
     * @return
     */
    @Override
    public Boolean isAmple(int amount) {
        int ample = lotteryDao.getAmple();
        logger.info("查询到余量为："+ample);
        return ample>=amount?true:false;
    }

    @Override
    public Boolean increaseForUser(String userId, int amount) {
        UserLottery userLottery = UserLottery.builder().userId(userId)
                .amount(amount)
                .time(LocalDateTime.now()).build();
        int result = lotteryDao.increaseForUser(userLottery);
        if (result > 0 ){
            logger.info("用户【"+userId+"】成功增加了"+amount+"个红包");
            return true;
        }else{
            logger.info("用户【"+userId+"】增加红包失败了");
        }
        return false;
    }

    @Override
    public Boolean decrease(int amount) {

        int result = lotteryDao.decrease(amount);
        if (result > 0 ){
            logger.info("奖池扣减了"+amount+"个红包");
            return true;
        }else{
            logger.info("奖池扣减红包失败了");
        }
        return false;
    }

    //@Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public Boolean userLotteryTransaction(String userId, int amount) {

        if(increaseForUser(userId,amount)){
            if(decrease(amount)){

                return true;
            }else {
                throw new RuntimeException("异常回滚");
            }
        }else{
            throw new RuntimeException("异常回滚");
        }
    }

}
